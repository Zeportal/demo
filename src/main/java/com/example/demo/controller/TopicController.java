package com.example.demo.controller;

import com.example.demo.entity.Topic;
import com.example.demo.exceptions.TopicNotFoundException;
import com.example.demo.repos.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class TopicController {
    @Autowired
    private TopicRepository topicRepository;

    @GetMapping("/topics/{topic_id}")
    public Topic retrieveTopicById(@PathVariable long topic_id) {
        Optional<Topic> topic = topicRepository.findById(topic_id);

        if (topic.isEmpty())
            throw new TopicNotFoundException();
        return topic.get();
    }

    @GetMapping("/topics/{author}")
    public Topic retrieveTopicByAuthor(@PathVariable String author) {
        Optional<Topic> topic = Optional.ofNullable(topicRepository.findByAuthor(author));

        if (topic.isEmpty())
            throw new TopicNotFoundException();
        return topic.get();
    }

    @DeleteMapping("/topics/{topic_id}")
    public void deleteTopic(@PathVariable long topic_id) {
        topicRepository.deleteById(topic_id);

    }

    @PostMapping("/topics")
    public ResponseEntity<Object> createTopic(@RequestBody Topic topic) {
        Topic newTopic=topicRepository.save(topic);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{topic_id}")
                .buildAndExpand(newTopic.getTopic_id()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/topics/{topic_id}")
    public ResponseEntity<Object> updateTopic(@RequestBody Topic topic, @PathVariable long topic_id) {
        Optional<Topic> topicOptional = topicRepository.findById(topic_id);
        if (topicOptional.isEmpty())
            return ResponseEntity.notFound().build();
        topic.setTopic_id(topic_id);
        topicRepository.save(topic);
        return ResponseEntity.noContent().build();
    }


}
