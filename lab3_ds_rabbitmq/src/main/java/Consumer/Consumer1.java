package Consumer;

import com.rabbitmq.client.*;
import enums.ExchangeType;
import enums.RouteTypes;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Consumer1 {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        //factory.setUsername("");
        //factory.setPassword("");
        //factory.setVirtualHost("");
        factory.setHost("51.83.68.66");
        factory.setPort(5672);
        Connection conn = factory.newConnection();

        if (conn != null) {
            Channel channel = conn.createChannel();
            channel.exchangeDeclare(ExchangeType.EXCHANGE_NAME, BuiltinExchangeType.TOPIC,true);
            String queueName = channel.queueDeclare().getQueue();
            // Queue
            channel.queueBind(queueName, ExchangeType.EXCHANGE_NAME, RouteTypes.R1.getType());

            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            Consumer consumer1 = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, StandardCharsets.UTF_8);
                    System.out.println(envelope.getRoutingKey());
                    System.out.println(" Message Received Queue 1 '" + message + "'");
                }
            };
            channel.basicConsume(queueName, true, consumer1);
        }
    }
}
