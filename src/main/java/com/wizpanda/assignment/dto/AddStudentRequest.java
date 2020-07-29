package com.wizpanda.assignment.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Size;


import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddStudentRequest {
    @NotBlank(message = "name is mandatory")
    @Size(min = 5,max = 255,message = "name must be at least 5 characters")
    private String name;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Must be a valid Email")
    private String email;

    @NotBlank(message = "Phone Number Can't be Null")
    @Size(min = 10, max = 10, message = "Phone Number must contain 10 digits")
    private String phoneNumber;

    @NotBlank(message = "Password Can't be Null")
    @Pattern(message = "Password Should contain minimum eight characters, " +
            "at least one uppercase letter, one lowercase letter" +
            " on number, one special character",regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
    private String password;

}
