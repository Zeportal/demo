package com.example.demo.controller;


import com.example.demo.dto.CommentDto;
import com.example.demo.responseDto.ResponseCommentDto;
import com.example.demo.services.CommentService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
public class CommentController {


    private final CommentService commentService;


    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/topic/{topicId}/comments")
    public List<ResponseCommentDto> getComments(@PathVariable Long topicId) {
        return commentService.getComments(topicId);
    }

    @GetMapping("/topic/{topicId}/{commentId}")
    public ResponseCommentDto getCommentById(@PathVariable Long commentId) {
        return commentService.getCommentById(commentId);
    }
    @GetMapping(value = "/comments", params = "topicAuthor")
    public List<ResponseCommentDto> getCommentsByTopicAuthor(@RequestParam String topicAuthor) {
        return commentService.getCommentsByTopicAuthor(topicAuthor);
    }
    @GetMapping(value = "/comments", params = "searchRequest")
    public List<ResponseCommentDto> getCommentsByTextContaining(@RequestParam String searchRequest) {
        return commentService.getCommentsByTextContaining(searchRequest);
    }

    @PostMapping("/topic/{topicId}")
    public ResponseCommentDto saveComment(@RequestBody @Valid CommentDto commentDto, @PathVariable Long topicId) {
        return commentService.saveComment(commentDto, topicId);
    }

    @DeleteMapping("/topic/{topicId}/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }

    @PutMapping("/topic/{topicId}/{commentId}")
    public ResponseCommentDto updateComment(@PathVariable Long commentId, @RequestBody @Valid CommentDto commentDto) {
        return commentService.updateComment(commentId, commentDto);
    }

}
