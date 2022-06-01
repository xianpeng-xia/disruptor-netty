package com.example.disruptornetty.client;

import com.example.disruptornetty.common.disruptor.MessageConsumer;
import com.example.disruptornetty.common.entity.TranslatorData;
import com.example.disruptornetty.common.entity.TranslatorDataWapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageConsumerImpl4Client extends MessageConsumer {
    public MessageConsumerImpl4Client(String consumerId) {
        super(consumerId);
    }

    @Override
    public void onEvent(TranslatorDataWapper event) throws Exception {
        TranslatorData resp = event.getData();
        ChannelHandlerContext ctx = event.getCtx();
        try {
            log.info("client,id:{},name:{},message:{}", resp.getId(), resp.getName(), resp.getMessage());

        } finally {
            // 释放缓存
            ReferenceCountUtil.release(resp);
        }
    }
}
