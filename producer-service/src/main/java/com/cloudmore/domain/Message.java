package com.cloudmore.domain;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Message {
    private UUID messageId;
    private Object payload;
}
