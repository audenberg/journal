package com.aud.demo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.aud.demo.model.Author;
import com.aud.demo.model.Role;
import com.aud.demo.repository.RoleRepository;
import com.aud.demo.service.AuthorService;



@Controller
public class LoginController {
	
	@Autowired
	private AuthorService authorService;
	
	
	
	@Autowired
    private RoleRepository roleRepository;

	@RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
	public ModelAndView login(){
		
		ModelAndView modelAndView = new ModelAndView();  //we are using thymeleaf so use modelview(obj+view)
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication(); //check authentication
		
		System.out.println("Authname"+auth.getName());    //getName it means user name in ur exp we use email
		
		Author author = authorService.findAuthorByEmail(auth.getName()); //checking through email from db
		
		if(author!=null) {
			modelAndView.addObject("userName", "Logged in as:" + author.getFname() + " " + author.getLname() ); //userName from thymeleaf
			//after geting email we are fetching author
			
			
			Collection<? extends GrantedAuthority> authorities = auth.getAuthorities(); //grantedauthority is used spring collection of roles
			 
			        List<String> roles = new ArrayList<String>();
			 
			        for (GrantedAuthority a : authorities) {
			            roles.add(a.getAuthority());
			        }
			        System.out.println(roles);    
			
			if(!author.isVerified()){ //this is for otp  
//			modelAndView.addObject("successMessage", "User has been registered successfully");
			
			modelAndView.setViewName("verifyOTP");
			return modelAndView;
				
			}else {
				if(roles.contains("USER")) {
					System.out.println("reached USER ROle");
					modelAndView.setViewName("redirect:/admin/author"); //this url mapping is done in ctrl
					return modelAndView;
				}else if(roles.contains("ADMIN")) {
					System.out.println("reached Admin ROle");
					modelAndView.setViewName("redirect:/officer");
					return modelAndView;
				}
			}
			
		}
	   
	  else { // if the user is not authenticated
			System.out.println("reached new author");
			modelAndView.addObject("userName", "" );
			modelAndView.addObject("successMessage", "");
			modelAndView.setViewName("login"); 
			return modelAndView;
		}
		
		
		return modelAndView;
	}
	
	
	@RequestMapping(value="/registration", method = RequestMethod.GET)
	public ModelAndView registration(){
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Author author = authorService.findAuthorByEmail(auth.getName());
		ModelAndView modelAndView = new ModelAndView();
		if(author==null) {
		
		Author author1 = new Author();
		modelAndView.addObject("user", author1);
		modelAndView.setViewName("registration");
		return modelAndView;
		}
		
		modelAndView.addObject("userName", "Logged in as:" + author.getFname() + " " + author.getLname() );
		modelAndView.setViewName("login");
		return modelAndView;
		
	}
	
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewAuthor(@Valid Author author, BindingResult bindingResult,HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		Author authorExits = authorService.findAuthorByEmail(author.getEmail());
		if (authorExits != null) {
			bindingResult
					.rejectValue("email", "error.user",
							"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		} else {
			
			author.setVerified(false);
			Random rnd = new Random();
			int otp = 100000 + rnd.nextInt(900000);
			author.setOtp(otp);
			authorService.saveAuthor(author);
			
			
//			Mail mail = new Mail();
//			mail.sendMail(author);
			
			modelAndView.addObject("successMessage", "OTP sent to your registered email, Login to verify email");
			
//			modelAndView.addObject("user", new User());
//			autoLogin(user,request );
			
			modelAndView.setViewName("login");
			
		}
		return modelAndView;
	}
	
	
	@RequestMapping(value = "/validateAuthor", method = RequestMethod.POST)
	public ModelAndView validateNewAuthor(@RequestParam("otp") int otp) {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Author author = authorService.findAuthorByEmail(auth.getName());
//		System.out.println("OTP:"+otp);
		modelAndView.setViewName("/");
		if (author != null) {
		 if(author.getOtp()==otp) {
			author.setVerified(true);
			Role userRole = roleRepository.findByRole("USER");
			author.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
			
	
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(auth.getAuthorities());
			authorities.add(new SimpleGrantedAuthority("USER"));
			Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(),auth.getCredentials(),authorities);
			SecurityContextHolder.getContext().setAuthentication(newAuth);
			
			authorService.updateAuthor(author);
						
			//check if some data is available pertaining to the student and return view accordingly.
			
			modelAndView.setViewName("redirect:/student");
			
			
		 }else {
			
			 modelAndView.addObject("successMessage", "Wrong OTP, Kindly verify.");
			 modelAndView.setViewName("verifyOTP");
				
		 }
		 
		}
		
		return modelAndView;
	}
	
	
	public void logout() {
	    HttpServletRequest request =
	        ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes())
	            .getRequest();
	    new SecurityContextLogoutHandler().logout(request, null, null);
	}
	
	@RequestMapping(value="/admin", method = RequestMethod.GET)
	public ModelAndView home(){
		ModelAndView modelAndView = new ModelAndView();
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		User user = userService.findUserByEmail(auth.getName());
//		modelAndView.addObject("userName", "Welcome Admin" + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
//		modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
		modelAndView.setViewName("result");
		return modelAndView;
	}
	
	
	
	
	@RequestMapping(value="/forgotPassword", method = RequestMethod.GET)
	public ModelAndView forgotPassword(){
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Author user = authorService.findAuthorByEmail(auth.getName());
		ModelAndView modelAndView = new ModelAndView();
		if(user==null) {
		
		Author user1 = new Author();
		modelAndView.addObject("user", user1);
		modelAndView.setViewName("forgotPassword");
		return modelAndView;
		}
		
		modelAndView.addObject("userName", "Logged in as:" + user.getFname() + " " + user.getLname() );
		modelAndView.setViewName("login");
		return modelAndView;
		
			
		
		
		
	}
	
	@RequestMapping(value="/forgotPassword", method = RequestMethod.POST)
	public ModelAndView forgotPasswordEmail(){
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Author user = authorService.findAuthorByEmail(auth.getName());
		ModelAndView modelAndView = new ModelAndView();
		if(user==null) {
		
		Author user1 = new Author();
		modelAndView.addObject("user", user1);
		modelAndView.setViewName("forgotPassword");
		return modelAndView;
		}
		
		modelAndView.addObject("userName", "Logged in as:" + user.getFname() + " " + user.getLname() );
		modelAndView.setViewName("login");
		return modelAndView;
				
	}
	
	
	 // redirect based on roles
	 
//	 protected String determineTargetUrl(Authentication authentication) {
//	        String url = "";
//	 
//	        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//	 
//	        List<String> roles = new ArrayList<String>();
//	 
//	        for (GrantedAuthority a : authorities) {
//	            roles.add(a.getAuthority());
//	        }
//	 
//	        if (isDba(roles)) {
//	            url = "/db";
//	        } else if (isAdmin(roles)) {
//	            url = "/admin";
//	        } else if (isUser(roles)) {
//	            url = "/home";
//	        } else {
//	            url = "/accessDenied";
//	        }
//	 
//	        return url;
//	    }
	
//	 Code for auto login
//	@Autowired
//    protected AuthenticationManager authenticationManager;
//	
//	private void autoLogin(User user, HttpServletRequest request) {
//        String username = user.getEmail();
//        String password = user.getPassword();
//        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
//
//        // generate session if one doesn't exist
//        request.getSession();
//
//        token.setDetails(new WebAuthenticationDetails(request));
//        Authentication authenticatedUser = authenticationManager.authenticate(token);
//
//        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
//        
//    }
//	
}