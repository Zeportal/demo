package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    Long commentId;
    @NotEmpty
    String author;
    @NotEmpty
    String text;
    Long topicId;

}
