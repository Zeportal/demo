package com.example.demo.services;

import com.example.demo.client.ExternalValidationClient;
import com.example.demo.config.InternalVariables;
import com.example.demo.dto.CommentDto;
import com.example.demo.entity.Comment;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repos.CommentRepository;
import com.example.demo.responseDto.ResponseCommentDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    private final static int LIST_STARTING_POSITION = 0;
    private final RestTemplate restTemplate;
    private final CommentRepository commentRepository;
    private final ExternalValidationClient externalValidationClient;
    private final ModelMapper modelMapper;
    private final InternalVariables properties;


    public List<ResponseCommentDto> getComments(Long topicId) {
        List<ResponseCommentDto> listOfResponseCommentDto = commentRepository.findByTopicId(topicId).stream()
                .map(comment -> modelMapper.map(comment, ResponseCommentDto.class))
                .toList();
        if (listOfResponseCommentDto.isEmpty()) {
            throw new ResourceNotFoundException("No comments found");
        }
        return listOfResponseCommentDto.size() > properties.getOutputLimit() ? listOfResponseCommentDto.subList(LIST_STARTING_POSITION, properties.getOutputLimit()) : listOfResponseCommentDto;
    }

    public ResponseCommentDto getCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .map(comment -> modelMapper.map(comment, ResponseCommentDto.class))
                .orElseThrow(() -> new ResourceNotFoundException("Comment with id " + commentId + " not found"));
    }

    public ResponseCommentDto saveComment(CommentDto commentDto, Long topicId) {
        if (externalValidationClient.validateComment(commentDto.getText(), commentDto.getAuthor(), topicId)) {
            System.out.println("WORKING");
        }
        final String externalServiceUrl = "http://localhost:8090/topic/";
        String text = commentDto.getText();
        String author = commentDto.getAuthor();
        String url = externalServiceUrl + topicId + "?text=" + text + "&author=" + author;
        if (restTemplate.getForObject(url, Boolean.class)) {
            System.out.println("REST WORKING");
        }
        return SaveCommentReturnCommentDto(topicId, commentDto);
    }


    public void deleteComment(Long commentId) {
        try {
            commentRepository.deleteById(commentId);
        } catch (Exception ex) {
            throw new ResourceNotFoundException("Comment with id " + commentId + " not found");
        }
    }

    public ResponseCommentDto updateComment(Long commentId, CommentDto commentDto) {
        return commentRepository.findById(commentId)
                .map(comment -> checkComment(commentDto, comment))
                .orElseThrow(() -> new ResourceNotFoundException("Comment with id " + commentId + " not found"));
    }

    private ResponseCommentDto checkComment(CommentDto commentDto, Comment comment) {
        if (externalValidationClient.validateComment(commentDto.getText(), commentDto.getAuthor(), comment.getTopicId())) {
            System.out.println("WORKING");
        }
        return UpdateCommentDto(commentDto, comment);
    }

    private ResponseCommentDto UpdateCommentDto(CommentDto commentDto, Comment comment) {
        comment.setAuthor(commentDto.getAuthor());
        comment.setText(commentDto.getText());
        commentRepository.save(comment);
        return modelMapper.map(comment, ResponseCommentDto.class);
    }

    private ResponseCommentDto SaveCommentReturnCommentDto(Long topicId, CommentDto dto) {
        dto.setTopicId(topicId);
        Comment comment = modelMapper.map(dto, Comment.class);
        commentRepository.save(comment);
        return modelMapper.map(comment, ResponseCommentDto.class);
    }

}
