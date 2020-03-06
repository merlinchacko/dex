package com.assignment.mediasearch.service;

import com.assignment.mediasearch.model.MediaInfo;

import java.util.Collection;

public interface MeadiaSearchService {

    Collection<MediaInfo> retrieve(String inputTerm);
}
