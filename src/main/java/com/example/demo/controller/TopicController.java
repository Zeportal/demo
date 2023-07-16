package com.example.demo.controller;

import com.example.demo.dto.TopicDto;
import com.example.demo.logger.SpringLoggingHelper;
import com.example.demo.services.TopicService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TopicController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @RequestMapping("/")
    String index(){
        logger.debug("This is a debug message");
        logger.info("This is an info message");
        logger.warn("This is a warn message");
        logger.error("This is an error message");
        new SpringLoggingHelper().helpMethod();
        return "index";
    }
    @Autowired
    private TopicService topicService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/topics")
    public ResponseEntity<?> getAllTopics() {
        return topicService.getAllTopicsList();
    }


    @GetMapping("/topic/{topicId}")
    public ResponseEntity<?> getTopicById(@PathVariable Long topicId) {
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
    public ResponseEntity<?> updateTopic(@PathVariable Long topicId, @RequestBody TopicDto topicDto){
        return topicService.updateTopic(topicId,topicDto);
    }
}
