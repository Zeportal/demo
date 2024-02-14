package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;


@Data
@NoArgsConstructor
public class UserDto {
    private Long userId;
    @NotEmpty
    private String userLogin;
}
