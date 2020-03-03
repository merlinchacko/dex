package com.assignment.mediasearch.wrapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown=true)
public class ITunesWrapper {

    private int resultCount;
    private List<ITunesResultWrapper> results;

}
