package com.example.demo.services;

import com.example.demo.dto.TopicDto;
import com.example.demo.entity.Topic;
import com.example.demo.repos.TopicRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<?> getAllTopicsList() {

        List<Topic> listOfTopic=topicRepository.findAll();
        List<TopicDto> listOfTopicDto=new ArrayList<>();
        if (!listOfTopic.isEmpty()) {
            for (Topic topic:listOfTopic){
                listOfTopicDto.add(modelMapper.map(topic, TopicDto.class));
            }
            return new ResponseEntity<>(listOfTopicDto, HttpStatus.OK);
        } else
            return new ResponseEntity<>("Data not found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> findById(Long topicId) {
        try{
            Topic topic=topicRepository.findById(topicId).get();
            TopicDto topicDto=modelMapper.map(topic, TopicDto.class);
            return new ResponseEntity<>(topicDto,HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Data not found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> saveTopic(TopicDto topicDto) {
        Topic topic=modelMapper.map(topicDto, Topic.class);
        topicRepository.save(topic);
        return new ResponseEntity<>("Saved successfully",HttpStatus.CREATED);
    }

    public ResponseEntity<?> deleteTopic(Long topicId) {
        try{
            topicRepository.deleteById(topicId);
            return new ResponseEntity<>("Deleted successfully",HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>("Data not found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> updateTopic(Long topicId, TopicDto topicDto){
        try{
            Topic topic = topicRepository.findById(topicId).get();
            topic.setAuthor(topicDto.getAuthor());
            topic.setTitle(topicDto.getTitle());
            topicRepository.save(topic);
            return new ResponseEntity<>(topicDto,HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Data not found",HttpStatus.NOT_FOUND);
        }
    }

}
