package com.example.demo.controller;

import com.example.demo.dto.TopicDto;
import com.example.demo.exceptions.GlobalExceptionHandler;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.responseDto.ResponseTopicDto;
import com.example.demo.services.TopicService;
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
public class TopicControllerTests {

    private MockMvc mockMvc;

    @Mock
    private TopicService topicService;

    @InjectMocks
    private TopicController topicController;

    private JacksonTester<ResponseTopicDto> responseTopicDtoJacksonTester;

    private JacksonTester<List> responseTopicDtoListJacksonTester;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(topicController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }


    @Test
    public void testGetAllTopicsList() throws Exception {
        ResponseTopicDto topicOne = new ResponseTopicDto(1L, "Help", "Me", 1L);
        ResponseTopicDto topicTwo = new ResponseTopicDto(2L, "Petr", "second", 1L);
        ResponseTopicDto topicThree = new ResponseTopicDto(3L, "Alex", "third", 1L);

        List<ResponseTopicDto> list = List.of(topicOne, topicTwo, topicThree);
        given(topicService.getAllTopicsList())
                .willReturn(list);
        MockHttpServletResponse response = mockMvc.perform(get("/topics"))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        assertThat(response.getContentAsString()).isEqualTo(responseTopicDtoListJacksonTester.write(list).getJson());
    }

    @Test
    public void testGetAllTopicsListWhenDoesNotExists() throws Exception {
        given(topicService.getAllTopicsList())
                .willThrow(new ResourceNotFoundException("No topics found"));
        mockMvc.perform(get("/topics"))
                .andExpect(status().isNotFound());

    }

    @Test
    public void testFindById() throws Exception {
        ResponseTopicDto topicDto = new ResponseTopicDto(2L, "Help", "Me", 1L);
        given(topicService.findById(2L))
                .willReturn(topicDto);
        MockHttpServletResponse response = mockMvc.perform(get("/topic/2"))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        assertThat(response.getContentAsString()).isEqualTo(responseTopicDtoJacksonTester.write(topicDto).getJson());
    }

    @Test
    public void testFindByIdWhenDoesNotExists() throws Exception {
        given(topicService.findById(2L))
                .willThrow(new ResourceNotFoundException("Comments with such id not found"));
        mockMvc.perform(get("/topic/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testSaveTopic() throws Exception {
        mockMvc.perform(post("/topic")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"new topic123123\",\"author\":\"mememe\",\"userId\":1}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testSaveTopicWithBadParams() throws Exception {
        mockMvc.perform(post("/topic")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"author\":\"5mindel\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteTopic() throws Exception {
        doNothing().when(topicService).deleteTopic(anyLong());
        mockMvc.perform(delete("/topic/2"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteTopicWhenDoesNotExists() throws Exception {
        doThrow(new ResourceNotFoundException("Topic with such id not found")).when(topicService).deleteTopic(2L);
        mockMvc.perform(delete("/topic/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateTopic() throws Exception {
        mockMvc.perform(put("/topic/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"new topic123123\",\"author\":\"mememe\",\"userId\":1}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateTopicWithBadParams() throws Exception {
        mockMvc.perform(put("/topic/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"author\":\"5mindel\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateTopicWhenDoesNotExists() throws Exception {
        TopicDto topicDto = new TopicDto("Help", "Me", 1L);
        given(topicService.updateTopic(1L, topicDto))
                .willThrow(new ResourceNotFoundException("Comments with such id not found"));
        mockMvc.perform(put("/topic/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Help\",\"author\":\"Me\",\"userId\":1}"))
                .andExpect(status().isNotFound());
    }
}
