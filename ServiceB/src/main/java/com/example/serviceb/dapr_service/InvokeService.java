package com.example.serviceb.dapr_service;

import io.dapr.client.DaprClient;
import io.dapr.client.domain.HttpExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.javapoet.ClassName;
import org.springframework.stereotype.Service;

@Service
public class InvokeService {
    private DaprClient daprClient;
    private static final Logger logger = LoggerFactory.getLogger(StateManagementService.class.getName());

    public InvokeService(DaprClient daprClient) {
        this.daprClient = daprClient;
    }

    public <T> T invokeMethod(String serviceName, String method, HttpExtension httpExtension, Class<T> className) {
        logger.info("Invoking Method " + serviceName + " " + method);
        //request body is null
        return daprClient.invokeMethod(serviceName, method, null, httpExtension, className).block();
    }

}
