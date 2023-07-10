package com.example.demo.controller;

import com.example.demo.entity.Comment;
import com.example.demo.exceptions.CommentNotFoundException;
import com.example.demo.repos.CommentRepository;
import com.example.demo.repos.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TopicRepository topicRepository;

    @GetMapping("/topics/{topic_id}/{comment_id}")
    public Comment retrieveCommentById(@PathVariable long topic_id, long comment_id){
        Optional<Comment> comment=commentRepository.findById(comment_id);
        if (comment.isEmpty())
            throw new CommentNotFoundException();
        return comment.get();
    }

    @GetMapping("/topics/{topic_id}/")
    public Comment retrieveComments(@PathVariable long topic_id){
        Optional<Comment> comment= Optional.ofNullable(commentRepository.findByMyTopic(topic_id));
        if (comment.isEmpty())
            throw new CommentNotFoundException();
        return comment.get();
    }

    @DeleteMapping("/topics/{topic_id}/{commend_id}")
    public void deleteComment(@PathVariable long topic_id, @PathVariable long comment_id) {
        commentRepository.deleteById(comment_id);
    }

    @PostMapping("/topics/{topic_id}")
    public ResponseEntity<Object> createComment(@PathVariable long topic_id,@RequestBody Comment comment) {
        Comment newComment=commentRepository.save(comment);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{comment_id}")
                .buildAndExpand(newComment.getComment_id()).toUri();

        return ResponseEntity.created(location).build();
    }

}
