package com.coderscampus.Assignment14.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("username")
    private String username;
}
