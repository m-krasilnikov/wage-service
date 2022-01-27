package com.cloudmore.controller;

import com.cloudmore.dto.WageRequest;
import com.cloudmore.service.WageService;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "api/v1")
@RequiredArgsConstructor
public class WageController {

    private final WageService wageService;

    @PostMapping(path = "/wage/calculate")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void calculateWage(@RequestBody WageRequest wageRequest) {
        wageService.calculateWage(wageRequest);
    }


}
