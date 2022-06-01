package com.example.disruptornetty.common.entity;

import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TranslatorDataWapper {
    private TranslatorData data;
    private ChannelHandlerContext ctx;

    public TranslatorDataWapper(TranslatorData data, ChannelHandlerContext ctx) {
        this.data = data;
        this.ctx = ctx;
    }

    public TranslatorDataWapper() {
    }
}
