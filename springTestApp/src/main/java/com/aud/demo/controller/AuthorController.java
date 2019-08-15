package com.aud.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aud.demo.model.Author;
import com.aud.demo.service.AuthorServiceImpl;

import net.minidev.json.JSONObject;


@RestController
@RequestMapping("/author")
public class AuthorController {

	@Autowired
	AuthorServiceImpl authorService;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PostMapping("/register")
	public Author registerAuthor(@RequestBody Author author) {
		
		Long id = authorService.saveAuthor(author);
		author.setId(id);
		
		logger.info("Author -> {}",author.toString());
		return author;
		
	}
	
	
	@PutMapping("/register")
	public Author updateAuthor(@RequestBody Author author) {
		
		Long id = authorService.saveAuthor(author);
		author.setId(id);
		
		logger.info("Author -> {}",author.toString());
		return author;
		
		
		
	}
	
	@DeleteMapping("/delete/{authorId}")
	public String deleteAuthor(@PathVariable long authorId) {
		
		authorService.deleteById(authorId);
		logger.info("Authro has been deleted with id : {}",authorId);
//		String responce = "{type:'success',text:'Author has been deleted'}";
		JSONObject response = new JSONObject();
		response.put("type","success");
		response.put("text","Author has been deleted");
		
		
		
		
		return response.toString();
		
	}
	
	@GetMapping("/fetchAll")
	public List<Author> getAllAuthors() {
		
		
		
		
		return authorService.findAll();
		
		
		
	}
	
}
