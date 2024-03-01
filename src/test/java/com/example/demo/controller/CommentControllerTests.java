package com.example.demo.controller;

import com.example.demo.dto.CommentDto;
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
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CommentControllerTests {

    private MockMvc mockMvc;

    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentController commentController;

    private JacksonTester<ResponseCommentDto> responseCommentDtoJacksonTester;

    private JacksonTester<List> responseCommentDtoListJacksonTester;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(commentController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }


    @Test
    public void testGetComments() throws Exception {
        ResponseCommentDto commentOne = new ResponseCommentDto(1L, "Help", "Me", 1L);
        ResponseCommentDto commentTwo = new ResponseCommentDto(2L, "Petr", "second", 1L);
        ResponseCommentDto commentThree = new ResponseCommentDto(3L, "Alex", "third", 1L);

        List<ResponseCommentDto> responseCommentDtos = List.of(commentOne, commentTwo, commentThree);
        given(commentService.getComments(1L))
                .willReturn(responseCommentDtos);
        MockHttpServletResponse response = mockMvc.perform(get("/topic/1/comments"))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        assertThat(response.getContentAsString()).isEqualTo(responseCommentDtoListJacksonTester.write(responseCommentDtos).getJson());
    }

    @Test
    public void testGetCommentsWhenDoesNotExists() throws Exception {
        given(commentService.getComments(1L))
                .willThrow(new ResourceNotFoundException("No comments found"));
        mockMvc.perform(get("/topic/1/comments"))
                .andExpect(status().isNotFound());

    }

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
    public void testGetCommentByIdWhenDoesNotExists() throws Exception {
        given(commentService.getCommentById(2L))
                .willThrow(new ResourceNotFoundException("Comments with such id not found"));
        mockMvc.perform(get("/topic/1/2"))
                .andExpect(status().isNotFound());
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
    public void testDeleteCommentWhenDoesNotExists() throws Exception {
        doThrow(new ResourceNotFoundException("Comment with such id not found")).when(commentService).deleteComment(2L);
        mockMvc.perform(delete("/topic/1/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateComment() throws Exception {
        mockMvc.perform(put("/topic/1/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"author\":\"5mindel\",\"text\":\"0.1 atm\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateCommentWithBadParams() throws Exception {
        mockMvc.perform(put("/topic/1/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"author\":\"5mindel\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateCommentWhenDoesNotExists() throws Exception {
        CommentDto commentDto = new CommentDto("Help", "Me");
        given(commentService.updateComment(1L, commentDto))
                .willThrow(new ResourceNotFoundException("Comments with such id not found"));
        mockMvc.perform(put("/topic/1/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"author\":\"Help\",\"text\":\"Me\"}"))
                .andExpect(status().isNotFound());
    }
}
