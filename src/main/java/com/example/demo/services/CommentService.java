package com.example.demo.services;

import com.example.demo.entity.Comment;
import com.example.demo.entity.Topic;

import java.util.List;

public interface CommentService {
    public List<Comment> getAllCommentsList();
    public void save(Comment comment);
    public Comment findById(Long commentId);
    public void delete(Comment comment);
    public List<Comment> findByTopic(Topic topic);
}
