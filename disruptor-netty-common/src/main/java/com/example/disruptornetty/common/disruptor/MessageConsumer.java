package com.example.disruptornetty.common.disruptor;

import com.example.disruptornetty.common.entity.TranslatorDataWapper;
import com.lmax.disruptor.WorkHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public abstract class MessageConsumer implements WorkHandler<TranslatorDataWapper> {
    private String consumerId;

    public MessageConsumer(String consumerId) {
        this.consumerId = consumerId;
    }
}
