package Consumer;

import com.rabbitmq.client.*;
import enums.ExchangeType;
import enums.RouteTypes;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.TimeoutException;

public class Consumer3 {
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
            channel.exchangeDeclare(ExchangeType.EXCHANGE_NAME, ExchangeType.TOPIC.getExchangeName(), true);
            // Queue
            channel.queueDeclare("CONSUMER_3", true, false, false, null);
            channel.queueBind("CONSUMER_3", ExchangeType.EXCHANGE_NAME, RouteTypes.R3.getType());

            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            PrintStream file = new PrintStream(new FileOutputStream("students.csv", true));

            PrintStream console = System.out;

            Consumer consumer3 = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.setOut(file);

                    // Write message only
                    // Routes have the form of: <personName>.<userType>.<roomID>.<roomType>
                    String routeKey = envelope.getRoutingKey();

                    String[] separated = routeKey.split("\\.");

                    String name = separated[0];
                    String roomId = separated[2];
                    String roomType = separated[3];

                    String csvRow = name + "." + roomType + "." + roomId + "." + new Date();
                    System.out.println(csvRow);

                    System.setOut(console);

                    String message = new String(body, StandardCharsets.UTF_8);

                    System.out.println(new Date() + " [x] Received '" +
                            routeKey + "'Message: '" + message + "'");
                }
            };
            channel.basicConsume("CONSUMER_3", true, consumer3);

        }
    }
}
