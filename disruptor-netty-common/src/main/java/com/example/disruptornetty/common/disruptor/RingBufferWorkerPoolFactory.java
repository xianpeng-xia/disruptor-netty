package com.example.disruptornetty.common.disruptor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

import com.example.disruptornetty.common.entity.TranslatorDataWapper;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.WorkerPool;
import com.lmax.disruptor.dsl.ProducerType;

public class RingBufferWorkerPoolFactory {

    private Map<String, MessageProducer> producers = new ConcurrentHashMap<>();
    private Map<String, MessageConsumer> consumers = new ConcurrentHashMap<>();
    private RingBuffer<TranslatorDataWapper> ringBuffer;
    private SequenceBarrier sequenceBarrier;
    private WorkerPool<TranslatorDataWapper> workerPool;

    /**
     * 静态内部类单例
     */
    private static class SingletonHolder {
        static final RingBufferWorkerPoolFactory instance = new RingBufferWorkerPoolFactory();
    }

    private RingBufferWorkerPoolFactory() {
    }

    public static RingBufferWorkerPoolFactory getInstance() {
        return SingletonHolder.instance;
    }

    public void initAndStart(ProducerType type, int bufferSize, WaitStrategy waitStrategy, MessageConsumer[] messageConsumers) {
        // 1 创建RingBuffer对象
        this.ringBuffer = RingBuffer.create(type,
                new EventFactory<TranslatorDataWapper>() {
                    @Override
                    public TranslatorDataWapper newInstance() {
                        return new TranslatorDataWapper();
                    }
                },
                bufferSize,
                waitStrategy);
        // 2 设置序号栅栏
        this.sequenceBarrier = this.ringBuffer.newBarrier();
        // 3 设置工作池
        this.workerPool = new WorkerPool<TranslatorDataWapper>(this.ringBuffer,
                this.sequenceBarrier,
                new EventExceptionHandler(),
                messageConsumers);

        // 4 把构建的消费者放入池中
        for (MessageConsumer mc : messageConsumers) {
            this.consumers.put(mc.getConsumerId(), mc);
        }
        // 5 添加Sequence
        this.ringBuffer.addGatingSequences(this.workerPool.getWorkerSequences());
        // 6 启动工作池
        this.workerPool.start(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() / 2));
    }

    public MessageProducer getMessageProducer(String producerId) {
        MessageProducer messageProducer = this.producers.get(producerId);
        if (messageProducer == null) {
            messageProducer = new MessageProducer(producerId, this.ringBuffer);
            this.producers.put(producerId, messageProducer);
        }
        return messageProducer;
    }

    /**
     * 异常类
     */
    public static class EventExceptionHandler implements ExceptionHandler<TranslatorDataWapper> {

        @Override
        public void handleEventException(Throwable ex, long sequence, TranslatorDataWapper event) {

        }

        @Override
        public void handleOnStartException(Throwable ex) {

        }

        @Override
        public void handleOnShutdownException(Throwable ex) {

        }
    }
}
