package com.aud.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aud.demo.model.Author;
import com.aud.demo.service.AuthorServiceImpl;


@RestController
@RequestMapping("/author")
public class AuthorController {

	@Autowired
	AuthorServiceImpl authorService;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PostMapping("/register")
	public Author registerAuthor(@RequestBody Author author) {
		
		authorService.saveAuthor(author);
		
		logger.info("Author -> {}",author.toString());
		return author;
		
		
		
	}
	
	@PutMapping("/register")
	public Author updateAuthor(@RequestBody Author author) {
		
		authorService.saveAuthor(author);
		
		logger.info("Author -> {}",author.toString());
		return author;
		
		
		
	}
	
	@GetMapping("/fetchAll")
	public List<Author> getAllAuthors() {
		
		
		
		
		return authorService.findAll();
		
		
		
	}
	
}
