package com.example.serviceb.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Joke {
    String id;
    String punchline;
    String setup;
    String type;
}
