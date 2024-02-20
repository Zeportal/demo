package com.example.demo.services;

import com.example.demo.dto.TopicDto;
import com.example.demo.entity.Topic;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repos.TopicRepository;
import com.example.demo.responseDto.ResponseTopicDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<ResponseTopicDto> getAllTopicsList() {
         List<ResponseTopicDto> listOfResponseTopicDto = topicRepository.findAll().stream()
                .map(topic -> modelMapper.map(topic, ResponseTopicDto.class))
                .toList();
        return Optional.of(listOfResponseTopicDto)
                .filter(list->!list.isEmpty())
                .map(list-> listOfResponseTopicDto)
                .orElseThrow(()->new ResourceNotFoundException("No topics found"));
    }

    public ResponseTopicDto findById(Long topicId) {
         return topicRepository.findById(topicId)
                .map(topic -> {
                    ResponseTopicDto responseTopicDto = modelMapper.map(topic, ResponseTopicDto.class);
                    return responseTopicDto;
                })
                .orElseThrow(() -> new ResourceNotFoundException("Topic with id " + topicId + " not found"));

    }

    public ResponseEntity<?> saveTopic(TopicDto topicDto) {
        Topic topic = modelMapper.map(topicDto, Topic.class);
        topicRepository.save(topic);
        return new ResponseEntity<>("Saved successfully", HttpStatus.CREATED);
    }

    public ResponseEntity<?> deleteTopic(Long topicId) {
        try {
            topicRepository.deleteById(topicId);
            return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Data not found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseTopicDto updateTopic(Long topicId, TopicDto topicDto) {
        return topicRepository.findById(topicId)
                .map(topic -> {
                    topic.setAuthor(topicDto.getAuthor());
                    topic.setTitle(topicDto.getTitle());
                    topicRepository.save(topic);
                    ResponseTopicDto responseTopicDto = modelMapper.map(topic, ResponseTopicDto.class);
                    return responseTopicDto;
                })
                .orElseThrow(()-> new ResourceNotFoundException("Topic with id " + topicId + " not found"));

    }

}
