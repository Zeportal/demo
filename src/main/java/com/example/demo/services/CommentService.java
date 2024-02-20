package com.example.demo.services;

import com.example.demo.dto.CommentDto;
import com.example.demo.entity.Comment;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repos.CommentRepository;
import com.example.demo.responseDto.ResponseCommentDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {


    private final CommentRepository commentRepository;


    private final ModelMapper modelMapper;

    public CommentService(CommentRepository commentRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
    }


    public List<ResponseCommentDto> getComments(Long topicId) {
        List<ResponseCommentDto> listOfResponseCommentDto = commentRepository.findByTopicId(topicId).stream()
                .map(comment -> modelMapper.map(comment, ResponseCommentDto.class))
                .toList();
        if (listOfResponseCommentDto.isEmpty()) throw new ResourceNotFoundException("No comments found");
        else return listOfResponseCommentDto;
    }

    public ResponseCommentDto getCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .map(comment -> modelMapper.map(comment, ResponseCommentDto.class))
                .orElseThrow(() -> new ResourceNotFoundException("Comment with id " + commentId + " not found"));
    }

    public ResponseCommentDto saveComment(CommentDto commentDto, Long topicId) {
        return Optional.of(commentDto)
                .map(dto -> {
                    dto.setTopicId(topicId);
                    Comment comment = modelMapper.map(dto, Comment.class);
                    commentRepository.save(comment);
                    return modelMapper.map(comment, ResponseCommentDto.class);
                })
                .orElseThrow(() -> new IllegalArgumentException("Invalid data provided"));
    }

    public void deleteComment(Long commentId) {
        try {
            commentRepository.deleteById(commentId);
        } catch (Exception ex) {
            throw new ResourceNotFoundException("Comment with id "+commentId+" not found");
        }
    }

    public ResponseCommentDto updateComment(Long commentId, CommentDto commentDto) {
        return commentRepository.findById(commentId)
                .map(comment -> {
                    comment.setAuthor(commentDto.getAuthor());
                    comment.setText(commentDto.getText());
                    commentRepository.save(comment);
                    return modelMapper.map(comment, ResponseCommentDto.class);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Comment with id " + commentId + " not found"));
    }

}
