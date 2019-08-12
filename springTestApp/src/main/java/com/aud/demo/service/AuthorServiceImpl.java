package com.aud.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aud.demo.model.Author;
import com.aud.demo.repository.AuthorRepository;

@Service
public class AuthorServiceImpl implements AuthorService{
	
	@Autowired
	AuthorRepository ar;

	@Override
	public Author findAuthorByEmail(String email) {
		// TODO Auto-generated method stub
		return ar.findAuthorByEmail(email);
	}

	@Override
	public List<Author> findAll() {
		// TODO Auto-generated method stub
		return ar.findAll();
	}

	@Override
	public void saveAuthor(Author author) {
		// TODO Auto-generated method stub
		
		ar.save(author);
	}

	@Override
	public void updateAuthor(Author author) {
		// TODO Auto-generated method stub
		
		
		ar.save(author);
		
	}

	@Override
	public Author findAuthorById(long id) {
		// TODO Auto-generated method stub
		
		 Optional<Author> OpAuthor = ar.findById(id);
		 if(OpAuthor.isPresent()) {
			return OpAuthor.get(); 
		 }else {
		 return null;
		 }
	}

}
