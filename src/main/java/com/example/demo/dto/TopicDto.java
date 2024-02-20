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
    private String userId;

}
