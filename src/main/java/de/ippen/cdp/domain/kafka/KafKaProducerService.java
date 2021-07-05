package de.ippen.cdp.domain.kafka;

import de.ippen.cdp.avro.User;
import de.ippen.cdp.infraestructure.entrypoint.request.UserRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaSendCallback;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.UUID;

@Slf4j
@Service
public class KafKaProducerService {

    private final KafkaTemplate<String, User> kafkaTemplate;

    @Value("${app.config.topic-name}")
    private String topic;

    public KafKaProducerService(KafkaTemplate<String, User> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUserTemplate(UserRequest userRequest) {

        User user = User.newBuilder()
                        .setFirstName(userRequest.getFirstName())
                        .setLastName(userRequest.getLastName()).build();

        ListenableFuture<SendResult<String, User>> future = this.kafkaTemplate.send(topic, UUID.randomUUID().toString(), user);

        future.addCallback(new KafkaSendCallback<String, User>() {
            @Override
            public void onSuccess(SendResult<String, User> result) {
                log.info("ok send ");
                log.info("ok FirstName = {}", result.getProducerRecord().value().getFirstName());
                log.error("ok Key = {}", result.getProducerRecord().key());
                log.info("ok LastName = {}", result.getProducerRecord().value().getLastName());
                log.info("ok send offset = {}", result.getRecordMetadata().offset());
                log.info("ok send key size = {}", result.getRecordMetadata().serializedKeySize());
                log.info("ok send value size = {}", result.getRecordMetadata().serializedValueSize());
            }

            @Override
            public void onFailure(KafkaProducerException e) {
                log.error("onFailure  ", e);
            }

        });
    }

}
