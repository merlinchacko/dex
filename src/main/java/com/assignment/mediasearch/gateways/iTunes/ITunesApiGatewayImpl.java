package com.assignment.mediasearch.gateways.iTunes;

import com.assignment.mediasearch.exception.InvalidResponseException;
import com.assignment.mediasearch.model.MediaInfo;
import com.assignment.mediasearch.wrapper.ITunesResultWrapper;
import com.assignment.mediasearch.wrapper.ITunesWrapper;
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
public class ITunesApiGatewayImpl {

    private RestTemplate restTemplate = new RestTemplate();

    @Value("${service.itunes-search-api.url}")
    private String itunesUrl;

    public Collection<MediaInfo> retrieveAlbumList(String request) {

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

        ResponseEntity<ITunesWrapper> response = restTemplate.exchange(itunesUrl, HttpMethod.GET, entity, ITunesWrapper.class, request);

        if (isNull(() -> response.getBody().getResults())) {

            log.error("Not able to retrieve albums with keyword {}", request);
            throw new AlbumsNotFoundException("Not able to retrieve albums using iTunes search api.");
        }
        for (ITunesResultWrapper iTunesResultWrapper : response.getBody().getResults()) {

            validateAlbums(iTunesResultWrapper);
        }

        Collection<MediaInfo> albumMediaInfo = response.getBody()
                                                       .getResults()
                                                       .stream()
                                                       .map(item -> new MediaInfo(item.getTrackName(),
                                                                                  Collections.singletonList(item.getArtistName()),
                                                                                  com.assignment.mediasearch.model.MediaType.ALBUM))
                                                       .collect(Collectors.toList());
        return albumMediaInfo;
    }

    private void validateAlbums(ITunesResultWrapper iTunesResultWrapper) {

        if (isNull(() -> iTunesResultWrapper.getTrackName()) || isNull(() -> iTunesResultWrapper.getArtistName())) {

            throw new InvalidResponseException("Itunes search api returns null response in track name or artist name.");
        }
    }
}

