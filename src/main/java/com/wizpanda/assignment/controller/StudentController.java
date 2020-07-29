package com.wizpanda.assignment.controller;


import com.wizpanda.assignment.dto.AddStudentRequest;
import com.wizpanda.assignment.dto.AuthenticationResponse;
import com.wizpanda.assignment.dto.LoginRequest;
import com.wizpanda.assignment.dto.RefreshTokenRequest;
import com.wizpanda.assignment.exception.StudentNotFoundException;
import com.wizpanda.assignment.model.Student;
import com.wizpanda.assignment.service.RefreshTokenService;
import com.wizpanda.assignment.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Controller
@RequestMapping("/api")
@AllArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private RefreshTokenService refreshTokenService;

    @GetMapping("/students")
    public ResponseEntity<List> getStudents(){
        return studentService.getStudents();
    }

    @PostMapping("/student/create")
    public ResponseEntity<Student> addStudent(@Valid @RequestBody AddStudentRequest addStudentRequest){
        return studentService.addStudent(addStudentRequest);
    }

    @PostMapping("/student/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) throws StudentNotFoundException {
        return studentService.login(loginRequest);
    }

    @PostMapping("/student/refresh/token")
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) throws StudentNotFoundException {
        return studentService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/student/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(OK).body("Refresh Token Deleted Successfully!!");
    }
}
