package com.yupi.rabbitmq.topic;

import com.rabbitmq.client.*;
import com.yupi.rabbitmq.ConnectionUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * 功能描述：
 *
 * @author Yupi Li
 * @date 2018/10/25 15:55
 */
public class Consumer2 {

    private final static String EXCHANGE_NAME = "exchange_topic";

    private final static String QUEUE_NAME = "ps_queue2";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 第三个参数为routing-key
        // # 匹配0至多个单词
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "china.#");
        // * 必须且只能匹配1个单词
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "usa.*");
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("consumer2 with routing-key 'china.#&usa.*' received message:"
                        + new String(body, StandardCharsets.UTF_8));
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
