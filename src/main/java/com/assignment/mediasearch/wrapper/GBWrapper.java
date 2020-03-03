package com.assignment.mediasearch.wrapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown=true)
public class GBWrapper {

    private int totalItems;
    private String kind;
    private Collection<GBItemsWrapper> items;

}
