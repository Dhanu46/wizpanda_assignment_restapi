package com.wizpanda.assignment.service;

import com.wizpanda.assignment.model.Student;
import com.wizpanda.assignment.respository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

import static java.util.Collections.singletonList;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String userName){
       Optional<Student> studentOptional = studentRepository.findByName(userName);
       Student student = studentOptional.orElseThrow(()-> new UsernameNotFoundException(" No Student "+ "Found with username :"+ userName));

        return new org.springframework.security
                .core.userdetails.User(student.getName(), student.getPassword(),
                true, true, true,
                true, getAuthorities("USER"));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return singletonList(new SimpleGrantedAuthority(role));
    }
}
