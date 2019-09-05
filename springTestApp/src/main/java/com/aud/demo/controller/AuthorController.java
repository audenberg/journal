package com.aud.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.minidev.json.JSONObject;




@RestController
@RequestMapping("/author")
public class AuthorController {

	@Autowired
	AuthorServiceImpl authorService;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PostMapping("/register")
	public String registerAuthor(@RequestBody @Valid Author author, BindingResult bindingResult) {
		
		return saveOrUpdate(author, bindingResult);
		
	}
	
	
	@PutMapping("/register")
	public String updateAuthor(@RequestBody @Valid Author author, BindingResult bindingResult) {
		
		
		return saveOrUpdate(author, bindingResult);
		
		
	}
	
	
	
public String saveOrUpdate(Author author, BindingResult bindingResult) {
		
		String fieldName="";
		String errorMsg="";
		
		JSONObject responce = new JSONObject();
		JSONObject errors = new JSONObject();
		

		
		if (bindingResult.hasErrors()) {
			
			 logger.info("Author->{} Binding results {}",author,bindingResult.getAllErrors());
			
			 responce.put("type", "error");
			
			
			 for (Object object : bindingResult.getAllErrors()) {
				 
				    if(object instanceof FieldError) {
				        FieldError fieldError = (FieldError) object;
				        fieldName = fieldError.getField()+"Err";
				        logger.info(" Binding Codes-> {}",fieldName);
				        
				    }

				    if(object instanceof ObjectError) {
				        ObjectError objectError = (ObjectError) object;
				        errorMsg = objectError.getDefaultMessage();
				        logger.info(" Binding Errors-> {} Message {}",objectError.getCode(),errorMsg);
				    }
				    errors.put(fieldName, errorMsg);
				    
				    
				}
			 responce.put("errors", errors);
			 return responce.toJSONString();
		}else {
	    author.setPassword( new BCryptPasswordEncoder().encode(author.getPassword()));
		Long id = authorService.saveAuthor(author);
		author.setId(id);
		
		logger.info("Author -> {}",author.toString());
			responce.put("type", "success");
			responce.put("obj", author);
			return responce.toJSONString();
			
		}	
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
