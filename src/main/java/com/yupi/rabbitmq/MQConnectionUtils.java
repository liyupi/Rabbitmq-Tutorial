package com.yupi.rabbitmq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 功能描述：
 *
 * @author Yupi Li
 * @date 2018/10/22 21:35
 */
public class MQConnectionUtils {

    public static Connection newConnection() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setUsername("liyupi");
        factory.setPassword("123456");
        factory.setPort(5672);
        factory.setVirtualHost("/vhost_test");
        return factory.newConnection();
    }
}
