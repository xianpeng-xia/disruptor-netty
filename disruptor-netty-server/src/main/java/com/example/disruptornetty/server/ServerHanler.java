package com.example.disruptornetty.server;

import com.example.disruptornetty.common.entity.TranslatorData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServerHanler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        TranslatorData request = (TranslatorData) msg;
        log.info("server,id:{},name:{},message:{}", request.getId(), request.getName(), request.getMessage());

        TranslatorData response = new TranslatorData();
        response.setId("resp: " + request.getId());
        response.setName("resp: " + request.getName());
        response.setMessage("resp: " + request.getMessage());
        ctx.writeAndFlush(response);
    }
}
