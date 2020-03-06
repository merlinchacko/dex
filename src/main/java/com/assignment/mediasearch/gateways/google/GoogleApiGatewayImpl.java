package com.assignment.mediasearch.gateways.google;

import com.assignment.mediasearch.exception.InvalidResponseException;
import com.assignment.mediasearch.exception.NotFoundException;
import com.assignment.mediasearch.model.MediaInfo;
import com.assignment.mediasearch.model.MediaType;
import com.assignment.mediasearch.wrapper.GBItemsWrapper;
import com.assignment.mediasearch.wrapper.GBWrapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import static com.assignment.mediasearch.validator.Validator.isNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@Slf4j
@RequiredArgsConstructor
@CircuitBreaker(name = "googleApiGateway", fallbackMethod = "getFallbackAlbums")
public class GoogleApiGatewayImpl {
    private final RestTemplate restTemplate;

    @Value("${service.google-books-api.url}")
    private String googleUrl;

    @Value("${service.max-limit}")
    private int maxLimit;

    @Cacheable("books")
    public Collection<MediaInfo> retrieveBookList(String inputTerm) {

        Collection<GBItemsWrapper> gbItemsWrappers = retrieveGBWrapper(inputTerm).getBody().getItems();

        for (GBItemsWrapper gbItemsWrapper : gbItemsWrappers) {

            validateBooks(gbItemsWrapper);
        }

        Collection<MediaInfo> bookMediaInfo = gbItemsWrappers.stream()
                                                             .map(item -> MediaInfo.builder()
                                                                                   .title(item.getVolumeInfo().getTitle())
                                                                                   .authors(item.getVolumeInfo().getAuthors())
                                                                                   .mediaType(MediaType.BOOK)
                                                                                   .build())
                                                             .collect(Collectors.toList());

        return bookMediaInfo;
    }

    private void validateBooks(GBItemsWrapper gbItemsWrapper) {
        if (isNull(() -> gbItemsWrapper.getVolumeInfo().getTitle())) {
            throw new InvalidResponseException("Google books api returns null response in title.");
        }
    }

    public Collection<MediaInfo> getFallbackBooks(String inputTern, Throwable t) {
        log.error("CIRCUIT BREAKER ENABLED!!! No Response From Google Api at this moment due to {} ", t.toString());
        return Collections.singletonList(MediaInfo.builder().build());
    }

    public ResponseEntity<GBWrapper> retrieveGBWrapper(String inputTerm) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<GBWrapper> response = restTemplate.exchange(googleUrl + maxLimit, HttpMethod.GET, entity, GBWrapper.class, inputTerm);

        if (isNull(() -> response.getBody().getItems())) {
            log.error("Not able to retrieve books with keyword {}", inputTerm);
            throw new NotFoundException("Not able to retrieve books using google api.");
        }

        return response;
    }
}
