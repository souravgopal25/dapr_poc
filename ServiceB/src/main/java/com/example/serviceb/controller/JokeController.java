package com.example.serviceb.controller;

import com.example.serviceb.model.Joke;
import com.example.serviceb.service.JokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController()
@RequestMapping("/joke")
public class JokeController {
    @Autowired
    JokeService jokeService;

    @GetMapping("")
    public ResponseEntity<Joke> getJoke() throws IOException {
        return jokeService.getJokeFromApi();
    }
}
