package com.assignment.mediasearch.endpoints;

import com.assignment.mediasearch.exception.InvalidResponseException;
import com.assignment.mediasearch.exception.NotFoundException;
import com.assignment.mediasearch.model.MediaInfo;
import com.assignment.mediasearch.service.MediaSearchServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = MediaSearchEndpoint.class)
public class MediaSearchEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MediaSearchServiceImpl service;

    @Test
    public void canGetMediaInfos() throws Exception {

        MediaInfo mediaInfo = MediaInfo.builder().title("Merlin").build();
        when(service.retrieve("merli")).thenReturn(Collections.singleton(mediaInfo));

        MvcResult result = mockMvc.perform(get("/media-search?inputTerm=merli").contentType(MediaType.APPLICATION_JSON)).andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    public void shouldReturnNotFoundException() throws Exception {
        when(service.retrieve("merlin")).thenThrow(new NotFoundException("bla"));
        MvcResult result = mockMvc.perform(get("/media-search?inputTerm=merlin").contentType(MediaType.APPLICATION_JSON)).andReturn();
        assertEquals(404, result.getResponse().getStatus());
    }

    @Test
    public void shouldReturnBadRequestForInvalidArrangementNumber() throws Exception {
        when(service.retrieve("merlin")).thenThrow(new InvalidResponseException("bla"));
        MvcResult result = mockMvc.perform(get("/media-search?inputTerm=merlin").contentType(MediaType.APPLICATION_JSON)).andReturn();
        assertEquals(500, result.getResponse().getStatus());
    }

}

