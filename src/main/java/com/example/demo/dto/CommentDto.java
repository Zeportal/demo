package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    @NotEmpty
    Long commentId;
    @NotEmpty
    String author;
    @NotEmpty
    String text;
    @NotEmpty
    Long topicId;

}
