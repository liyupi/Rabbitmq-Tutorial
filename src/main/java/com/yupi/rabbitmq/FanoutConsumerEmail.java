package com.yupi.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * 功能描述：
 *
 * @author Yupi Li
 * @date 2018/10/22 21:40
 */
public class FanoutConsumerEmail {

    // 队列名称
    private static final String EMAIL_QUEUE = "email_queue_fanout";

    // 交换机名称
    private static final String DESTINATION_NAME = "my_fanout_destination";


    public static void main(String[] args) throws IOException, TimeoutException {
        // 建立MQ连接
        Connection connection = MQConnectionUtils.newConnection();
        // 创建通道
        Channel channel = connection.createChannel();
        // 声明消费者队列
        channel.queueDeclare(EMAIL_QUEUE, false, false, false, null);
        // 绑定交换机
        channel.queueBind(EMAIL_QUEUE, DESTINATION_NAME, "");
        // 消费监听消息
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, StandardCharsets.UTF_8);
                System.out.println("邮件消费者获取生产消息：" + msg);
            }
        };
        channel.basicConsume(EMAIL_QUEUE,consumer);
    }
}
