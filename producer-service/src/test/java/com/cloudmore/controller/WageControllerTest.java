package com.cloudmore.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cloudmore.controller.handler.RestExceptionHandler;
import com.cloudmore.dto.WageRequest;
import com.cloudmore.dto.WageResponse;
import com.cloudmore.exception.ProducerException;
import com.cloudmore.service.WageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.math.BigDecimal;
import java.net.URL;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(SpringExtension.class)
class WageControllerTest {
    private MockMvc mvc;
    @Mock
    private WageService wageService;
    @InjectMocks
    private WageController underTest;

    private final String uuid = UUID.randomUUID().toString();
    private final ObjectMapper om = new ObjectMapper()
        .registerModule(new JavaTimeModule());

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(underTest)
            .setControllerAdvice(new RestExceptionHandler())
            .build();
    }

    @Test
    @DisplayName("Correct wage request return 202-accepted")
    void saveWageShouldReturn202() throws Exception {
        //given
        final WageResponse expectedResponse = new WageResponse(uuid);
        when(wageService.saveWage(any(WageRequest.class)))
            .thenReturn(uuid);
        var req = createRequest();

        //when
        var response = mvc.perform(post("/api/v1/employee-management/wage")
            .contentType(MediaType.APPLICATION_JSON)
            .content(om.writeValueAsString(req)))
            .andExpect(status().isAccepted()).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(202);
        assertThat(response.getContentAsString()).isEqualTo(om.writeValueAsString(expectedResponse));

    }

    @Test
    @DisplayName("Exception in service should throw 500")
    void exceptionInServiceShouldThrow() throws Exception {
        //given
        when(wageService.saveWage(any(WageRequest.class)))
            .thenThrow(new ProducerException("e"));
        var req = createRequest();

        //when
        var response = mvc.perform(post("/api/v1/employee-management/wage")
            .contentType(MediaType.APPLICATION_JSON)
            .content(om.writeValueAsString(req)))
            .andExpect(status().isInternalServerError()).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(500);
    }


    private WageRequest createRequest() {
        final WageRequest wageRequest = new WageRequest();
        wageRequest.setName("Alec");
        wageRequest.setSurname("Baldwin");
        wageRequest.setEventTime(Instant.now());
        wageRequest.setWage(BigDecimal.TEN);
        return wageRequest;
    }
}