package com.assignment.mediasearch.service;

import com.assignment.mediasearch.gateways.google.GoogleApiGatewayImpl;
import com.assignment.mediasearch.gateways.iTunes.ITunesApiGatewayImpl;
import com.assignment.mediasearch.model.MediaInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class MediaSearchServiceImpl implements MeadiaSearchService {

    private final GoogleApiGatewayImpl googleApiGateway;
    private final ITunesApiGatewayImpl iTunesApiGateway;

    public Collection<MediaInfo> retrieve(String request) {

        Stream<MediaInfo> mediaInfoStream = Stream.of(googleApiGateway.retrieveBookList(request), iTunesApiGateway.retrieveAlbumList(request))
                                                  .flatMap(Collection::stream);

        Collection<MediaInfo> mediaInfos = mediaInfoStream.sorted(Comparator.comparing(MediaInfo::getTitle)).collect(Collectors.toList());

        return mediaInfos;
    }

}
