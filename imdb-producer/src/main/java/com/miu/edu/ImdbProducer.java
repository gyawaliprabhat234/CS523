package com.miu.edu;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Prabhat Gyawali
 * @created 22-Sep-2022 - 12:01 PM
 * @project BigDataProject
 */

@Component
@RequiredArgsConstructor
public class ImdbProducer {

    private static final String TOPIC = "imdb_movies";
    private final KafkaTemplate<String, Movie> kafkaTemplate;
    public void produce(Movie movie)
    {
        System.out.println("Outgoing Message - Producing -> {\n "+movie+  "\n }");
        this.kafkaTemplate.send(TOPIC, movie);
    }
}
