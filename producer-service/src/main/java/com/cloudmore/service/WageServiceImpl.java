package com.cloudmore.service;

import com.cloudmore.configuration.KafkaConfigurationProperties;
import com.cloudmore.domain.Message;
import com.cloudmore.dto.WageRequest;
import com.cloudmore.exception.ProducerException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WageServiceImpl implements WageService {
    private final KafkaConfigurationProperties kafkaProperties;
    private final KafkaTemplate<String, Message> kafkaTemplate;
    private final ObjectMapper om;

    @Override
    public String saveWage(WageRequest wageRequest) {
        val messageId = UUID.randomUUID();
        val message = new Message(messageId, wageRequest);
        try {
            val producerRecord = new ProducerRecord<>(
                kafkaProperties.getTopic(), messageId.toString(), message);
            kafkaTemplate.send(producerRecord);
            log.info("Message sended -> {}", om.writeValueAsString(wageRequest));
        } catch (Exception e) {
            throw new ProducerException(e);
        }
        return messageId.toString();
    }
}
