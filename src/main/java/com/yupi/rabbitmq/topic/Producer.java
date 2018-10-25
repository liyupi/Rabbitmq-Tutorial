package com.yupi.rabbitmq.topic;

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

    private final static String EXCHANGE_NAME = "exchange_topic";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.newConnection();
        Channel channel = connection.createChannel();
        // 声明一个fanout（扇形）类型的交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        String china1Msg = "this is my china.beijing message";
        String china2Msg = "this is my china.shanghai.yugarden message";
        String usaMsg = "this is my usa.town message";
        // 发布消息，第二个参数为routing-key，用于匹配特定消费者队列
        channel.basicPublish(EXCHANGE_NAME, "china.beijing", null, china1Msg.getBytes());
        channel.basicPublish(EXCHANGE_NAME, "china.shanghai.yugarden", null, china2Msg.getBytes());
        channel.basicPublish(EXCHANGE_NAME, "usa.town", null, usaMsg.getBytes());
        System.out.println("send all messages succeed");
        channel.close();
        connection.close();
    }
}
