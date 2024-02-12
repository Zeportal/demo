package com.example.demo.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTopicDto {
    @NotEmpty
    Long topicId;
    @NotEmpty
    String title;
    @NotEmpty
    String author;
    @NotEmpty
    String userId;

}
