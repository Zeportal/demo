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
        Map<String, Object> jsonResponseMap = new LinkedHashMap<>();
       try{
           List<Topic> listOfTopic=topicService.getAllTopicsList();
           List<TopicDto> listOfTopicDto=new ArrayList<>();
           if (!listOfTopic.isEmpty()) {
               for (Topic topic:listOfTopic){
                   listOfTopicDto.add(modelMapper.map(topic, TopicDto.class));
               }
               jsonResponseMap.put("status",1);
               jsonResponseMap.put("data",listOfTopicDto);
               return new ResponseEntity<>(jsonResponseMap, HttpStatus.OK);
             } else {
               jsonResponseMap.clear();
               jsonResponseMap.put("status", 0);
               jsonResponseMap.put("message", "Data is not found");
               return new ResponseEntity<>(jsonResponseMap, HttpStatus.NOT_FOUND);
           }
        } catch (Exception ex) {
           jsonResponseMap.clear();
           jsonResponseMap.put("status", 0);
           jsonResponseMap.put("message", "Data is not found");
           return new ResponseEntity<>(jsonResponseMap, HttpStatus.NOT_FOUND);
       }
    }

    @GetMapping("/topic/{topicId}")
    public ResponseEntity<?> getTopicById(@PathVariable Long topicId) {
        Map<String, Object> jsonResponseMap=new LinkedHashMap<>();
        try{
            Topic topic=topicService.findById(topicId);
            TopicDto topicDto=modelMapper.map(topic, TopicDto.class);
            jsonResponseMap.put("status", 1);
            jsonResponseMap.put("data", topicDto);
            return new ResponseEntity<>(jsonResponseMap,HttpStatus.OK);
        } catch (Exception ex) {
            jsonResponseMap.clear();
            jsonResponseMap.put("status", 0);
            jsonResponseMap.put("message", "Data is not found");
            return new ResponseEntity<>(jsonResponseMap, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/topic")
    public ResponseEntity<?> saveTopic(@RequestBody TopicDto topicDto) {
        Map<String,Object> jsonResponseMap=new LinkedHashMap<>();
        Topic topic=modelMapper.map(topicDto, Topic.class);
        topicService.save(topic);
        jsonResponseMap.put("status",1);
        jsonResponseMap.put("message","Saved Successfully");
        return new ResponseEntity<>(jsonResponseMap,HttpStatus.CREATED);
    }

    @DeleteMapping("/topic/{topicId}")
    public ResponseEntity<?> deleteTopic(@PathVariable Long topicId) {
        Map<String,Object> jsonResponseMap=new LinkedHashMap<>();
        try{
            Topic topic=topicService.findById(topicId);
            topicService.delete(topic);
            jsonResponseMap.put("status", 1);
            jsonResponseMap.put("message","Deleted successfully");
            return new ResponseEntity<>(jsonResponseMap,HttpStatus.OK);
        } catch (Exception ex){
            jsonResponseMap.clear();
            jsonResponseMap.put("status", 0);
            jsonResponseMap.put("message", "Data is not found");
            return new ResponseEntity<>(jsonResponseMap, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/topic/{topicId}")
    public ResponseEntity<?> updateTopic(@PathVariable Long topicId, @RequestBody TopicDto topicDto){
        Map<String,Object> jsonResponseMap=new LinkedHashMap<>();
        try{
            Topic topic = topicService.findById(topicId);
            topic.setAuthor(topicDto.getAuthor());
            topic.setTitle(topicDto.getTitle());
            topicService.save(topic);
            jsonResponseMap.put("status",1);
            jsonResponseMap.put("data",topicService.findById(topicId));
            return new ResponseEntity<>(jsonResponseMap,HttpStatus.OK);
        } catch (Exception ex) {
            jsonResponseMap.clear();
            jsonResponseMap.put("status",1);
            jsonResponseMap.put("message","Data is not found");
            return new ResponseEntity<>(jsonResponseMap,HttpStatus.NOT_FOUND);
        }
    }
}
