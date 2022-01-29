package com.cloudmore.model;

import java.math.BigDecimal;
import java.time.Instant;
import lombok.Data;

@Data
public class EmployeeWage {
    private String name;
    private String surname;
    private BigDecimal wage;
    private Instant eventTime;
}