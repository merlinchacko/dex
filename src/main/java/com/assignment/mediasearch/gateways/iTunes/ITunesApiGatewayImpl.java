package com.assignment.mediasearch.gateways.iTunes;

import com.assignment.mediasearch.exception.InvalidResponseException;
import com.assignment.mediasearch.exception.NotFoundException;
import com.assignment.mediasearch.model.MediaInfo;
import com.assignment.mediasearch.wrapper.ITunesResultWrapper;
import com.assignment.mediasearch.wrapper.ITunesWrapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.assignment.mediasearch.validator.Validator.isNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@Slf4j
@RequiredArgsConstructor
@CircuitBreaker(name = "iTunesApiGateway", fallbackMethod = "getFallbackAlbums")
public class ITunesApiGatewayImpl {

    private final RestTemplate restTemplate;

    @Value("${service.itunes-search-api.url}")
    private String itunesUrl;

    @Value("${service.max-limit}")
    private int maxLimit;

    public Collection<MediaInfo> retrieveAlbumList(String inputTerm) {
        Collection<ITunesResultWrapper> iTunesResultWrappers = retrieveITunesWrapper(inputTerm).getBody().getResults();
        for (ITunesResultWrapper iTunesResultWrapper : iTunesResultWrappers) {
            validateAlbums(iTunesResultWrapper);
        }
        Collection<MediaInfo> albumMediaInfo = iTunesResultWrappers.stream()
                                                                   .map(item -> MediaInfo.builder()
                                                                                         .title(item.getTrackName())
                                                                                         .authors(Collections.singletonList(item.getArtistName()))
                                                                                         .mediaType(com.assignment.mediasearch.model.MediaType.ALBUM)
                                                                                         .build())
                                                                   .collect(Collectors.toList());
        return albumMediaInfo;
    }

    private void validateAlbums(ITunesResultWrapper iTunesResultWrapper) {
        if (isNull(() -> iTunesResultWrapper.getTrackName()) || isNull(() -> iTunesResultWrapper.getArtistName())) {
            throw new InvalidResponseException("Itunes search api returns null response in track name or artist name.");
        }
    }

    public Collection<MediaInfo> getFallbackAlbums(String request, Throwable t) {
        log.error("CIRCUIT BREAKER ENABLED!!! No Response From ITunes Api at this moment due to {} ", t.toString());
        return Collections.singletonList(MediaInfo.builder().build());
    }

    public ResponseEntity<ITunesWrapper> retrieveITunesWrapper(String inputTerm) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();

        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(new MediaType("text", "javascript", Charset.forName("UTF-8")));
        jsonConverter.setSupportedMediaTypes(supportedMediaTypes);
        converters.add(jsonConverter);

        restTemplate.setMessageConverters(converters);

        ResponseEntity<ITunesWrapper> response = restTemplate.exchange(itunesUrl + maxLimit, HttpMethod.GET, entity, ITunesWrapper.class, inputTerm);

        if (isNull(() -> response.getBody().getResults())) {
            log.error("Not able to retrieve albums with keyword {}", inputTerm);
            throw new NotFoundException("Not able to retrieve albums using iTunes search api.");
        }

        return response;
    }

}

