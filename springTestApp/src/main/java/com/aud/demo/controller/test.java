package com.aud.demo.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test {

	
	
	@RequestMapping(value="/test" ,method = RequestMethod.GET)
	public String registration() {
		return new BCryptPasswordEncoder().encode("password");
	}
	
}
