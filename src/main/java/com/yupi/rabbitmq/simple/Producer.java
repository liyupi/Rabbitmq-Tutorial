package com.yupi.rabbitmq.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.yupi.rabbitmq.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 功能描述：
 *
 * @author Yupi Li
 * @date 2018/10/22 21:32
 */
public class Producer {

    // 队列名称
    private static final String QUQUE_NAME = "simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 建立MQ连接
        Connection connection = ConnectionUtils.newConnection();
        // 创建通道
        Channel channel = connection.createChannel();
        // 创建队列
        channel.queueDeclare(QUQUE_NAME,false,false,false, null);
        // 发布消息
        String msg = "this is my message";
        channel.basicPublish("", QUQUE_NAME, null, msg.getBytes());
        System.out.println("send message succeed");
        channel.close();
        connection.close();
    }

}
