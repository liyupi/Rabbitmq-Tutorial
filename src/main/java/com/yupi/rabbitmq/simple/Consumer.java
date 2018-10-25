package com.yupi.rabbitmq.simple;

import com.rabbitmq.client.*;
import com.yupi.rabbitmq.ConnectionUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * 功能描述：
 *
 * @author Yupi Li
 * @date 2018/10/24 22:11
 */
public class Consumer {

    // 队列名称
    private static final String QUQUE_NAME = "simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        // 创建连接
        Connection connection = ConnectionUtils.newConnection();
        // 创建通道
        Channel channel = connection.createChannel();
        // 声明队列（生产者已创建队列，但是为了保险，再次声明）
        channel.queueDeclare(QUQUE_NAME, false, false, false, null);
        // 创建消费者
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("get Message: " + new String(body, StandardCharsets.UTF_8));
            }
        };
        // 消费
        channel.basicConsume(QUQUE_NAME, true, defaultConsumer);
    }

}
