package com.assignment.mediasearch.wrapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown=true)
public class GBVolumeInfoWrapper {

    private String title;
    private List<String> authors;
    private String language;

}
