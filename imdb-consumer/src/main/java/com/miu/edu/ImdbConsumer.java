package com.miu.edu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Prabhat Gyawali
 * @created 22-Sep-2022 - 12:01 PM
 * @project BigDataProject
 */

@Component
@RequiredArgsConstructor
public class ImdbConsumer {

    private static final String TOPIC = "imdb_movies";
    @KafkaListener(topics = TOPIC, groupId = "group_id")
    public void consume(String data) throws JsonProcessingException {
        Movie movie = new ObjectMapper().readValue(data, Movie.class);
       System.out.println("Incoming Message - Consuming -> {\n "+movie+"\n } ");
    }
}
