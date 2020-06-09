package com.example.demo.service;

import com.example.demo.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

@Service
@Slf4j
public class KafkaService {

    private static final String TOPIC_NAME = "Hello-JSON";

    @Autowired
    KafkaTemplate<Object, UserDto> kafkaTemplate;


//    @KafkaListener(id = "Starship", topics = {"TutorialTopic"}, containerFactory = "singleFactory")
//    public void consume(StarshipDto dto) {
//        log.info("=> consumed {}", writeValueAsString(dto));
//    }


    @KafkaListener(id = "Hello", topics = {"test"}, groupId ="group_id")
    public void consume(String hello) {
        log.info("=> consumed {}", hello);
    }

    @KafkaListener(id = "Hello-JSON", topics = {"Hello-JSON"}, groupId ="group_json", containerFactory = "userKafkaListenerFactory")
    public void consumeJson(UserDto user) {
        log.info("=> consumed JSON message {} : ", user.toString());
    }

    public void sendUser(UserDto userDto) {
        kafkaTemplate.send(TOPIC_NAME, userDto);
    }

//    private String writeValueAsString(StarshipDto dto) {
//        try {
//            return objectMapper.writeValueAsString(dto);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//            throw new RuntimeException("Writing value to JSON failed: " + dto.toString());
//        }
//    }

//    @Scheduled(initialDelay = 10000, fixedDelay = 5000)
//    public void produce() {
//        StarshipDto dto = createDto();
//        log.info("<= sending {}", writeValueAsString(dto));
//        kafkaStarshipTemplate.send("server.starship", dto);
//    }

//    private StarshipDto createDto() {
//        return new StarshipDto("Starship " + (LocalTime.now().toNanoOfDay() / 1000000));
//    }

}
