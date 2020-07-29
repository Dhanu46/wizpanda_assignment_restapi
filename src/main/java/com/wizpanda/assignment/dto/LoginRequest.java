package com.wizpanda.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotBlank(message = "username can't be blank")
    @Size(min = 5, message = "Username must contain at least 5 Characters")
    private String userName;
    @NotBlank(message = "Password Can't be blank")
    @Size(min=8, message = "Password can't be less than 8 Characters")
    private String password;
}
