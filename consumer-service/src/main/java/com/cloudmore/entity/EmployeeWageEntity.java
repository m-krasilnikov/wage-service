package com.cloudmore.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee_wage")
public class EmployeeWageEntity {
    @EmbeddedId
    private EmployeeEntity employee;
    @Length(min =36, max = 36)
    private String id;
    private BigDecimal wage;
    private Instant eventTime;
}
