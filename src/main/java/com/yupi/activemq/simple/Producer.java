package com.yupi.activemq.simple;

import com.yupi.activemq.ConnectionUtils;

import javax.jms.*;

/**
 * @Author: Yupi Li
 * @Date: Created in 14:44 2018/10/25
 * @Description:
 * @Modified By:
 */
public class Producer {

    private final static String QUEUE_NAME = "simple_queue";

    public static void main(String[] args) throws JMSException {
        // 创建连接
        Connection connection = ConnectionUtils.newConnection();
        // 开启连接
        connection.start();
        // 创建会话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 创建队列
        Queue queue = session.createQueue(QUEUE_NAME);
        // 创建生产者
        MessageProducer producer = session.createProducer(queue);
        // 创建消息
        TextMessage textMessage = session.createTextMessage("this is my message");
        // 发送消息
        producer.send(textMessage);
        // 关闭连接
        producer.close();
        session.close();
        connection.close();
    }
}
