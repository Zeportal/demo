package com.example.demo.repos;

import com.example.demo.entity.Comment;
import com.example.demo.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findByAuthor(String author);
    Comment findByTopic(Topic topic);
}
