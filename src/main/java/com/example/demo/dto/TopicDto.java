package com.example.demo.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TopicDto {

    Long topicId;

    String title;

    String author;

    String userId;

}
