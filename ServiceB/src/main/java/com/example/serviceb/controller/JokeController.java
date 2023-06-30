package com.example.serviceb.controller;

import com.example.serviceb.model.Joke;
import com.example.serviceb.model.Message;
import com.example.serviceb.service.JokeService;
import io.dapr.client.DaprClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController()
@RequestMapping("/joke")
public class JokeController {
    private static final Logger logger = LoggerFactory.getLogger(JokeService.class.getName());
    @Autowired
    JokeService jokeService;

    @Autowired
    DaprClient daprClient;
    public static final String PUBSUB_NAME = "pubsub";
    public static final String TOPIC_NAME = "exampleTopic";

    @GetMapping("")
    public ResponseEntity<Joke> getJoke() throws Exception {
        return jokeService.getJokeFromApi();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Joke> getJokeFromId(@PathVariable Long id) {
        return jokeService.getJokeFromId(id);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Joke>> getAllSavedJoke() throws IOException {
        return jokeService.getAllSavedJokeFromApi();
    }

}
