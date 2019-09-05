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


import com.aud.demo.model.Reviewer;

import com.aud.demo.service.ReviewerServiceImpl;

import net.minidev.json.JSONObject;

@RestController
@RequestMapping("/reviewer")
public class ReviewerController {


	@Autowired
	ReviewerServiceImpl reviewerService;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PostMapping("/register")
	public String registerReviewer(@RequestBody @Valid Reviewer reviewer, BindingResult bindingResult) {
		
		return saveOrUpdate(reviewer, bindingResult);
		
	}
	
	
	@PutMapping("/register")
	public String updateReviewer(@RequestBody @Valid Reviewer reviewer, BindingResult bindingResult) {
		
		
		return saveOrUpdate(reviewer, bindingResult);
		
		
	}
	
	
	
public String saveOrUpdate(Reviewer reviewer, BindingResult bindingResult) {
		
		String fieldName="";
		String errorMsg="";
		
		JSONObject responce = new JSONObject();
		JSONObject errors = new JSONObject();
		

		
		if (bindingResult.hasErrors()) {
			
			 logger.info("reviewer->{} Binding results {}",reviewer,bindingResult.getAllErrors());
			
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
			reviewer.setPassword( new BCryptPasswordEncoder().encode(reviewer.getPassword()));
		Long id = reviewerService.saveReviewer(reviewer);
		reviewer.setId(id);
		
		logger.info("reviewer -> {}",reviewer.toString());
			responce.put("type", "success");
			responce.put("obj", reviewer);
			return responce.toJSONString();
			
		}	
}
	
	
	@DeleteMapping("/delete/{reviewerId}")
	public String deleteAuthor(@PathVariable long reviewerId) {
		
		reviewerService.deleteById(reviewerId);
		logger.info("Reviewer has been deleted with id : {}",reviewerId);
//		String responce = "{type:'success',text:'Author has been deleted'}";
		JSONObject response = new JSONObject();
		response.put("type","success");
		response.put("text","reviewer has been deleted");
		
		
		
		
		return response.toString();
		
	}
	
	@GetMapping("/fetchAll")
	public List<Reviewer> getAllReviewers() {
		
		
		
		
		return reviewerService.findAll();
		
		
		
	}
	
}
