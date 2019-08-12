package com.aud.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aud.demo.model.Author;


@RestController
@RequestMapping("/author")
public class AuthorController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PostMapping("/register")
	public Author index(@RequestBody Author author) {
		author.setId(1L);
		
		logger.info("Author -> {}",author.toString());
		return author;
		
		
		
	}
}
