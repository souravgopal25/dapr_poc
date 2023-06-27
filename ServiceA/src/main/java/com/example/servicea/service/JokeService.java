package com.example.servicea.service;

import com.example.servicea.dto.Joke;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class JokeService {

    public ResponseEntity<Joke> getJokeFromApi() throws IOException {
        List<Joke> jokeList = new ArrayList<>();
        AsyncHttpClient client = new DefaultAsyncHttpClient();
        client.prepare("GET", "https://dad-jokes.p.rapidapi.com/random/joke")
                .setHeader("X-RapidAPI-Key", "4c7d83c2a8mshda0dfea48bb7428p12bcc2jsn43fac042d372")
                .setHeader("X-RapidAPI-Host", "dad-jokes.p.rapidapi.com")
                .execute()
                .toCompletableFuture()
                .thenAccept(response -> {
                    JSONArray body = new JSONObject(response).getJSONArray("body");
                    JSONObject jokeJson = body.getJSONObject(0);

                    Joke joke = Joke.builder()
                            .id(jokeJson.getString("_id"))
                            .punchline(jokeJson.getString("punchline"))
                            .setup(jokeJson.getString("setup"))
                            .type(jokeJson.getString("type"))
                            .build();
                    jokeList.add(joke);
                })
                .join();
        client.close();
        return new ResponseEntity<Joke>(jokeList.get(0), HttpStatus.OK);
    }

}
