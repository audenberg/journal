package com.aud.demo.service;

import java.util.List;

import com.aud.demo.model.Author;

public interface AuthorService {

	public Author findAuthorByEmail(String email);
	public List<Author> findAll();
	public Long saveAuthor(Author Author);
	public void updateAuthor(Author Author);
	public Author findAuthorById(long id);
	public void deleteById(long id);
//	public List<Author> findAuthorsByUsnIgnoreCaseContaining(String q);
		
}
