package com.example.demo.repos;

import com.example.demo.dto.TopicDto;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findByAuthor(String author);
    List<Comment> findByTopicTopicId(Long topicId);
}
