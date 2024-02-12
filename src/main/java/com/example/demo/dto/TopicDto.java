package com.example.demo.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicDto {
    @NotEmpty
    Long topicId;
    @NotEmpty
    String title;
    @NotEmpty
    String author;
    @NotEmpty
    String userId;

}
