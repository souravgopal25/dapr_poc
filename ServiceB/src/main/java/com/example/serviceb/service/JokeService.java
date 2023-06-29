package com.example.serviceb.service;

import com.example.serviceb.model.Joke;
import com.example.serviceb.model.Message;
import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;
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
    private DaprClient daprClient;
    private StateManagementService stateManagementService;

    public JokeService(DaprClient daprClient, StateManagementService stateManagementService) {
        this.daprClient = daprClient;
        this.stateManagementService = stateManagementService;
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
        Joke response = daprClient.invokeMethod("serviceA", "joke", null, HttpExtension.GET, Joke.class).block();
        if (response != null) {
            response.setId(String.valueOf(counter.incrementAndGet()));
            response.setType("Service B");
            //stateManagementService.saveState(response.getId(), response);
            logger.info(response.toString());
            daprClient.publishEvent(PUBSUB_NAME, TOPIC_NAME,
                    Message.builder()
                            .message("Response Received Success")
                            .id(response.getId()).build()).block();
        } else {
            throw new Exception("Joke Not found");
        }

        return new ResponseEntity<Joke>(response, HttpStatus.OK);
    }
}
