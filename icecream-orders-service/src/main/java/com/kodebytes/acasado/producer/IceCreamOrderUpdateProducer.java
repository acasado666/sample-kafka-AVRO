package com.kodebytes.acasado.producer;

import com.kodebytes.acasado.domain.generated.IceCreamUpdateEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class IceCreamOrderUpdateProducer {

    @Value("${spring.kafka.topic:ice-cream-orders}")
    private String topic;

    KafkaTemplate<String, IceCreamUpdateEvent> kafkaTemplate;

    public IceCreamOrderUpdateProducer(KafkaTemplate<String, IceCreamUpdateEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUpdateMessage(String orderId, IceCreamUpdateEvent iceCreamOrderUpdate) {
        String key = orderId;

        var producerRecord = new ProducerRecord<>(topic, key, iceCreamOrderUpdate);

        CompletableFuture<SendResult<String, IceCreamUpdateEvent>> futureUpdate = kafkaTemplate.send(producerRecord);

        futureUpdate.whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("Failed to update IceCreamOrder | topic={}, key={}, error={}",
                        topic, key, ex.getMessage(), ex);
            } else {
                var metadata = result.getRecordMetadata();
                log.info("Published Updated IceCreamOrder | topic={}, partition={}, offset={}, key={}",
                        metadata.topic(), metadata.partition(), metadata.offset(), key);
            }
        });
    }

}
