package com.example.demo.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CommentDto {

    Long commentId;

    String author;

    String text;

    Long topicId;

}
