package com.example.demo.services;

import com.example.demo.client.ExternalValidationClient;
import com.example.demo.config.InternalVariables;
import com.example.demo.dto.CommentDto;
import com.example.demo.entity.Comment;
import com.example.demo.repos.CommentRepository;
import com.example.demo.responseDto.ResponseCommentDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTests {
    @InjectMocks
    private CommentService service;
    @Mock
    private CommentRepository repository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private InternalVariables internalVariables;
    @Mock
    private ExternalValidationClient externalValidationClient;
    @Mock
    private RestTemplate restTemplate;

    @Test
    void testGetComments() {
        Comment commentOne = new Comment(1L, "Help", "Me", 1L);
        Comment commentTwo = new Comment(2L, "Petr", "second", 1L);
        Comment commentThree = new Comment(3L, "Alex", "third", 1L);

        List<Comment> list = List.of(commentOne, commentTwo, commentThree);
        when(repository.findByTopicId(1L)).thenReturn(list);

        ResponseCommentDto responseCommentDtoOne = ResponseCommentDto.builder()
                .commentId(1L)
                .author("Help")
                .text("Me")
                .topicId(1L)
                .build();
        ResponseCommentDto responseCommentDtoTwo = ResponseCommentDto.builder()
                .commentId(2L)
                .author("Petr")
                .text("Second")
                .topicId(1L)
                .build();
        ResponseCommentDto responseCommentDtoThree = ResponseCommentDto.builder()
                .commentId(3L)
                .author("Alex")
                .text("Third")
                .topicId(1L)
                .build();
        when(modelMapper.map(commentOne, ResponseCommentDto.class)).thenReturn(responseCommentDtoOne);
        when(modelMapper.map(commentTwo, ResponseCommentDto.class)).thenReturn(responseCommentDtoTwo);
        when(modelMapper.map(commentThree, ResponseCommentDto.class)).thenReturn(responseCommentDtoThree);
        when(internalVariables.getOutputLimit()).thenReturn(5);
        List<ResponseCommentDto> commentDtoList = service.getComments(1L);


        assertEquals(list.size(), commentDtoList.size());
        verify(repository, times(1)).findByTopicId(1L);
    }

    @Test
    void testGetCommentById() {
        Comment comment = new Comment(1L, "Stealer", "Im here", 1L);
        when(repository.findById(1L)).thenReturn(Optional.of(comment));
        ResponseCommentDto responseCommentDtoOne = ResponseCommentDto.builder()
                .commentId(1L)
                .author("Stealer")
                .text("Im here")
                .topicId(1L)
                .build();
        when(modelMapper.map(comment, ResponseCommentDto.class)).thenReturn(responseCommentDtoOne);

        ResponseCommentDto responseCommentDto = service.getCommentById(1L);

        assertEquals(responseCommentDto.getText(), comment.getText());
        verify(repository, times(1)).findById(1L);
        verify(modelMapper, times(1)).map(comment, ResponseCommentDto.class);
    }

    @Test
    void testSaveComment() {
        Comment comment = new Comment(1L, "Stealer", "Im here", 1L);
        CommentDto commentDto = new CommentDto(1L, "Stealer", "Im here", 1L);
        ResponseCommentDto responseCommentDto = new ResponseCommentDto(1L, "Stealer", "Im here", 1L);

        when(modelMapper.map(commentDto, Comment.class)).thenReturn(comment);
        when(repository.save(comment)).thenReturn(comment);
        when(modelMapper.map(comment, ResponseCommentDto.class)).thenReturn(responseCommentDto);
        when(restTemplate.getForObject(anyString(), eq(Boolean.class))).thenReturn(true);

        ResponseCommentDto out = service.saveComment(commentDto, 1L);

        assertEquals(out.getText(), commentDto.getText());
        verify(modelMapper, times(1)).map(commentDto, Comment.class);
        verify(repository, times(1)).save(comment);
        verify(modelMapper, times(1)).map(comment, ResponseCommentDto.class);

    }

    @Test
    void testDeleteComment() {
        assertDoesNotThrow(() -> service.deleteComment(1L));
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void updateComment() {
        Comment comment = new Comment(1L, "pre", "pres text", 1L);
        CommentDto commentDto = new CommentDto(1L, "after", "afters text", 1L);
        ResponseCommentDto responseCommentDto = new ResponseCommentDto(1L, "after", "afters text", 1L);
        when(repository.findById(1L)).thenReturn(Optional.of(comment));
        comment.setText("after");
        comment.setAuthor("afters text");
        when(repository.save(comment)).thenReturn(comment);
        when(modelMapper.map(comment, ResponseCommentDto.class)).thenReturn(responseCommentDto);

        ResponseCommentDto out = service.updateComment(1L, commentDto);

        assertEquals(out.getText(), commentDto.getText());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(comment);
        verify(modelMapper, times(1)).map(comment, ResponseCommentDto.class);
    }
}
