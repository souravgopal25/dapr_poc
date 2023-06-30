package com.example.serviceb.service;

import com.example.serviceb.dapr_service.InvokeService;
import com.example.serviceb.dapr_service.PublisherService;
import com.example.serviceb.dapr_service.StateManagementService;
import com.example.serviceb.model.Joke;
import com.example.serviceb.model.Message;
import io.dapr.client.DaprClient;
import io.dapr.client.domain.HttpExtension;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class JokeService {
    //    String serviceAPath = "http://localhost:8080/joke";
    private static final Logger logger = LoggerFactory.getLogger(JokeService.class.getName());
    public static final String PUBSUB_NAME = "pubsub";
    public static final String TOPIC_NAME = "exampleTopic";

    private InvokeService invokeService;
    private StateManagementService stateManagementService;
    private PublisherService publisherService;

    public JokeService(InvokeService invokeService, StateManagementService stateManagementService, PublisherService publisherService) {
        this.invokeService = invokeService;
        this.stateManagementService = stateManagementService;
        this.publisherService = publisherService;
    }

    private final AtomicLong counter = new AtomicLong();

    public ResponseEntity<List<Joke>> getAllSavedJokeFromApi() throws IOException {
        List<String> keyList = new ArrayList<>();
        for (Long i = 0L; i < counter.get(); i++) {
            keyList.add(String.valueOf(i));
        }
        List<Joke> jokeList = stateManagementService.getBulkState(keyList, Joke.class);
        return new ResponseEntity<List<Joke>>(jokeList, HttpStatus.OK);
    }

    public ResponseEntity<Joke> getJokeFromId(Long id) {
        Joke joke = stateManagementService.getState(String.valueOf(id), Joke.class);
        return new ResponseEntity<Joke>(joke, HttpStatus.OK);
    }

    public ResponseEntity<Joke> getJokeFromApi() throws Exception {
        logger.info("Get Joke Hit");
        Joke response = invokeService.invokeMethod("serviceA", "joke", HttpExtension.GET, Joke.class);
        if (response != null) {
            response.setId(String.valueOf(counter.incrementAndGet()));
            response.setType("Service B");
            stateManagementService.saveState(response.getId(), response);
            logger.info(response.toString());
            publisherService.publishEvent(TOPIC_NAME, Message.builder()
                    .message("Response Received Success")
                    .id(response.getId()).build());
        } else {
            throw new Exception("Joke Not found");
        }

        return new ResponseEntity<Joke>(response, HttpStatus.OK);
    }
}
