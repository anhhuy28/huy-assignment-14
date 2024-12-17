package com.coderscampus.Assignment14.domain;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Channel {
    private Long id;
    private String name;
    private List<Chat> messages;
}
