package com.example.disruptornetty.common.disruptor;

import com.example.disruptornetty.common.entity.TranslatorData;
import com.example.disruptornetty.common.entity.TranslatorDataWapper;
import com.lmax.disruptor.RingBuffer;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;

@Data
public class MessageProducer {
    private String producerId;
    RingBuffer<TranslatorDataWapper> ringBuffer;


    public MessageProducer(String producerId, RingBuffer<TranslatorDataWapper> ringBuffer) {
        this.producerId = producerId;
        this.ringBuffer = ringBuffer;
    }

    public void onData(TranslatorData data, ChannelHandlerContext ctx) {
        long sequence = ringBuffer.next();
        try {
            TranslatorDataWapper wapper = ringBuffer.get(sequence);
            wapper.setData(data);
            wapper.setCtx(ctx);
        } finally {
            ringBuffer.publish(sequence);
        }

    }
}
