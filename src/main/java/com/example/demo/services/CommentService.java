package com.example.demo.services;

import com.example.demo.dto.CommentDto;
import com.example.demo.entity.Comment;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repos.CommentRepository;
import com.example.demo.responseDto.ResponseCommentDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ModelMapper modelMapper;


    public List<ResponseCommentDto> getComments(Long topicId) {
        List<ResponseCommentDto> listOfResponseCommentDto = commentRepository.findByTopicId(topicId).stream()
                .map(comment -> modelMapper.map(comment, ResponseCommentDto.class))
                .toList();
        return Optional.of(listOfResponseCommentDto)
                .filter(list->!list.isEmpty())
                .map(list->listOfResponseCommentDto)
                .orElseThrow(()->new ResourceNotFoundException("No comments found"));
    }

    public ResponseCommentDto getCommentById(Long commentId){
        return commentRepository.findById(commentId)
                .map(comment -> {
                    ResponseCommentDto responseCommentDto = modelMapper.map(comment, ResponseCommentDto.class);
                    return responseCommentDto;
                })
                .orElseThrow(()-> new ResourceNotFoundException("Comment with id "+ commentId + " not found"));
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

    public ResponseCommentDto updateComment(Long commentId, CommentDto commentDto){
        return commentRepository.findById(commentId)
                .map(comment -> {
                    comment.setAuthor(commentDto.getAuthor());
                    comment.setText(commentDto.getText());
                    commentRepository.save(comment);
                    ResponseCommentDto responseCommentDto = modelMapper.map(comment, ResponseCommentDto.class);
                    return responseCommentDto;
                })
                .orElseThrow(()->new ResourceNotFoundException("Comment with id "+ commentId + " not found"));
    }

}
