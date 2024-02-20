package com.example.demo.controller;

import com.example.demo.dto.TopicDto;
import com.example.demo.responseDto.ResponseTopicDto;
import com.example.demo.services.TopicService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TopicController {


    @Autowired
    private TopicService topicService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/topics")
    public List<ResponseTopicDto> getAllTopics() {
        return topicService.getAllTopicsList();
    }


    @GetMapping("/topic/{topicId}")
    public ResponseTopicDto getTopicById(@PathVariable Long topicId) {
        return topicService.findById(topicId);
    }

    @PostMapping("/topic")
    public ResponseEntity<?> saveTopic(@RequestBody TopicDto topicDto) {
        return topicService.saveTopic(topicDto);
    }

    @DeleteMapping("/topic/{topicId}")
    public ResponseEntity<?> deleteTopic(@PathVariable Long topicId) {
        return topicService.deleteTopic(topicId);
    }

    @PutMapping("/topic/{topicId}")
    public ResponseTopicDto updateTopic(@PathVariable Long topicId, @RequestBody TopicDto topicDto) {
        return topicService.updateTopic(topicId, topicDto);
    }
}
