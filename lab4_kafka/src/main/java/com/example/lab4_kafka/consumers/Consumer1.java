package com.example.lab4_kafka.consumers;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

public class Consumer1 {
    private final String topic;
    private final Properties props;

    public Consumer1(String topic, Properties props) {
        this.topic = topic;
        this.props = props;
    }


    public void consume() {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(topic));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(1000);
            for (ConsumerRecord<String, String> record : records) {
                String[] key=record.key().split("\\.");
                if(key[1].equals("student") && key[3].equals("Office")){
                    System.out.printf("%s [%d] offset=%d, key=%s, value=\"%s\"\n",
                            record.topic(), record.partition(),
                            record.offset(), record.key(), record.value());
                }
            }
        }
    }
}
