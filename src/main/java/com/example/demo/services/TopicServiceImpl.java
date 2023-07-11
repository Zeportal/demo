package com.example.demo.services;

import com.example.demo.entity.Topic;
import com.example.demo.repos.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicServiceImpl implements TopicService{
    @Autowired
    private TopicRepository topicRepository;

    @Override
    public List<Topic> getAllTopicsList() {
        return topicRepository.findAll();
    }

    @Override
    public void save(Topic topic) {
        topicRepository.save(topic);
    }

    @Override
    public Topic findById(Long topicId) {
        return topicRepository.findById(topicId).get();
    }

    @Override
    public void delete(Topic topic) {
        topicRepository.delete(topic);
    }
}
