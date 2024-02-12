package com.example.demo.controller;


import com.example.demo.dto.CommentDto;
import com.example.demo.responseDto.ResponseCommentDto;
import com.example.demo.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;


@RestController
public class CommentController {



    @Autowired
    private CommentService commentService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/topic/{topicId}/comments")
    public List<ResponseCommentDto> getComments(@PathVariable Long topicId) {
        return commentService.getComments(topicId);
    }

    @GetMapping("/topic/{topicId}/{commentId}")
    public ResponseCommentDto getCommentById(@PathVariable Long commentId){
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
    public ResponseCommentDto updateComment(@PathVariable Long commentId, @RequestBody CommentDto commentDto){
        return commentService.updateComment(commentId,commentDto);
    }

}
