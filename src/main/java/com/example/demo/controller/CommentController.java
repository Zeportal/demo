package com.example.demo.controller;


import com.example.demo.dto.CommentDto;
import com.example.demo.logger.SpringLoggingHelper;
import com.example.demo.services.CommentService;
import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;


@RestController
public class CommentController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @RequestMapping("/topic/")
    String index(){
        logger.debug("This is a debug message");
        logger.info("This is an info message");
        logger.warn("This is a warn message");
        logger.error("This is an error message");
        new SpringLoggingHelper().helpMethod();
        return "index";
    }

    @Autowired
    private CommentService commentService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/topic/{topicId}/comments")
    public ResponseEntity<?> getComments(@PathVariable Long topicId) {
        return commentService.getComments(topicId);
    }

    @GetMapping("/topic/{topicId}/{commentId}")
    public ResponseEntity<?> getCommentById(@PathVariable Long commentId){
        return commentService.getCommentById(commentId);
    }

    @PostMapping("/topic/{topicId}")
    public ResponseEntity<?> saveComment(@RequestBody CommentDto commentDto,@PathVariable Long topicId) {
        return commentService.saveComment(commentDto, topicId);
    }

    @DeleteMapping("/topic/{topicId}/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId){
        return commentService.deleteComment(commentId);
    }

    @PutMapping("/topic/{topicId}/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Long commentId, @RequestBody CommentDto commentDto){
        return commentService.updateComment(commentId,commentDto);
    }

}
