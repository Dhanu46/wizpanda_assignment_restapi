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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Controller
@RequestMapping("/api")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class StudentController {

    private final StudentService studentService;
    private RefreshTokenService refreshTokenService;

    @GetMapping("/students")
    public ResponseEntity<List> getStudents(){
        return studentService.getStudents();
    }

    @PostMapping("/student/create")
    public ResponseEntity<String> addStudent(@Valid @RequestBody AddStudentRequest addStudentRequest){
        return studentService.addStudent(addStudentRequest);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody LoginRequest loginRequest) throws StudentNotFoundException {
        return studentService.login(loginRequest);
    }

    @PostMapping("/auth/refresh/token")
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) throws StudentNotFoundException {
        return studentService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(OK).body("Refresh Token Deleted Successfully!!");
    }
}
