package com.assignment.mediasearch.gateways.iTunes;

import com.assignment.mediasearch.model.MediaInfo;
import com.assignment.mediasearch.wrapper.ITunesWrapper;
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

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
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

        Collection<MediaInfo> albumMediaInfo = response.getBody()
                                                       .getResults()
                                                       .stream()
                                                       .map(item -> new MediaInfo(item.getTrackName(),
                                                                                  Collections.singletonList(item.getArtistName()),
                                                                                  com.assignment.mediasearch.model.MediaType.ALBUM))
                                                       .collect(Collectors.toList());
        return albumMediaInfo;
    }

}
