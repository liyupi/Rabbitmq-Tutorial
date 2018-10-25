package com.yupi.rabbitmq.route;

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

    private final static String EXCHANGE_NAME = "exchange_direct";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.newConnection();
        Channel channel = connection.createChannel();
        // 声明一个fanout（扇形）类型的交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        String infoMsg = "this is my info message";
        String errorMsg = "this is my error message";
        String debugMsg = "this is my debug message";
        // 发布消息，第二个参数为routing-key，用于匹配特定消费者队列
        channel.basicPublish(EXCHANGE_NAME, "info", null, infoMsg.getBytes());
        channel.basicPublish(EXCHANGE_NAME, "error", null, errorMsg.getBytes());
        channel.basicPublish(EXCHANGE_NAME, "debug", null, debugMsg.getBytes());
        System.out.println("send all messages succeed");
        channel.close();
        connection.close();
    }
}
