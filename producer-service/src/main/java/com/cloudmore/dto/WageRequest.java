package com.cloudmore.dto;

import java.math.BigDecimal;
import java.time.Instant;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class WageRequest {
    @NotNull
    @Size(max = 255)
    private String name;
    @NotNull
    @Size(max = 255)
    private String surname;
    @NotNull
    @PositiveOrZero
    private BigDecimal wage;
    private Instant eventTime;
}
