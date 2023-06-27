package com.example.serviceb.service;

import com.example.serviceb.model.Joke;
import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;
import io.dapr.client.domain.HttpExtension;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.net.http.HttpClient;
import java.time.Duration;

@Service
public class JokeService {
    String serviceAPath = "http://localhost:8080/joke";
    private static final Logger logger = LoggerFactory.getLogger(JokeService.class.getName());

//    //private DaprClient daprClient;
//
//    public JokeService(final DaprClient daprClient) {
//        this.daprClient = daprClient;
//    }


    public ResponseEntity<Joke> getJokeFromApi() throws IOException {

//        DaprClient daprClient = new DaprClientBuilder().build();
//        List<Joke> jokeList = new ArrayList<>();
//        AsyncHttpClient client = new DefaultAsyncHttpClient();
//        client.prepare("GET", serviceAPath)
//                .execute()
//                .toCompletableFuture()
//                .thenAccept(response -> {
//                            JSONObject jsonObject = new JSONObject(response.getResponseBody());
//                            jokeList.add(
//                                    Joke.builder()
//                                            .id(jsonObject.getString("id"))
//                                            .punchline(jsonObject.getString("punchline"))
//                                            .setup(jsonObject.getString("setup"))
//                                            .type(jsonObject.getString("type"))
//                                            .build());
//                        }
//                ).join();
//        client.close();
//        return new ResponseEntity<Joke>(jokeList.get(0), HttpStatus.OK);
//        String dapr_url = "http://localhost:" + DAPR_HTTP_PORT + "/joke";
//        DaprClient daprClient = new DaprClientBuilder().build();
//        Mono<String> stringMono = daprClient.invokeMethod("serviceA", "/joke", null, HttpExtension.GET, String.class);
//        System.out.println(stringMono.toString());
        logger.info("Get Joke Hit");

        DaprClient daprClient = new DaprClientBuilder().build();
        Joke response = daprClient.invokeMethod("serviceA", "joke", null, HttpExtension.GET, Joke.class).block();
        System.out.println(response);
        return new ResponseEntity<Joke>(response, HttpStatus.OK);

    }
}
