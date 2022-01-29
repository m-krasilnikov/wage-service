package com.cloudmore.mapper;

import com.cloudmore.model.WageMessage;
import com.cloudmore.entity.EmployeeWageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeWageMapper {

    @Mapping(source = "messageBody.name", target = "employee.name")
    @Mapping(source = "messageBody.surname", target = "employee.surname")
    @Mapping(source = "messageBody.wage", target = "wage")
    @Mapping(source = "messageBody.eventTime", target = "eventTime")
    @Mapping(source = "messageId", target = "id")
    EmployeeWageEntity mapToEntity(WageMessage source);

}
