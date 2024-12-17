package com.coderscampus.Assignment14.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chat {
    private Long id;
    private User user;
    private String content;
    private Long channelId;
}
