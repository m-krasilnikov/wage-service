package com.cloudmore.consumer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.cloudmore.model.EmployeeWage;
import com.cloudmore.model.WageMessage;
import com.cloudmore.service.EmployeeWageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WageConsumerTest {
    @Mock
    private ObjectMapper om;
    @Mock
    private EmployeeWageService employeeWageService;
    @InjectMocks
    private WageConsumer subject;

    @Test
    void consumeShouldPass() {
        doNothing().when(employeeWageService).saveWage(any(WageMessage.class));

        subject.consume("key", "{}");

        verify(employeeWageService, times(1)).saveWage(any(WageMessage.class));
    }

    @Test
    @DisplayName("In case of exception of Json processing employeeWageService should not be executed")
    void consumeShouldDoNothingIfThrows() throws JsonProcessingException {
        doThrow(JsonProcessingException.class).when(om).readValue(eq("{}"), eq(EmployeeWage.class));

        subject.consume("key", "{}");

        verify(employeeWageService, times(0)).saveWage(any(WageMessage.class));
    }
}