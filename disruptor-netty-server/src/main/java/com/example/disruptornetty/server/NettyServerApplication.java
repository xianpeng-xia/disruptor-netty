package com.example.disruptornetty.server;

import com.example.disruptornetty.common.disruptor.MessageConsumer;
import com.example.disruptornetty.common.disruptor.RingBufferWorkerPoolFactory;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NettyServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyServerApplication.class, args);

        MessageConsumer[] messageConsumers = new MessageConsumer[4];
        for (int i = 0; i < messageConsumers.length; i++) {
            messageConsumers[i] = new MessageConsumerImpl4Server("code:serverId::" + i);
        }
        RingBufferWorkerPoolFactory.getInstance().initAndStart(ProducerType.MULTI,
                1024 * 1024,
                new BlockingWaitStrategy(),
                messageConsumers);

        new NettyServer();
    }

}
