package com.assignment.mediasearch.gateways.google;

import com.assignment.mediasearch.model.MediaInfo;
import com.assignment.mediasearch.model.MediaType;
import com.assignment.mediasearch.wrapper.GBWrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class GoogleApiGatewayImpl {

    private RestTemplate restTemplate = new RestTemplate();

    @Value("${service.google-books-api.url}")
    private String googleUrl;

    public Collection<MediaInfo> retrieveBookList(String request) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<GBWrapper> response = restTemplate.exchange(googleUrl, HttpMethod.GET, entity, GBWrapper.class, request);

        Collection<MediaInfo> bookMediaInfo = response.getBody()
                                                      .getItems()
                                                      .stream()
                                                      .map(item -> new MediaInfo(item.getVolumeInfo().getTitle(),
                                                                                 item.getVolumeInfo().getAuthors(),
                                                                                 MediaType.BOOK))
                                                      .collect(Collectors.toList());

        return bookMediaInfo;
    }
}
