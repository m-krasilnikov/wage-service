package com.cloudmore.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.cloudmore.configuration.TaxConfiguration;
import com.cloudmore.entity.EmployeeWageEntity;
import com.cloudmore.mapper.EmployeeWageMapper;
import com.cloudmore.model.EmployeeWage;
import com.cloudmore.model.WageMessage;
import com.cloudmore.repository.EmployeeManagementRepository;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmployeeWageServiceImplTest {
    public static final double TAX_RATE = 1.1;

    @Mock
    private TaxConfiguration taxConfiguration;
    @Mock
    private EmployeeManagementRepository repository;
    @Mock
    private EmployeeWageMapper mapper;
    @Captor
    private ArgumentCaptor<EmployeeWageEntity> entityCaptor;
    @InjectMocks
    private EmployeeWageServiceImpl subject;

    @Test
    void saveWageShouldPass() {
        var message = createMessage();
        var entity = createEntity(message);

        doReturn(BigDecimal.valueOf(TAX_RATE)).when(taxConfiguration).getRate();
        doReturn(entity).when(mapper).mapToEntity(any(WageMessage.class));
        doReturn(null).when(repository).save(entityCaptor.capture());

        subject.saveWage(message);

        verify(taxConfiguration, times(1)).getRate();
        verify(mapper, times(1)).mapToEntity(any(WageMessage.class));
        verify(repository, times(1)).save(any(EmployeeWageEntity.class));

        assertEquals(
            message.getMessageBody().getWage().multiply(BigDecimal.valueOf(TAX_RATE)),
            entityCaptor.getValue().getWage()
        );
    }

    private EmployeeWageEntity createEntity(WageMessage message) {
        return EmployeeWageEntity.builder()
           .wage(message.getMessageBody().getWage())
           .id(message.getMessageId())
           .build();
    }

    private WageMessage createMessage() {
        final EmployeeWage employeeWage = new EmployeeWage();
        employeeWage.setWage(BigDecimal.TEN);

        return WageMessage.builder()
            .messageBody(employeeWage)
            .messageId(UUID.randomUUID().toString())
            .build();
    }
}