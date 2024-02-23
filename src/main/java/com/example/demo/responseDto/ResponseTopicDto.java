package com.example.demo.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseTopicDto {

    private Long topicId;
    @NotBlank
    private String title;
    @NotBlank
    private String author;

    private Long userId;

}
