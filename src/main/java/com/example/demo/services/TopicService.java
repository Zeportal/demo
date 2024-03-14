package com.example.demo.services;

import com.example.demo.config.InternalVariables;
import com.example.demo.dto.TopicDto;
import com.example.demo.entity.Topic;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repos.TopicRepository;
import com.example.demo.responseDto.ResponseTopicDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TopicService {
    private final static int LIST_STARTING_POSITION = 0;
    private final TopicRepository topicRepository;
    private final ModelMapper modelMapper;
    private final InternalVariables properties;

    public List<ResponseTopicDto> getAllTopicsList() {
        List<ResponseTopicDto> listOfResponseTopicDto = topicRepository.findAll().stream()
                .map(topic -> modelMapper.map(topic, ResponseTopicDto.class))
                .toList();
        if (listOfResponseTopicDto.isEmpty()) {
            throw new ResourceNotFoundException("No topics found");
        }
        return listOfResponseTopicDto.size() > properties.getOutputLimit() ? listOfResponseTopicDto.subList(LIST_STARTING_POSITION, properties.getOutputLimit()) : listOfResponseTopicDto;
    }
    public List<ResponseTopicDto> getTopicsByCommentTextContaining(String searchRequest) {
        List<ResponseTopicDto> listOfResponseTopicDto = topicRepository.findTopicsByCommentTextContaining(searchRequest).stream()
                .map(topic -> modelMapper.map(topic,ResponseTopicDto.class))
                .toList();
        if (listOfResponseTopicDto.isEmpty()) {
            throw new ResourceNotFoundException("No topics with such search request found");
        }
        return listOfResponseTopicDto;
    }

    public ResponseTopicDto findById(Long topicId) {
        return topicRepository.findById(topicId)
                .map(topic -> modelMapper.map(topic, ResponseTopicDto.class))
                .orElseThrow(() -> new ResourceNotFoundException("Topic with id " + topicId + " not found"));

    }

    public ResponseTopicDto saveTopic(TopicDto topicDto) {
        return saveAndReturnTopicDto(topicDto);
    }

    public void deleteTopic(Long topicId) {
        try {
            topicRepository.deleteById(topicId);
        } catch (Exception ex) {
            throw new ResourceNotFoundException("Topic with id " + topicId + " not found");
        }
    }

    public ResponseTopicDto updateTopic(Long topicId, TopicDto topicDto) {
        return topicRepository.findById(topicId)
                .map(topic -> updateSaveReturnTopicDto(topicDto, topic))
                .orElseThrow(() -> new ResourceNotFoundException("Topic with id " + topicId + " not found"));

    }

    private ResponseTopicDto updateSaveReturnTopicDto(TopicDto topicDto, Topic topic) {
        topic.setAuthor(topicDto.getAuthor());
        topic.setTitle(topicDto.getTitle());
        topicRepository.save(topic);
        return modelMapper.map(topic, ResponseTopicDto.class);
    }

    private ResponseTopicDto saveAndReturnTopicDto(TopicDto topicDto) {
        Topic topic = modelMapper.map(topicDto, Topic.class);
        topicRepository.save(topic);
        return modelMapper.map(topic, ResponseTopicDto.class);
    }
}
