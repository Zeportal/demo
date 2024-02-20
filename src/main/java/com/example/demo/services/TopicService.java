package com.example.demo.services;

import com.example.demo.dto.TopicDto;
import com.example.demo.entity.Topic;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repos.TopicRepository;
import com.example.demo.responseDto.ResponseTopicDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicService {


    private final TopicRepository topicRepository;


    private final ModelMapper modelMapper;

    public TopicService(TopicRepository topicRepository, ModelMapper modelMapper) {
        this.topicRepository = topicRepository;
        this.modelMapper = modelMapper;
    }

    public List<ResponseTopicDto> getAllTopicsList() {
        List<ResponseTopicDto> listOfResponseTopicDto = topicRepository.findAll().stream()
                .map(topic -> modelMapper.map(topic, ResponseTopicDto.class))
                .toList();
        if (listOfResponseTopicDto.isEmpty()) throw new ResourceNotFoundException("No topics found");
        else return listOfResponseTopicDto;
    }

    public ResponseTopicDto findById(Long topicId) {
        return topicRepository.findById(topicId)
                .map(topic -> modelMapper.map(topic, ResponseTopicDto.class))
                .orElseThrow(() -> new ResourceNotFoundException("Topic with id " + topicId + " not found"));

    }

    public ResponseTopicDto saveTopic(TopicDto topicDto) {
        return Optional.of(topicDto)
                .map(dto -> {
                    Topic topic = modelMapper.map(dto, Topic.class);
                    topicRepository.save(topic);
                    return modelMapper.map(topic, ResponseTopicDto.class);
                })
                .orElseThrow(() -> new IllegalArgumentException("Invalid data provided"));
    }

    public void deleteTopic(Long topicId) {
        try {
            topicRepository.deleteById(topicId);
        } catch (Exception ex) {
            throw new ResourceNotFoundException("Topic with id "+topicId+" not found");
        }
    }

    public ResponseTopicDto updateTopic(Long topicId, TopicDto topicDto) {
         return topicRepository.findById(topicId)
                .map(topic -> {
                    topic.setAuthor(topicDto.getAuthor());
                    topic.setTitle(topicDto.getTitle());
                    topicRepository.save(topic);
                    return modelMapper.map(topic, ResponseTopicDto.class);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Topic with id " + topicId + " not found"));

    }

}
