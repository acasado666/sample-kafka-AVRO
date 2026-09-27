package com.kodebytes.acasado.consumer;

import com.kodebytes.acasado.domain.generated.IceCreamOrder;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class IceCreamOrdersConsumer {

    @KafkaListener(
            topics = {"${spring.kafka.topic:ice-cream-orders}"}
            , autoStartup = "${iceCreamOrdersConsumer.startup:true}"
            , groupId = "${spring.kafka.consumer.group-id:icecream-orders-listener-group}")
    public void onMessage(ConsumerRecord<String, IceCreamOrder> consumerRecord) {
        log.info("ConsumerRecord key: {} , value: {} ", consumerRecord.key(), consumerRecord.value());
    }
}
