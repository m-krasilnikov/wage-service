package com.cloudmore.service;

import com.cloudmore.configuration.TaxConfiguration;
import com.cloudmore.model.WageMessage;
import com.cloudmore.entity.EmployeeWageEntity;
import com.cloudmore.mapper.EmployeeWageMapper;
import com.cloudmore.repository.EmployeeManagementRepository;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeWageServiceImpl implements EmployeeWageService {
    private final TaxConfiguration taxConfiguration;
    private final EmployeeManagementRepository repository;
    private final EmployeeWageMapper mapper;

    @Override
    @Transactional
    public void saveWage(WageMessage message) {
        var employeeWageEntity= mapper.mapToEntity(message);
        calculateTaxes(taxConfiguration.getRate(), employeeWageEntity);
        repository.save(employeeWageEntity);
        log.info("message:[{}] saved in the db", message.getMessageId());
    }

    private void calculateTaxes(BigDecimal rate, EmployeeWageEntity employee) {
        final BigDecimal wageWithTaxes = employee.getWage().multiply(rate);
        employee.setWage(wageWithTaxes);
    }
}
