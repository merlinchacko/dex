package com.assignment.mediasearch.gateways;

import com.assignment.mediasearch.gateways.google.GoogleApiGatewayImpl;
import com.assignment.mediasearch.model.MediaInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class GoogleApiGatewayImplTest {

    @Autowired
    GoogleApiGatewayImpl googleApiGateway;

    @Test
    public void canRetrieveBookList() {
        Collection<MediaInfo> mediaInfos = googleApiGateway.retrieveBookList("bla");
        assertNotNull(mediaInfos);
        assertEquals(10, mediaInfos.size());
    }

}
