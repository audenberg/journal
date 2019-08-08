package com.aud.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aud.demo.model.Student;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {

	
}
