package com.example.demo.responseDto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;


@Data
@NoArgsConstructor
public class ResponseUserDto {
    @NotEmpty
    private Long userId;
    @NotEmpty
    private String userLogin;
}
