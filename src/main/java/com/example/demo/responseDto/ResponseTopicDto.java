package com.example.demo.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTopicDto {
    @NotEmpty
    Long topicId;
    @NotEmpty
    String title;
    @NotEmpty
    String author;
    @NotEmpty
    String userId;

}
