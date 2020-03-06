package com.assignment.mediasearch.model;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class MediaInfo {

    private String title;
    private List<String> authors;
    private MediaType mediaType;

}
