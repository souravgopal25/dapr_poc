package com.example.serviceb.dapr_service;

import io.dapr.client.DaprClient;
import io.dapr.client.domain.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@Service
public class StateManagementService {
    private DaprClient daprClient;
    private static final String STATE_STORE_NAME = "statestore";
    private static final Logger logger = LoggerFactory.getLogger(StateManagementService.class.getName());

    public StateManagementService(DaprClient daprClient) {
        this.daprClient = daprClient;
    }

    public <T> void saveState(String key, T value) {
        daprClient.saveState(STATE_STORE_NAME, key, value).subscribe(
                response -> {
                    logger.info("Object Saved success", value.toString());
                },
                error -> {
                    logger.error("Error while saving state", value.toString());
                },
                () -> {
                    logger.info("Saving State Operation Completed");
                }
        );
    }

    public <T> T getState(String key, Class<T> className) {
        return daprClient.getState(STATE_STORE_NAME, key, className).map(State::getValue).block();
    }

    public <T> List<T> getBulkState(List<String> keyList, Class<T> className) {
        List<T> items = new ArrayList<>();
        List<State<T>> jokeMonoStateList = daprClient.getBulkState(STATE_STORE_NAME, keyList, className).flatMapMany(Flux::fromIterable).collectList().block();
        jokeMonoStateList
                .stream()
                .filter(itemState -> itemState.getValue() != null)
                .forEach(itemState -> {
                    items.add(itemState.getValue());
                });
        return items;
    }


}
