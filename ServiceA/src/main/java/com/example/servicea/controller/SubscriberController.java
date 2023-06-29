package com.example.servicea.controller;

import com.example.servicea.dto.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dapr.Topic;
import io.dapr.client.domain.CloudEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class SubscriberController {
    private static final Logger logger = LoggerFactory.getLogger(SubscriberController.class.getName());

    @Topic(name = "exampleTopic", pubsubName = "pubsub")
    @PostMapping(path = "/exampleTopic", consumes = MediaType.ALL_VALUE)
    public Mono<ResponseEntity> getSuccessMesage(@RequestBody(required = false) CloudEvent<String> cloudEvent) {
        logger.info("Subscriber Hit");
        return Mono.fromRunnable(() -> {
            try {
                logger.info("Subscription Recieved");
                logger.info(new ObjectMapper().writeValueAsString(cloudEvent));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
