package com.example.serviceb.dapr_service;

import io.dapr.client.DaprClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherService {
    private DaprClient daprClient;
    private static final Logger logger = LoggerFactory.getLogger(StateManagementService.class.getName());
    //this can be anything you want to have
    public static final String PUBSUB_NAME = "pubsub";

    public PublisherService(DaprClient daprClient) {
        this.daprClient = daprClient;
    }

    public <T> void publishEvent(String topicName, T object) {
        daprClient.publishEvent(PUBSUB_NAME, topicName, object).block();
    }

    public <T> void publishBulkEvents(String topicName, List<T> eventList, Class<T> className) {
        //todo add publish bulk events
//        daprClient.publishEvents
    }
}
