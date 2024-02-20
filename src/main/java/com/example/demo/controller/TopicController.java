package com.example.demo.controller;

import com.example.demo.dto.TopicDto;
import com.example.demo.responseDto.ResponseTopicDto;
import com.example.demo.services.TopicService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TopicController {


    private final TopicService topicService;


    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping("/topics")
    public List<ResponseTopicDto> getAllTopics() {
        return topicService.getAllTopicsList();
    }


    @GetMapping("/topic/{topicId}")
    public ResponseTopicDto getTopicById(@PathVariable Long topicId) {
        return topicService.findById(topicId);
    }

    @PostMapping("/topic")
    public ResponseTopicDto saveTopic(@RequestBody @Valid TopicDto topicDto) {
        return topicService.saveTopic(topicDto);
    }

    @DeleteMapping("/topic/{topicId}")
    public void deleteTopic(@PathVariable Long topicId) {
        topicService.deleteTopic(topicId);
    }

    @PutMapping("/topic/{topicId}")
    public ResponseTopicDto updateTopic(@PathVariable Long topicId, @RequestBody @Valid TopicDto topicDto) {
        return topicService.updateTopic(topicId, topicDto);
    }
}
