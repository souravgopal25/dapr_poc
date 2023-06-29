package com.example.serviceb.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class Message {
    String id;
    String message;
}
