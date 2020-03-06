package com.assignment.mediasearch;

import com.assignment.mediasearch.exception.InvalidResponseException;
import com.assignment.mediasearch.gateways.iTunes.ITunesApiGatewayImpl;
import com.assignment.mediasearch.model.MediaInfo;
import com.assignment.mediasearch.wrapper.ITunesWrapper;
import io.vavr.collection.Stream;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

import static io.github.resilience4j.circuitbreaker.CircuitBreaker.State;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = MediaSearchApplication.class)
@DirtiesContext
public class CircuitBreakerTest extends AbstractCircuitBreakerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ITunesApiGatewayImpl iTunesApiGateway;

    @Before
    public void setup() {
        //transitionToClosedState(GOOGLE_API);
        transitionToClosedState(ITUNES_API);
    }

//    @Test
//    public void shouldOpenBackendACircuitBreaker() {
//        // When
//        Stream.rangeClosed(1, 2).forEach((count) -> produceFailure(GOOGLE_API));
//
//        // Then
//        checkHealthStatus(GOOGLE_API, State.OPEN);
//    }

//    @Test
//    public void shouldCloseBackendACircuitBreaker() {
//        transitionToOpenState(GOOGLE_API);
//        registry.circuitBreaker(GOOGLE_API).transitionToHalfOpenState();
//
//        // When
//        Stream.rangeClosed(1, 3).forEach((count) -> produceSuccess(GOOGLE_API));
//
//        // Then
//        checkHealthStatus(GOOGLE_API, State.CLOSED);
//    }

    @Test(expected = InvalidResponseException.class)
    public void shouldOpenBackendBCircuitBreaker() {
        // When
        Stream.rangeClosed(1, 4).forEach((count) -> produceFailure(ITUNES_API));

        // Then
        checkHealthStatus(ITUNES_API, State.OPEN);
    }

    @Test
    public void shouldCloseCircuitBreaker() {
        transitionToOpenState(ITUNES_API);
        registry.circuitBreaker(ITUNES_API).transitionToHalfOpenState();

        // When
        Stream.rangeClosed(1, 4).forEach((count) -> produceSuccess(ITUNES_API));

        // Then
        checkHealthStatus(ITUNES_API, State.CLOSED);
    }

    private void produceFailure(String backend) {
        ResponseEntity<ITunesWrapper> response = iTunesApiGateway.retrieveITunesWrapper("merlin");
    }

    private void produceSuccess(String backend) {
        ResponseEntity<ITunesWrapper> response = iTunesApiGateway.retrieveITunesWrapper("merli");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}

