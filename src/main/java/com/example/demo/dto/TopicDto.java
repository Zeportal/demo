package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicDto {
    private Long topicId;
    @NotBlank
    private String title;
    @NotBlank
    private String author;
    private Long userId;

    public TopicDto(Long topicId, String author, String title) {
        this.topicId = topicId;
        this.title = title;
        this.author = author;
    }

    public TopicDto(String title, String author, Long userId) {
        this.title = title;
        this.author = author;
        this.userId = userId;
    }
}
