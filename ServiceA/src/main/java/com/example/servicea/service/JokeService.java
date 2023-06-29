package com.example.servicea.service;

import com.example.servicea.dto.Joke;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class JokeService {

    private static final Logger logger = LoggerFactory.getLogger(JokeService.class.getName());

    public ResponseEntity<Joke> getJokeFromApi() throws IOException {
        List<Joke> jokeList = new ArrayList<>();
        AsyncHttpClient client = new DefaultAsyncHttpClient();
        client.prepare("GET", "https://backend-omega-seven.vercel.app/api/getjoke")
                .execute()
                .toCompletableFuture()
                .thenAccept(response -> {
                    //logger.info(String.valueOf(response));
                    JSONArray body = new JSONArray(response.getResponseBody());
                    JSONObject jokeJson = body.getJSONObject(0);
                    Joke joke = Joke.builder()
                            .id("Sample Id")
                            .punchline(jokeJson.getString("punchline"))
                            .setup(jokeJson.getString("question"))
                            .type("Service A")
                            .build();
                    jokeList.add(joke);
                })
                .join();
        client.close();
        return new ResponseEntity<Joke>(jokeList.get(0), HttpStatus.OK);
    }

}
