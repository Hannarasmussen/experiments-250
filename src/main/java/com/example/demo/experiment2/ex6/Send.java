
package com.example.demo.experiment2.ex6;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

public class Send {
    private static final String QUEUE_NAME = "polls";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
                Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Hello World!";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");

        }

    }

    // public static Channel createChannel(Connection connection) throws Exception {
    // Channel channel = connection.createChannel();
    // channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    // return channel;
    // }
}