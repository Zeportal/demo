package com.example.demo.services;

import com.example.demo.dto.CommentDto;
import com.example.demo.entity.Comment;
import com.example.demo.repos.CommentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ModelMapper modelMapper;


    public ResponseEntity<?> getComments(Long topicId) {
        List<Comment> listOfComment=commentRepository.findByTopicId(topicId);
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

    public ResponseEntity<?> getCommentById(Long commentId){
        try{
            Comment comment=commentRepository.findById(commentId).get();
            CommentDto commentDto=modelMapper.map(comment,CommentDto.class);
            return new ResponseEntity<>(commentDto,HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("data not found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> saveComment(CommentDto commentDto, Long topicId) {
        commentDto.setTopicId(topicId);
        Comment comment=modelMapper.map(commentDto, Comment.class);
        commentRepository.save(comment);
        return new ResponseEntity<>("Saved successfully",HttpStatus.CREATED);
    }

    public ResponseEntity<?> deleteComment(Long commentId){
        try{
            commentRepository.deleteById(commentId);
            return new ResponseEntity<>("Deleted successfully",HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>("Data not found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> updateComment(Long commentId, CommentDto commentDto){
        try{
            Comment comment=commentRepository.findById(commentId).get();
            comment.setAuthor(commentDto.getAuthor());
            comment.setText(commentDto.getText());
            commentRepository.save(comment);
            return new ResponseEntity<>(commentDto,HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Data not found",HttpStatus.NOT_FOUND);
        }
    }

}
