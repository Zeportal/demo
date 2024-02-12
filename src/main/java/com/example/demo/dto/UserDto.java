package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class UserDto {
    @NotEmpty
    private Long userId;
    @NotEmpty
    private String userLogin;
}
