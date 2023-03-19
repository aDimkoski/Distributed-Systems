package com.example.lab4_kafka;

import com.example.lab4_kafka.consumers.Consumer1;
import com.example.lab4_kafka.consumers.Consumer2;
import com.example.lab4_kafka.consumers.Consumer3;
import com.example.lab4_kafka.enums.RoomType;
import com.example.lab4_kafka.enums.UserType;
import com.example.lab4_kafka.enums.Username;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Properties;
import java.util.Random;

public class KafkaLab {
    private final String topic;
    private final String group;
    private final Properties props;


    public KafkaLab(String brokers, String username, String password) {
        this.topic = "192122";
        this.group = "group1";

//        String jaasTemplate = "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";";
//        String jaasCfg = String.format(jaasTemplate, username, password);

        String serializer = StringSerializer.class.getName();
        String deserializer = StringDeserializer.class.getName();
        props = new Properties();
        props.put("bootstrap.servers", brokers);
        props.put("group.id", group + "-consumer");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("auto.offset.reset", "earliest");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", deserializer);
        props.put("value.deserializer", deserializer);
        props.put("key.serializer", serializer);
        props.put("value.serializer", serializer);
//        props.put("security.protocol", "SASL_SSL");
//        props.put("sasl.mechanism", "SCRAM-SHA-256");
//        props.put("sasl.jaas.config", jaasCfg);
    }

    public static String randomRoutingKey() {
        Random random = new Random();
        String username = Username.values()[random.nextInt(Username.values().length)].getName();
        String userType = UserType.values()[random.nextInt(UserType.values().length)].getType();
        String roomType = RoomType.values()[random.nextInt(RoomType.values().length)].getType();
        return username + "." + userType + "." + random.nextInt(20) + "." + roomType;
    }

    public void produce() {
        Producer<String, String> producer = new KafkaProducer<>(props);
        //protocol <name>.<usertype>.<roomNumber>.<roomType>
        //         alex.student.112.office
        for (int i = 0; i < 20; i++) {
            String message = "Message " + i;
            String routingKey = randomRoutingKey();
            producer.send(new ProducerRecord<>(topic, routingKey, message));
            System.out.println(" Message Sent '" + routingKey + "'");
        }
    }



    public static void main(String[] args) throws FileNotFoundException {
        String brokers = "kafkaserver.devops.mk:9092";
        String username = "";
        String password = "";
        KafkaLab c = new KafkaLab(brokers, username, password);
        c.produce();

//        Consumer1 consumer1=new Consumer1(c.topic,c.props);
//        consumer1.consume();
//        Consumer2 consumer2=new Consumer2(c.topic,c.props);
//        consumer2.consume();
        Consumer3 consumer3=new Consumer3(c.topic,c.props);
        consumer3.consume();
    }
}
