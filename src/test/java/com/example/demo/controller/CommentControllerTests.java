package com.example.demo.controller;

import com.example.demo.dto.CommentDto;
import com.example.demo.entity.Comment;
import com.example.demo.exceptions.GlobalExceptionHandler;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.responseDto.ResponseCommentDto;
import com.example.demo.services.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonbTester;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class CommentControllerTests {

    private MockMvc mockMvc;

    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentController commentController;

    private JacksonTester<ResponseCommentDto> responseCommentDtoJacksonTester;

    private JacksonTester<List> responseCommentDtoListJacksonTester;

    private JacksonTester<CommentDto> commentDtoJacksonTester;
    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
//        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(commentController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }


    @Test
    public void testGetComments() throws Exception {
        ResponseCommentDto commentOne = new ResponseCommentDto(1L, "Help", "Me", 1L);
        ResponseCommentDto commentTwo = new ResponseCommentDto(2L, "Petr", "second", 1L);
        ResponseCommentDto commentThree = new ResponseCommentDto(3L, "Alex", "third", 1L);

        List<ResponseCommentDto> list = List.of(commentOne, commentTwo, commentThree);
//        when(commentService.getComments(1L)).thenReturn(list);
        given(commentService.getComments(1L))
                .willReturn(list);
        MockHttpServletResponse response = mockMvc.perform(get("/topic/1/comments"))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        assertThat(response.getContentAsString()).isEqualTo(responseCommentDtoListJacksonTester.write(list).getJson());
    }

/*    @Test
    public void testGetCommentsWhenDoesNotExists() throws Exception {
        when(commentService.getComments(1L)).thenThrow(new ResourceNotFoundException("No comments found"));
        mockMvc.perform(get("/topic/1/comments"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No comments found"));
    }*/

    @Test
    public void testGetCommentById() throws Exception {
        ResponseCommentDto commentDto = new ResponseCommentDto(2L, "Help", "Me", 1L);
        given(commentService.getCommentById(2L))
                .willReturn(commentDto);
        MockHttpServletResponse response = mockMvc.perform(get("/topic/1/2"))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        assertThat(response.getContentAsString()).isEqualTo(responseCommentDtoJacksonTester.write(commentDto).getJson());
    }

    @Test
    public void testSaveComment() throws Exception {
        mockMvc.perform(post("/topic/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"author\":\"5mindel\",\"text\":\"0.1 atm\"}"))
                .andExpect(status().isOk());
    }
    @Test
    public void testSaveCommentWithBadParams() throws Exception {
        mockMvc.perform(post("/topic/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"author\":\"5mindel\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteComment() throws Exception {
        doNothing().when(commentService).deleteComment(anyLong());
        mockMvc.perform(delete("/topic/1/2"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateComment() throws Exception {
        mockMvc.perform(put("/topic/1/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"author\":\"5mindel\",\"text\":\"0.1 atm\"}"))
                .andExpect(status().isOk());
    }
}
