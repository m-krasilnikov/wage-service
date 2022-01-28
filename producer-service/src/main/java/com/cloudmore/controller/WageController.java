package com.cloudmore.controller;

import com.cloudmore.dto.WageRequest;
import com.cloudmore.dto.WageResponse;
import com.cloudmore.service.WageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "api/v1/wage-management")
@RequiredArgsConstructor
public class WageController {

    private final WageService wageService;

    @PostMapping(path = "/wage")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public WageResponse saveWage(@RequestBody WageRequest wageRequest) {
        log.info("Got request -> {}", wageRequest.toString());
        var eventId = wageService.saveWage(wageRequest);
        return new WageResponse(eventId);
    }

}
