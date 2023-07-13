package com.example.demo.controller;

import com.example.demo.dto.CommentDto;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Topic;
import com.example.demo.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/topic/{topicId}/comments")
    public ResponseEntity<?> getComment(Topic topic) {
        Map<String, Object> jsonResponseMap = new LinkedHashMap<>();
        try{
            List<Comment> listOfComment=commentService.findByTopic(topic);
            List<CommentDto> listOfCommentDto=new ArrayList<>();
            if (!listOfComment.isEmpty()) {
                for (Comment comment:listOfComment){
                    listOfCommentDto.add(modelMapper.map(comment, CommentDto.class));
                }
                jsonResponseMap.put("status",1);
                jsonResponseMap.put("data",listOfCommentDto);
                return new ResponseEntity<>(jsonResponseMap, HttpStatus.OK);
            } else {
                jsonResponseMap.clear();
                jsonResponseMap.put("status", 0);
                jsonResponseMap.put("message", "Data is not found");
                return new ResponseEntity<>(jsonResponseMap, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            jsonResponseMap.clear();
            jsonResponseMap.put("status", 0);
            jsonResponseMap.put("message", "Data is not found");
            return new ResponseEntity<>(jsonResponseMap, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/topic/{topicId}/{commentId}")
    public ResponseEntity<?> getCommentById(@PathVariable Long commentId){
        Map<String, Object> jsonResponseMap = new LinkedHashMap<>();
        try{
            Comment comment=commentService.findById(commentId);
            CommentDto commentDto=modelMapper.map(comment,CommentDto.class);
            jsonResponseMap.put("status", 1);
            jsonResponseMap.put("data", commentDto);
            return new ResponseEntity<>(jsonResponseMap,HttpStatus.OK);
        } catch (Exception ex) {
            jsonResponseMap.clear();
            jsonResponseMap.put("status", 0);
            jsonResponseMap.put("message", "Data is not found");
            return new ResponseEntity<>(jsonResponseMap, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/topic/{topicId}")
    public ResponseEntity<?> saveComment(@RequestBody CommentDto commentDto) {
        Map<String, Object> jsonResponseMap=new LinkedHashMap<>();
        Comment comment=modelMapper.map(commentDto, Comment.class);
        commentService.save(comment);
        jsonResponseMap.put("status",1);
        jsonResponseMap.put("message","Saved Successfully");
        return new ResponseEntity<>(jsonResponseMap,HttpStatus.CREATED);
    }

    @DeleteMapping("/topic/{topicId}/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId){
        Map<String,Object> jsonResponseMap=new LinkedHashMap<>();
        try{
            Comment comment=commentService.findById(commentId);
            commentService.delete(comment);
            jsonResponseMap.put("status", 1);
            jsonResponseMap.put("message","Deleted successfully");
            return new ResponseEntity<>(jsonResponseMap,HttpStatus.OK);
        } catch (Exception ex){
            jsonResponseMap.clear();
            jsonResponseMap.put("status", 0);
            jsonResponseMap.put("message", "Data is not found");
            return new ResponseEntity<>(jsonResponseMap, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/topic/{topicId}/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Long commentId, @RequestBody CommentDto commentDto){
        Map<String,Object> jsonResponseMap=new LinkedHashMap<>();
        try{
            Comment comment=commentService.findById(commentId);
            comment.setAuthor(commentDto.getAuthor());
            comment.setText(commentDto.getText());
            commentService.save(comment);
            jsonResponseMap.put("status",1);
            jsonResponseMap.put("data",commentService.findById(commentId));
            return new ResponseEntity<>(jsonResponseMap,HttpStatus.OK);
        } catch (Exception ex) {
            jsonResponseMap.clear();
            jsonResponseMap.put("status",1);
            jsonResponseMap.put("message","Data is not found");
            return new ResponseEntity<>(jsonResponseMap,HttpStatus.NOT_FOUND);
        }
    }

}
