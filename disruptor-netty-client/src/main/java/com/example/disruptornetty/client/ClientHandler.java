package com.example.disruptornetty.client;

import com.example.disruptornetty.common.entity.TranslatorData;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.EventExecutorGroup;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            TranslatorData resp = (TranslatorData) msg;
            log.info("client,id:{},name:{},message:{}", resp.getId(), resp.getName(), resp.getMessage());

        } finally {
            // 释放缓存
            ReferenceCountUtil.release(msg);
        }
    }
}
