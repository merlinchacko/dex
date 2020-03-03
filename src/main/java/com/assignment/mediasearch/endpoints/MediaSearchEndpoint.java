package com.assignment.mediasearch.endpoints;

import com.assignment.mediasearch.model.MediaInfo;
import com.assignment.mediasearch.service.MediaSearchServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class MediaSearchEndpoint {

    private final MediaSearchServiceImpl service;

    @ApiOperation(value = "Validates an arrangement and returns it on valid!", tags = {"Arrangements"})
    @ApiResponses({@ApiResponse(code = 200, message = "Arrangement found and it is valid!"),
                   @ApiResponse(code = 400, message = "Arrangement Number is invalid!"),
                   @ApiResponse(code = 404, message = "Arrangement not found!")})
    @GetMapping
    public ResponseEntity<Collection<MediaInfo>> retrieveMedia(@RequestParam String inputTerm) {

        return new ResponseEntity<>(service.retrieve(inputTerm), HttpStatus.OK);
    }
}