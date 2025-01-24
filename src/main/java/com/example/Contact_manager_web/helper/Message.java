package com.example.Contact_manager_web.helper;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private String content;
    @Builder.Default
    private  MessageType type = MessageType.blue;
}
