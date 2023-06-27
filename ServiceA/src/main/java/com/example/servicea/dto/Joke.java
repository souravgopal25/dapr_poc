package com.example.servicea.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Joke {
    String id;
    String punchline;
    String setup;
    String type;
}
