package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaController {
    @Autowired
    KafkaService kafkaService;

    @PostMapping("/sendUser")
    public void sentMessage(@RequestBody UserDto userDto) {
        kafkaService.sendUser(userDto);
    }
}
