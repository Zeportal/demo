package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Long commentId;
    @NotBlank
    private String author;
    @NotBlank
    private String text;
    private Long topicId;

    public CommentDto(String author, String text) {
        this.author = author;
        this.text = text;
    }
}
