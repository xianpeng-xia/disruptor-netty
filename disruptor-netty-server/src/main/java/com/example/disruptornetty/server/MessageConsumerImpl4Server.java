package com.example.disruptornetty.server;

import com.example.disruptornetty.common.disruptor.MessageConsumer;
import com.example.disruptornetty.common.entity.TranslatorData;
import com.example.disruptornetty.common.entity.TranslatorDataWapper;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageConsumerImpl4Server extends MessageConsumer {
    public MessageConsumerImpl4Server(String consumerId) {
        super(consumerId);
    }

    @Override
    public void onEvent(TranslatorDataWapper event) throws Exception {
        // 业务逻辑处理
        TranslatorData request = (TranslatorData) event.getData();
        ChannelHandlerContext ctx = event.getCtx();

        log.info("server,id:{},name:{},message:{}", request.getId(), request.getName(), request.getMessage());
        // 回复响应信息
        TranslatorData response = new TranslatorData();
        response.setId("resp: " + request.getId());
        response.setName("resp: " + request.getName());
        response.setMessage("resp: " + request.getMessage());

        ctx.writeAndFlush(response);
    }
}
