package com.eagle;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Main {
    public static void main(String args[]) throws InterruptedException {
        Properties props = new Properties();
        props.put("bootstrap.servers", "h131:9095");
        // props.put("zookeeper.connect", "192.168.95.217:2181");
        props.put("group.id", "test-consumer-group1");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("auto.offset.reset", "earliest");
        props.put("session.timeout.ms", "30000");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        System.out.println(11);
        KafkaProducer<Object, Object> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 20; i++) {
            Future<RecordMetadata> send = producer.send(new ProducerRecord<>("gen2", Integer.toString(i), Integer.toString(i)));
            try {
                RecordMetadata recordMetadata = send.get();
                System.out.println(212);
                Thread.sleep(100);
            }catch (InterruptedException e){
                e.printStackTrace();
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }

        }

        producer.close();
//        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(props);
//        System.out.println("properties loaded");
//        kafkaConsumer.subscribe(Arrays.asList("gen2"));
//        kafkaConsumer.seekToBeginning(Collections.emptyList());
//        while (true) {
//            ConsumerRecords<String, String> records = kafkaConsumer.poll(1);
//            System.out.println("Records Length : " + records.count());
//            for (ConsumerRecord<String, String> record : records) {
//                System.out.printf("offset = %d, value = %s", record.offset(), record.value());
//                System.out.println();
//            }
//            Thread.sleep(30000);
//        }
    }
}