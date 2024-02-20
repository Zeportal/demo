package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@NoArgsConstructor
public class UserDto {
    private Long userId;
    @NotBlank
    private String userLogin;
}
