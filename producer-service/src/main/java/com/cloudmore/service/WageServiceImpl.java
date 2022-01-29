package com.cloudmore.service;

import com.cloudmore.configuration.KafkaConfigurationProperties;
import com.cloudmore.dto.WageRequest;
import com.cloudmore.exception.ProducerException;
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
    private final KafkaTemplate<String, WageRequest> kafkaTemplate;

    @Override
    public String saveWage(WageRequest wageRequest) {
        val messageId = UUID.randomUUID().toString();
        try {
            val producerRecord = new ProducerRecord<>(
                kafkaProperties.getTopic(), messageId, wageRequest);
            kafkaTemplate.send(producerRecord);
            log.info("Message sent with id-> {}", messageId);
        } catch (Exception e) {
            throw new ProducerException(e);
        }
        return messageId;
    }
}
