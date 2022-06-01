package com.example.disruptornetty.server;


import com.example.disruptornetty.common.disruptor.MessageProducer;
import com.example.disruptornetty.common.disruptor.RingBufferWorkerPoolFactory;
import com.example.disruptornetty.common.entity.TranslatorData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "ServerHanler")
public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        /*
        TranslatorData request = (TranslatorData) msg;
        log.info("server,id:{},name:{},message:{}", request.getId(), request.getName(), request.getMessage());

        TranslatorData response = new TranslatorData();
        response.setId("resp: " + request.getId());
        response.setName("resp: " + request.getName());
        response.setMessage("resp: " + request.getMessage());
        ctx.writeAndFlush(response);
        */
        TranslatorData request = (TranslatorData) msg;
        // 应用服务自身的id生产规则
        String producerId = "code:sessionId:001";
        MessageProducer messageProducer = RingBufferWorkerPoolFactory.getInstance().getMessageProducer(producerId);
        messageProducer.onData(request,ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("ex:", cause);
    }
}
