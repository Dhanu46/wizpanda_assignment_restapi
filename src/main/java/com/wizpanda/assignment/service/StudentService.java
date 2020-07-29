package com.wizpanda.assignment.service;

import com.wizpanda.assignment.dto.AddStudentRequest;
import com.wizpanda.assignment.dto.AuthenticationResponse;
import com.wizpanda.assignment.dto.LoginRequest;
import com.wizpanda.assignment.dto.RefreshTokenRequest;
import com.wizpanda.assignment.exception.StudentNotFoundException;
import com.wizpanda.assignment.model.Student;
import com.wizpanda.assignment.respository.StudentRepository;
import com.wizpanda.assignment.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    public ResponseEntity<List> getStudents() {
        List<Student> students = new ArrayList<>();
        studentRepository.findAll().forEach(students::add);
       return ResponseEntity.status(200).body(students);
    }

    @Transactional
    public ResponseEntity<Student> addStudent(AddStudentRequest addStudentRequest) {
        Student student = new Student();
        student.setName(addStudentRequest.getName());
        student.setEmail(addStudentRequest.getEmail());
        student.setPassword(passwordEncoder.encode(addStudentRequest.getPassword()));
        student.setPhoneNumber(addStudentRequest.getPhoneNumber());
        studentRepository.save(student);
        return ResponseEntity.status(201).body(student);

    }

    public ResponseEntity<AuthenticationResponse> login(LoginRequest loginRequest) throws StudentNotFoundException {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
        return ResponseEntity.status(200).body(AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(loginRequest.getUserName())
                .build());
    }
    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) throws StudentNotFoundException {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(refreshTokenRequest.getUsername())
                .build();
    }

}
