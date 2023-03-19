package com.example.lab4_kafka.consumers;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

public class Consumer3 {
    private final String topic;
    private final Properties props;

    public Consumer3(String topic, Properties props) {
        this.topic = topic;
        this.props = props;
    }


    public void consume() throws FileNotFoundException {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(topic));

        PrintStream file = new PrintStream(new FileOutputStream("students.csv", true));

        PrintStream console = System.out;

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(1000);
            for (ConsumerRecord<String, String> record : records) {
                String[] key = record.key().split("\\.");
                if (key[1].equals("student")) {
                    System.setOut(file);
                    String csvRow = key[0] + "." + key[1] + "." + key[2] + "." + key[3] + " - " + new Date();
                    System.out.println(csvRow);
                    System.setOut(console);

                    System.out.println("Message written in csv: "+csvRow);
                }
            }
        }
    }
}

