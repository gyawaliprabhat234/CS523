package cs.miu.edu;


import java.util.*;

import kafka.serializer.StringDecoder;


import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaPairInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Prabhat Gyawali
 * @project BigDataProject
 */

public class ConsumerApplication {

	public static void main(String[] args) {
		System.out.println(Arrays.toString(args));
		FilterParameters filterParameters = new FilterParameters(args);
		System.out.println(filterParameters);
		
		SparkConf conf = new SparkConf().setAppName("kafka-consumer-spark").setMaster("local[*]");
		JavaSparkContext sc = new JavaSparkContext(conf);

		JavaStreamingContext ssc = new JavaStreamingContext(sc, new Duration(10000));

		Map<String, String> kafkaParams = new HashMap<>();
		kafkaParams.put("metadata.broker.list", "localhost:9092");
		Set<String> topics = Collections.singleton("imdb_movies");
		HBaseTable table = new HBaseTable(filterParameters.getTableName());
		JavaPairInputDStream<String, String> directKafkaStream = KafkaUtils.createDirectStream(ssc, String.class,
				String.class, StringDecoder.class, StringDecoder.class, kafkaParams, topics);
		System.out.println("Created Table name ["+filterParameters.getTableName()+"] in Hbase");
		directKafkaStream.foreachRDD(rdd ->
				{
					if (rdd.count() > 0) {
						rdd.collect()
								.stream()
								.map(FunctionWithException.apply(pair -> new ObjectMapper().readValue(pair._2, Movie.class)))
								.filter(value -> value != null)
								.filter(filterParameters::checkReleaseYear)
								.filter(filterParameters::checkGenres)
								.forEach(ConsumerWithException.accept(table::insertRow));
					}
				});
		ssc.start();
		ssc.awaitTermination();
	}
}
