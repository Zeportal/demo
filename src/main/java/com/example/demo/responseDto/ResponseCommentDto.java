package com.example.demo.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseCommentDto {

    @NotEmpty
    Long commentId;
    @NotEmpty
    String author;
    @NotEmpty
    String text;
    @NotEmpty
    Long topicId;

}