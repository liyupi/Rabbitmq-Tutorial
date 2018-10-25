package com.yupi.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 功能描述：事务模式
 *
 * @author Yupi Li
 * @date 2018/10/25 18:50
 */
public class TxProducer {

    private final static String QUEUE_NAME = "test_queue_tx";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String msg = "this is my message";
        try {
            // 设置channel为事务模式
            channel.txSelect();
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            int i = 1 / 0;
            channel.txCommit();
        } catch (Exception e) {
            channel.txRollback();
            System.out.println("send message rollback");
        }
    }

}
