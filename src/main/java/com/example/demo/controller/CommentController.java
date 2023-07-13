package com.example.demo.controller;

import com.example.demo.dto.CommentDto;
import com.example.demo.entity.Comment;
import com.example.demo.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/topic/{topicId}/comments")
    public ResponseEntity<?> getComments(@PathVariable Long topicId) {
            List<Comment> listOfComment=commentService.findByTopicId(topicId);
            List<CommentDto> listOfCommentDto=new ArrayList<>();
            if (!listOfComment.isEmpty()) {
                for (Comment comment:listOfComment){
                    listOfCommentDto.add(modelMapper.map(comment, CommentDto.class));
                }
                return new ResponseEntity<List>(listOfCommentDto,HttpStatus.OK);
            } else {
                return new ResponseEntity<>("data not found", HttpStatus.NOT_FOUND);
            }
        }

    @GetMapping("/topic/{topicId}/{commentId}")
    public ResponseEntity<?> getCommentById(@PathVariable Long commentId){
        try{
            Comment comment=commentService.findById(commentId);
            CommentDto commentDto=modelMapper.map(comment,CommentDto.class);
            return new ResponseEntity<>(commentDto,HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("data not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/topic/{topicId}")
    public ResponseEntity<?> saveComment(@RequestBody CommentDto commentDto) {
        Comment comment=modelMapper.map(commentDto, Comment.class);
        commentService.save(comment);
        return new ResponseEntity<>("Saved successfully",HttpStatus.CREATED);
    }

    @DeleteMapping("/topic/{topicId}/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId){
        try{
            commentService.deleteById(commentId);
            return new ResponseEntity<>("Deleted successfully",HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>("Data not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/topic/{topicId}/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Long commentId, @RequestBody CommentDto commentDto){
        try{
            Comment comment=commentService.findById(commentId);
            comment.setAuthor(commentDto.getAuthor());
            comment.setText(commentDto.getText());
            commentService.save(comment);
            return new ResponseEntity<>(comment,HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Data not found",HttpStatus.NOT_FOUND);
        }
    }

}
