package com.yupi.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

/**
 * 功能描述：Confirm模式
 *
 * @author Yupi Li
 * @date 2018/10/25 19:23
 */
public class ConfirmProducer {

    private final static String QUEUE_NAME = "test_queue_confirm";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtils.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String msg = "this is my message";
        // 设置channel为confirm模式（禁止和事务模式同时使用）
        channel.confirmSelect();
        // 普通模式
        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        System.out.println("ordinary mode");
        if (!channel.waitForConfirms()) {
            System.out.println("message send failed");
        } else {
            System.out.println("message send succeed");
        }
        // 批量模式
        for (int i = 0; i < 10; i++) {
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        }
        System.out.println("batch mode");
        if (!channel.waitForConfirms()) {
            System.out.println("messages send failed");
        } else {
            System.out.println("all messages send succeed");
        }
        // 异步模式
        // 确认的消息标识集合
        final SortedSet<Long> confirmSet = Collections.synchronizedSortedSet(new TreeSet<>());
        // 通道添加监听
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                if (multiple) {
                    System.out.println("handleACK multiple");
                    confirmSet.headSet(deliveryTag + 1).clear();
                } else {
                    System.out.println("handleACK not multiple");
                    confirmSet.remove(deliveryTag);
                }
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                if (multiple) {
                    System.out.println("handleNack multiple");
                    confirmSet.headSet(deliveryTag + 1).clear();
                } else {
                    System.out.println("handleNack not multiple");
                    confirmSet.remove(deliveryTag);
                }
            }
        });

        for (int i = 0; i < 1000000; i++) {
            // 获取下一个标识
            long seqNo = channel.getNextPublishSeqNo();
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            System.out.println("new seqNo: " + seqNo);
            confirmSet.add(seqNo);
        }
        channel.close();
        connection.close();
    }
}
