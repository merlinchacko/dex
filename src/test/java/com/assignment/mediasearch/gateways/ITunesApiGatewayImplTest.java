package com.assignment.mediasearch.gateways;

import com.assignment.mediasearch.gateways.google.GoogleApiGatewayImpl;
import com.assignment.mediasearch.gateways.iTunes.ITunesApiGatewayImpl;
import com.assignment.mediasearch.wrapper.GBItemsWrapper;
import com.assignment.mediasearch.wrapper.GBVolumeInfoWrapper;
import com.assignment.mediasearch.wrapper.GBWrapper;
import com.assignment.mediasearch.wrapper.ITunesWrapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;

@RunWith(MockitoJUnitRunner.class)
public class ITunesApiGatewayImplTest {

    ITunesApiGatewayImpl iTunesApiGateway;
    @Mock
    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        iTunesApiGateway = new ITunesApiGatewayImpl();
       // ReflectionTestUtils.setField(googleApiGateway, "googleUrl", "/googleUrl");
    }

//    @Test
//    public void canRetrieveGBWrapper() throws URISyntaxException {
//
//
//        ResponseEntity<ITunesWrapper> responseEntity = new ResponseEntity<>(ITunesWrapper.builder().build(), HttpStatus.OK);
//       // Mockito.when(restTemplate.exchange(Matchers.any(URI.class), Matchers.any(HttpMethod.class), Matchers.<HttpEntity<?>> any(), Matchers.<Class<Object>> any())).thenReturn(responseEntity);
//
//        Mockito.when(restTemplate.exchange("https://www.google.com", HttpMethod.GET, new HttpEntity<>(String.class), ITunesWrapper.class))
//               .thenReturn(responseEntity);
//
//        iTunesApiGateway
//                .retrieveITunesWrapper("any");
//
//       // assertEquals(responseEntity.getBody().getItems(), gbWrapper.getBody().getItems());
//    }

    //    @Test(expected = InvestmentException.class)
    //    public void shouldThrowInvestmentExceptionWhenDataIncomplete() {
    //
    //        final ArgumentCaptor<HttpEntity<InvestmentPackageInput>> investmentPackageInputCapture = ArgumentCaptor.forClass(HttpEntity.class);
    //        ResponseEntity<InvestmentPackageResponse> responseEntity = new ResponseEntity<>(InvestmentPackageResponse.builder().build(), HttpStatus.OK);
    //
    //        when(restTemplate.exchange(ArgumentMatchers.anyString(),
    //                                   ArgumentMatchers.eq(HttpMethod.POST),
    //                                   investmentPackageInputCapture.capture(),
    //                                   ArgumentMatchers.<Class<InvestmentPackageResponse>>any())).thenReturn(responseEntity);
    //
    //        investmentService.createInvestmentPackage(OrderKey.builder().build(), InvestmentPackageInput.builder().answers(new HashMap<>()).build());
    //    }
}
