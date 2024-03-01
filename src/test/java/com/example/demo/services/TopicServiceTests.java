package com.example.demo.services;

import com.example.demo.dto.TopicDto;
import com.example.demo.entity.Topic;
import com.example.demo.properties.YamlProperties;
import com.example.demo.repos.TopicRepository;
import com.example.demo.responseDto.ResponseTopicDto;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TopicServiceTests {
    @InjectMocks
    private TopicService service;
    @Mock
    private TopicRepository repository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private YamlProperties yamlProperties;

    @Test
    void testGetTopics() {
        Topic topicOne = new Topic(1L, "Help", "Me");
        Topic topicTwo = new Topic(2L, "Petr", "second");
        Topic topicThree = new Topic(3L, "Alex", "third");

        List<Topic> topics = List.of(topicOne, topicTwo, topicThree);
        when(repository.findAll()).thenReturn(topics);

        ResponseTopicDto responseTopicDtoOne = ResponseTopicDto.builder()
                .topicId(1L)
                .author("Help")
                .title("Me")
                .build();
        ResponseTopicDto responseTopicDtoTwo = ResponseTopicDto.builder()
                .topicId(2L)
                .author("Petr")
                .title("Second")
                .build();
        ResponseTopicDto responseTopicDtoThree = ResponseTopicDto.builder()
                .topicId(3L)
                .author("Alex")
                .title("Third")
                .topicId(1L)
                .build();
        when(modelMapper.map(topicOne, ResponseTopicDto.class)).thenReturn(responseTopicDtoOne);
        when(modelMapper.map(topicTwo, ResponseTopicDto.class)).thenReturn(responseTopicDtoTwo);
        when(modelMapper.map(topicThree, ResponseTopicDto.class)).thenReturn(responseTopicDtoThree);
        when(yamlProperties.getOutputLimit()).thenReturn(5);
        List<ResponseTopicDto> topicDtoList = service.getAllTopicsList();
        assertEquals(topics.size(), topicDtoList.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetTopicById() {
        Topic topic = new Topic(1L, "Stealer", "Im here");
        when(repository.findById(1L)).thenReturn(Optional.of(topic));
        ResponseTopicDto responseTopicDtoOne = ResponseTopicDto.builder()
                .topicId(1L)
                .author("Stealer")
                .title("Im here")
                .build();
        when(modelMapper.map(topic, ResponseTopicDto.class)).thenReturn(responseTopicDtoOne);

        ResponseTopicDto responseTopicDto = service.findById(1L);

        assertEquals(responseTopicDto.getTitle(), topic.getTitle());
        verify(repository, times(1)).findById(1L);
        verify(modelMapper, times(1)).map(topic, ResponseTopicDto.class);
    }

    @Test
    void testSaveTopic() {
        Topic topic = new Topic(1L, "Stealer", "Im here");
        TopicDto topicDto = new TopicDto(1L, "Stealer", "Im here");
        ResponseTopicDto responseTopicDto = ResponseTopicDto.builder()
                .topicId(1L)
                .author("Stealer")
                .title("Im here")
                .build();

        when(modelMapper.map(topicDto, Topic.class)).thenReturn(topic);
        when(repository.save(topic)).thenReturn(topic);
        when(modelMapper.map(topic, ResponseTopicDto.class)).thenReturn(responseTopicDto);

        ResponseTopicDto out = service.saveTopic(topicDto);

        assertEquals(out.getTitle(), topicDto.getTitle());
        verify(modelMapper, times(1)).map(topicDto, Topic.class);
        verify(repository, times(1)).save(topic);
        verify(modelMapper, times(1)).map(topic, ResponseTopicDto.class);

    }

    @Test
    void testDeleteComment() {
        assertDoesNotThrow(() -> service.deleteTopic(1L));
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void updateComment() {
        Topic topic = new Topic(1L, "pre", "pres text");
        TopicDto topicDto = new TopicDto(1L, "after", "afters text");
        ResponseTopicDto responseTopicDto = ResponseTopicDto.builder()
                .topicId(1L)
                .author("after")
                .title("afters text")
                .build();
        when(repository.findById(1L)).thenReturn(Optional.of(topic));
        topic.setTitle("after");
        topic.setAuthor("afters text");
        when(repository.save(topic)).thenReturn(topic);
        when(modelMapper.map(topic, ResponseTopicDto.class)).thenReturn(responseTopicDto);

        ResponseTopicDto out = service.updateTopic(1L, topicDto);

        assertEquals(out.getTitle(), topicDto.getTitle());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(topic);
        verify(modelMapper, times(1)).map(topic, ResponseTopicDto.class);
    }
}
