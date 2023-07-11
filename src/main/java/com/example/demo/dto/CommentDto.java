package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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

//    @JsonProperty("topic")
//    CommentDto commentDto;

}
