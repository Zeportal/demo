package com.example.demo.dto;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicDto {
    Long topicId;
    @NotEmpty
    String title;
    @NotEmpty
    String author;
    String userId;

}
