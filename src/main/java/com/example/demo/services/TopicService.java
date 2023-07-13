package com.example.demo.services;

import com.example.demo.entity.Topic;

import java.util.List;

public interface TopicService {
    public List<Topic> getAllTopicsList();
    public void save(Topic topic);
    public Topic findById(Long topicId);
    public void delete(Topic topic);
}
