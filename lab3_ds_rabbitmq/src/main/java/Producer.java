import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import enums.ExchangeType;
import enums.RoomType;
import enums.UserType;
import enums.Username;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class Producer {
    public static String randomRoutingKey() {
        Random random = new Random();
        String username = Username.values()[random.nextInt(Username.values().length)].getName();
        String userType = UserType.values()[random.nextInt(UserType.values().length)].getType();
        String roomType = RoomType.values()[random.nextInt(RoomType.values().length)].getType();
        return username + "." + userType + "." + random.nextInt(20) + "." + roomType;
    }

    public static void main(String[] args) {
        try {
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
                //protocol <name>.<usertype>.<roomNumber>.<roomType>
                //         alex.student.112.office
                for (int i = 0; i < 20; i++) {
                    String message = "Message " + i;
                    String routingKey=randomRoutingKey();
                    channel.basicPublish(ExchangeType.EXCHANGE_NAME, routingKey, null, message.getBytes(StandardCharsets.UTF_8));
                    System.out.println(" Message Sent '" + message + "'");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
