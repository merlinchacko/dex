package com.assignment.mediasearch.gateways;

import com.assignment.mediasearch.exception.NotFoundException;
import com.assignment.mediasearch.gateways.google.GoogleApiGatewayImpl;
import com.assignment.mediasearch.wrapper.GBWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class GoogleApiGatewayImplTest {
    @InjectMocks
    GoogleApiGatewayImpl googleApiGateway;
    @Mock
    private RestTemplate restTemplate;

    @Test(expected = NotFoundException.class)
    public void canRetrieveGBWrapper() {
        ResponseEntity<GBWrapper> responseEntity = new ResponseEntity<>(new GBWrapper(), HttpStatus.OK);
        when(restTemplate.exchange("/googleUrl", HttpMethod.GET, new HttpEntity<>(null, new HttpHeaders()), GBWrapper.class)).thenReturn(responseEntity);
        googleApiGateway.retrieveGBWrapper("bla");
    }

}
