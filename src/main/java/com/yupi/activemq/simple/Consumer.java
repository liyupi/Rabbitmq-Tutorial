package com.yupi.activemq.simple;

import com.yupi.activemq.ConnectionUtils;

import javax.jms.*;
import java.io.IOException;

/**
 * @Author: Yupi Li
 * @Date: Created in 15:04 2018/10/25
 * @Description:
 * @Modified By:
 */
public class Consumer {

    private final static String QUEUE_NAME = "simple_queue";

    public static void main(String[] args) throws JMSException, IOException {
        Connection connection = ConnectionUtils.newConnection();
        connection.start();
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(message -> {
            TextMessage textMessage = (TextMessage) message;
            try {
                System.out.println("received message: " + textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });
        System.in.read();
        consumer.close();
        session.close();
        connection.close();
    }
}
