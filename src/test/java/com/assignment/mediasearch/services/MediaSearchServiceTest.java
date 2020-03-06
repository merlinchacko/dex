package com.assignment.mediasearch.services;

import com.assignment.mediasearch.gateways.google.GoogleApiGatewayImpl;
import com.assignment.mediasearch.gateways.iTunes.ITunesApiGatewayImpl;
import com.assignment.mediasearch.model.MediaInfo;
import com.assignment.mediasearch.model.MediaType;
import com.assignment.mediasearch.service.MediaSearchServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class MediaSearchServiceTest {

    @Mock
    GoogleApiGatewayImpl googleApiGateway;

    @Mock
    ITunesApiGatewayImpl iTunesApiGateway;

    private MediaSearchServiceImpl service;

    @Before
    public void setup() {
        service = new MediaSearchServiceImpl(googleApiGateway, iTunesApiGateway);
        MediaInfo mediaInfo1 = MediaInfo.builder().title("Abc").authors(Collections.singletonList("Test Author")).mediaType(MediaType.BOOK).build();
        MediaInfo mediaInfo2 = MediaInfo.builder().title("Bcd").authors(Collections.singletonList("Test Artist")).mediaType(MediaType.ALBUM).build();
        when(googleApiGateway.retrieveBookList(any())).thenReturn(Collections.singletonList(mediaInfo1));
        when(iTunesApiGateway.retrieveAlbumList(any())).thenReturn(Collections.singletonList(mediaInfo2));
    }

    @Test
    public void shouldReturnSortedMediaInfoList() {
        Collection<MediaInfo> mediaInfos = service.retrieve("any");
        assertEquals(Optional.of("Abc"), mediaInfos.stream().map(MediaInfo::getTitle).findFirst());
        assertEquals(2, mediaInfos.size());
    }

}
