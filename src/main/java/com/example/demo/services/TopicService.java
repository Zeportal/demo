package com.example.demo.services;

import com.example.demo.entity.Topic;
import com.example.demo.repos.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    public List<Topic> getAllTopicsList() {
        return topicRepository.findAll();
    }

    public void save(Topic topic) {
        topicRepository.save(topic);
    }

    public Topic findById(Long topicId) {
        return topicRepository.findById(topicId).get();
    }

    public void deleteById(Long topicId) {
        topicRepository.deleteById(topicId);
    }
}
