package com.cloudmore.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WageMessage {
    private String messageId;
    private EmployeeWage messageBody;
}
