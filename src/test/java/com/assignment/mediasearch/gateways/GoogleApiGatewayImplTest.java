package com.assignment.mediasearch.gateways;

import com.assignment.mediasearch.gateways.google.GoogleApiGatewayImpl;
import com.assignment.mediasearch.wrapper.GBItemsWrapper;
import com.assignment.mediasearch.wrapper.GBWrapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class GoogleApiGatewayImplTest {

    GoogleApiGatewayImpl googleApiGateway;
    @Mock
    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        googleApiGateway = new GoogleApiGatewayImpl(restTemplate);
    }

    @Test
    public void canRetrieveGBWrapper() {
        ResponseEntity<GBWrapper> responseEntity = new ResponseEntity<>(new GBWrapper(), HttpStatus.OK);
        when(restTemplate.exchange("/googleUrl", HttpMethod.GET, new HttpEntity<>(null, new HttpHeaders()), GBWrapper.class)).thenReturn(responseEntity);
        googleApiGateway.retrieveGBWrapper("bla");
    }

//    @Test
//    public void canRetrieveBookList() {
//        ResponseEntity<GBWrapper> responseEntity = new ResponseEntity<>(new GBWrapper(), HttpStatus.OK);
//        when(googleApiGateway.retrieveGBWrapper("bla")).thenReturn(responseEntity);
//        googleApiGateway.retrieveBookList("bla");
//    }

}
