package com.yupi.rabbitmq.work;

import com.rabbitmq.client.*;
import com.yupi.rabbitmq.MQConnectionUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * 功能描述：
 *
 * @author Yupi Li
 * @date 2018/10/25 10:06
 */
public class Consumer2 {

    private final static String QUEUE_NAME = "work_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = MQConnectionUtils.newConnection();
        Channel channel = connection.createChannel();
        // 每个消费者发送确认消息前，最多向其发送1条消息，限制消费者每次只能处理1条消息
        int prefetchCount = 1;
        channel.basicQos(prefetchCount);
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("consumer 2 received " + new String(body, StandardCharsets.UTF_8));
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 手动应答
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        // ack设置为true，则是自动应答，如果使用公平消费，则关闭ack
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, defaultConsumer);
    }


}
