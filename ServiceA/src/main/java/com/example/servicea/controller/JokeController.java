package com.example.servicea.controller;

import com.example.servicea.dto.Joke;
import com.example.servicea.service.JokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController("/joke")
public class JokeController {
    @Autowired
    JokeService jokeService;

    @GetMapping("")
    public ResponseEntity<Joke> getJoke() throws IOException {
        return jokeService.getJokeFromApi();
    }
}
