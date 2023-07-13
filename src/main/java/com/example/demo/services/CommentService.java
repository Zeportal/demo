package com.example.demo.services;

import com.example.demo.dto.CommentDto;
import com.example.demo.dto.TopicDto;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Topic;
import com.example.demo.repos.CommentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<Comment> getAllCommentsList() {
        return commentRepository.findAll();
    }

    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId).get();
    }

    public void deleteById(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    public List<Comment> findByTopicId(Long topicId){
        return (List<Comment>) commentRepository.findByTopicTopicId(topicId);
    }

}
