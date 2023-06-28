package com.example.serviceb.controller;

import com.example.serviceb.model.Joke;
import com.example.serviceb.service.JokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController()
@RequestMapping("/joke")
public class JokeController {
    @Autowired
    JokeService jokeService;

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
