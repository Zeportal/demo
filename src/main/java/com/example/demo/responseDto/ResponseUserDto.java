package com.example.demo.responseDto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@NoArgsConstructor
public class ResponseUserDto {

    private Long userId;
    @NotBlank
    private String userLogin;
}
