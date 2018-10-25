package com.yupi.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

/**
 * @Author: Yupi Li
 * @Date: Created in 15:05 2018/10/25
 * @Description:
 * @Modified By:
 */
public class ConnectionUtils {

    private static ConnectionFactory connectionFactory;

    static {
        connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
    }

    public static Connection newConnection() throws JMSException {
        return connectionFactory.createConnection();
    }
}
