package com.assignment.mediasearch.endpoints;

import com.assignment.mediasearch.model.MediaInfo;
import com.assignment.mediasearch.service.MediaSearchServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
@RequestMapping(value = "/media-search", produces = MediaType.APPLICATION_JSON_VALUE)
public class MediaSearchEndpoint {

    private final MediaSearchServiceImpl service;

    @ApiOperation(value = "Retrieve books and albums for the search input term ", tags = {"Retrieve Media"})
    @ApiResponses({@ApiResponse(code = 200, message = "Retrieved books and albums based on the input term!"),
                   @ApiResponse(code = 404, message = "Not able to retrieve media based on the input term!")})
    @GetMapping
    public ResponseEntity<Collection<MediaInfo>> retrieveMedia(@RequestParam String inputTerm) throws InterruptedException {
        return new ResponseEntity<>(service.retrieve(inputTerm), HttpStatus.OK);
    }
}