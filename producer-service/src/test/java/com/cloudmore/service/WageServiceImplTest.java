package com.cloudmore.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.cloudmore.configuration.KafkaConfigurationProperties;
import com.cloudmore.dto.WageRequest;
import com.cloudmore.exception.ProducerException;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

@ExtendWith(MockitoExtension.class)
class WageServiceImplTest {
    @Mock
    private KafkaConfigurationProperties kafkaProperties;
    @Mock
    private KafkaTemplate<String, WageRequest> kafkaTemplate;
    @InjectMocks
    private WageServiceImpl subject;

    @Test
    void saveWageShouldPass() {
        doReturn("topic").when(kafkaProperties).getTopic();

        subject.saveWage(new WageRequest());

        verify(kafkaProperties, times(1)).getTopic();
        verify(kafkaTemplate, times(1)).send(any(ProducerRecord.class));
    }

    @Test
    void saveWageShouldThrow() {
        doReturn("topic").when(kafkaProperties).getTopic();
        doThrow(RuntimeException.class).when(kafkaTemplate).send(any(ProducerRecord.class));

        assertThrows(ProducerException.class, () -> subject.saveWage(new WageRequest()));
    }

}