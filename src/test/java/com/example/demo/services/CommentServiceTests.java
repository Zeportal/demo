package com.example.demo.services;

import com.example.demo.entity.Comment;
import com.example.demo.repos.CommentRepository;
import com.example.demo.responseDto.ResponseCommentDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTests {
    @InjectMocks
    CommentService service;
    @Mock
    CommentRepository repository;
    @Mock
    ModelMapper modelMapper;
    @Test
    void testGetComments() {
        List<Comment> list = new ArrayList<>();
        Comment commentOne = new Comment((long) 1,"asdd", "dddasd",(long) 1);
        Comment commentTwo = new Comment((long) 2,"Petr","second", (long) 1);
        Comment commentThree = new Comment((long) 3,"Alex","third", (long) 1);

        list.add(commentOne);
        list.add(commentTwo);
        list.add(commentThree);
        System.out.println(commentOne.getTopicId());
        when(repository.findByTopicId((long) 1)).thenReturn(list);

        List<ResponseCommentDto> commentDtoList = service.getComments((long)1);

        assertEquals(3,commentDtoList.size());
        verify(repository, times(1)).findByTopicId((long) 1);
    }
    @Test
    void testGetCommentById() {
        List<Comment> list = new ArrayList<>();
        Comment comment = new Comment((long) 1,"ASdss", "asdad", (long) 1);
        list.add(comment);
        System.out.println(Optional.of(comment));
        when(repository.findById((long) 1)).thenReturn(Optional.of(comment));
        ResponseCommentDto responseCommentDto = service.getCommentById((long) 1);
        verify(repository, times(1)).findById((long) 1);
    }
}
