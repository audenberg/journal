package com.aud.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aud.demo.model.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

	public Author findAuthorByEmail(String email);
}
