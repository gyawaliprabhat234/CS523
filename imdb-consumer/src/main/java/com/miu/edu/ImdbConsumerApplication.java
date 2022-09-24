package com.miu.edu;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
//
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaPairInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;

import kafka.serializer.StringDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileReader;
import java.util.List;

@SpringBootApplication
public class ImdbConsumerApplication implements CommandLineRunner {


	@Autowired
	private ImdbConsumer producer;

	public static void main(String[] args) {
		SpringApplication.run(ImdbConsumerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Spark Streaming started now .....");

		SparkConf conf = new SparkConf().setAppName("kafka-sandbox").setMaster("local[*]");
		JavaSparkContext sc = new JavaSparkContext(conf);
		// batchDuration - The time interval at which streaming data will be divided into batches
		JavaStreamingContext ssc = new JavaStreamingContext(sc, new Duration(20000));

		Map<String, String> kafkaParams = new HashMap<>();
		kafkaParams.put("metadata.broker.list", "localhost:9092");
		Set<String> topics = Collections.singleton("imdb_movies");

		JavaPairInputDStream<String, String> directKafkaStream = KafkaUtils.createDirectStream(ssc, String.class,
				String.class, StringDecoder.class, StringDecoder.class, kafkaParams, topics);
		directKafkaStream.foreachRDD(rdd -> {

			System.out.println("New data arrived  " + rdd.partitions().size() +" Partitions and " + rdd.count() + " Records");
			if(rdd.count() > 0) {
				rdd.collect().forEach(rawRecord -> {
					System.out.println(rawRecord);
					System.out.println("***************************************");
					System.out.println(rawRecord._2);
					String record = rawRecord._2();
					System.out.println(record);
				});
			}
		});
		ssc.start();
		ssc.awaitTermination();

	}
}
