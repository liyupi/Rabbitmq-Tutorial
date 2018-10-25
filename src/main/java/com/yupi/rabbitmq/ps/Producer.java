package com.yupi.rabbitmq.ps;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.yupi.rabbitmq.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 功能描述：
 *
 * @author Yupi Li
 * @date 2018/10/25 12:35
 */
public class Producer {

    private final static String EXCHANGE_NAME = "exchange_fanout";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.newConnection();
        Channel channel = connection.createChannel();
        // 声明一个fanout（扇形）类型的交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        String msg = "this is my message";
        // 发布消息
        channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());
        System.out.println("send message succeed");
        channel.close();
        connection.close();
    }
}
