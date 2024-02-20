package com.example.demo.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;


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