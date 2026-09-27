package com.kodebytes.acasado.producer;

import com.kodebytes.acasado.domain.generated.IceCreamOrder;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class IceCreamOrderProducer {

    @Value("${spring.kafka.topic:ice-cream-orders}")
    private String topic;

    KafkaTemplate<String, IceCreamOrder> kafkaTemplate;

    public IceCreamOrderProducer(KafkaTemplate<String, IceCreamOrder> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(IceCreamOrder iceCreamOrder) {
        String key = iceCreamOrder.getId().toString();
        log.info("Sending OrderEvent to topic={}, key={}", topic, key);

        var producerRecord = new ProducerRecord<>(topic, key, iceCreamOrder);

        CompletableFuture<SendResult<String, IceCreamOrder>> future = kafkaTemplate.send(producerRecord);

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("Failed to publish IceCreamOrder | topic={}, key={}, error={}",
                        topic, key, ex.getMessage(), ex);
            } else {
                var metadata = result.getRecordMetadata();
                log.info("Published IceCreamOrder | topic={}, partition={}, offset={}, key={}",
                        metadata.topic(), metadata.partition(), metadata.offset(), key);
            }
        });
    }
}
