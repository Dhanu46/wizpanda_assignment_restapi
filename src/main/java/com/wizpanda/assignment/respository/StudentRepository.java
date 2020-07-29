package com.wizpanda.assignment.respository;

import com.wizpanda.assignment.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student,Long> {
    Optional<Student> findByName(String userName);
}
