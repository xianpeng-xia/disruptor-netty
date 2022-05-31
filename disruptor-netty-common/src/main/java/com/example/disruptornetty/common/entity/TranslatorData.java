package com.example.disruptornetty.common.entity;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TranslatorData implements Serializable {
    private static final long serialVersionUid = 1L;

    private String id;
    private String name;
    /**
     * 消息体内容
     */
    private String message;
}
