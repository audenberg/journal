package com.aud.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aud.demo.model.Reviewer;

@Repository
public interface ReviewerRepository extends JpaRepository<Reviewer,Long> {
	
      public Reviewer findReviewerByEmail(String email);
}
