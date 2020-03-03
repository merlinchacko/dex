package com.assignment.mediasearch.model;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.List;

@Value
public class MediaInfo {

    private String title;
    private List<String> author;
    private MediaType mediaType;


}
