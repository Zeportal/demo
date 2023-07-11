package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TopicDto {
    Long topicId;

    String title;

    String author;

//    @JsonProperty("user")
//    UserDto userDto;
//
//    @JsonProperty("comments")
//    Set<CommentDto> commentDto;
}
