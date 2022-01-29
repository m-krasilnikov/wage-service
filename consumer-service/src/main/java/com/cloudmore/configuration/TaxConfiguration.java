package com.cloudmore.configuration;

import java.math.BigDecimal;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "tax")
public class TaxConfiguration {
    private BigDecimal rate;
}
