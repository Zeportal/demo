package com.example.demo.controller;

import com.example.demo.dto.TopicDto;
import com.example.demo.entity.Topic;
import com.example.demo.services.TopicService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TopicController {
    @Autowired
    private TopicService topicService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/topics")
    public ResponseEntity<?> getTopic() {
           List<Topic> listOfTopic=topicService.getAllTopicsList();
           List<TopicDto> listOfTopicDto=new ArrayList<>();
           if (!listOfTopic.isEmpty()) {
               for (Topic topic:listOfTopic){
                   listOfTopicDto.add(modelMapper.map(topic, TopicDto.class));
               }
               return new ResponseEntity<>(listOfTopicDto, HttpStatus.OK);
             } else
               return new ResponseEntity<>("Data not found", HttpStatus.NOT_FOUND);
    }


    @GetMapping("/topic/{topicId}")
    public ResponseEntity<?> getTopicById(@PathVariable Long topicId) {
        try{
            Topic topic=topicService.findById(topicId);
            TopicDto topicDto=modelMapper.map(topic, TopicDto.class);
            return new ResponseEntity<>(topicDto,HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Data not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/topic")
    public ResponseEntity<?> saveTopic(@RequestBody TopicDto topicDto) {
        Topic topic=modelMapper.map(topicDto, Topic.class);
        topicService.save(topic);
        return new ResponseEntity<>("Saved successfully",HttpStatus.CREATED);
    }

    @DeleteMapping("/topic/{topicId}")
    public ResponseEntity<?> deleteTopic(@PathVariable Long topicId) {
        try{
            topicService.deleteById(topicId);
            return new ResponseEntity<>("Deleted successfully",HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>("Data not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/topic/{topicId}")
    public ResponseEntity<?> updateTopic(@PathVariable Long topicId, @RequestBody TopicDto topicDto){
        try{
            Topic topic = topicService.findById(topicId);
            topic.setAuthor(topicDto.getAuthor());
            topic.setTitle(topicDto.getTitle());
            topicService.save(topic);
            return new ResponseEntity<>(topic,HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Data not found",HttpStatus.NOT_FOUND);
        }
    }
}
