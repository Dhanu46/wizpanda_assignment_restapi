package com.wizpanda.assignment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Table(name = "student")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long studentId;

    @NotNull(message = "Name Can't be null")
    @Column(unique = true)
    private String name;

    @NotNull(message = "Email Can't be Null")
    @Email(message = "Must be a Valid email")
    private String email;

    @NotNull(message = "Password Can't be Null")
    private String password;

    @NotNull(message = "Phone Number can't be Null")
    @Size(min=10,max=10,message = "Phone number should be 10 digits")
    private String phoneNumber;
}
