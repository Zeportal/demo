package com.example.demo.responseDto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class ResponseUserDto {
    @NotEmpty
    private Long userId;
    @NotEmpty
    private String userLogin;
}
