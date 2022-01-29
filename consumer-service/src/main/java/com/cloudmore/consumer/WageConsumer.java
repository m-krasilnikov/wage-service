package com.cloudmore.consumer;

import com.cloudmore.model.EmployeeWage;
import com.cloudmore.model.WageMessage;
import com.cloudmore.service.EmployeeWageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WageConsumer{
    private final ObjectMapper om;
    private final EmployeeWageService employeeWageService;

    @KafkaListener(topics = "${spring.kafka.topic}")
    public void consume(
        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
        @Payload String payload)
    {
        log.info("consumed message with key: [ {} ]", key);
        convert(key, payload).ifPresent(employeeWageService::saveWage);
    }

    private Optional<WageMessage> convert(String key, String payload) {
        try {
            val employeeWage = om.readValue(payload, EmployeeWage.class);
            val wageMessage = WageMessage.builder()
                .messageBody(employeeWage)
                .messageId(key)
                .build();
            return Optional.of(wageMessage);
        } catch (JsonProcessingException e) {
            log.error("can't parse incoming message with key: {}", key);
            //TODO: Add logic to send the message in the DB or in the another queue
        }
        return Optional.empty();
    }
}
