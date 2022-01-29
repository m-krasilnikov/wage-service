package com.cloudmore.entity;


import java.io.Serializable;
import javax.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class EmployeeEntity implements Serializable {
    private String name;
    private String surname;
}
