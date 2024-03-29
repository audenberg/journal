package com.aud.demo.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private DataSource dataSource;
	
	@Value("${spring.queries.users-query}")
	private String usersQuery;
	
	@Value("${spring.queries.roles-query}")
	private String rolesQuery;

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.
			jdbcAuthentication()
				.usersByUsernameQuery(usersQuery)
				.authoritiesByUsernameQuery(rolesQuery)
				.dataSource(dataSource) // for database connection
				.passwordEncoder(bCryptPasswordEncoder);
				
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.
			authorizeRequests()
					.antMatchers("/").permitAll()
					.antMatchers("/login").permitAll()
					.antMatchers("/registration").permitAll()
					.antMatchers("/validate").permitAll()
					.antMatchers("/test").permitAll()
					.antMatchers("/validateUser").permitAll()
					.antMatchers("/forgotPassword").permitAll()
					.antMatchers("/author","/paper","/uploadFile","/fileupload","/reviewer").hasAnyAuthority(new String[] {"USER","ADMIN"})
					//.antMatchers("/reviewer").hasAuthority("ADMIN")
					.antMatchers("/admin").hasAuthority("ADMIN")
					.and().authorizeRequests().anyRequest()
	                .authenticated() //.
					.and()
					.formLogin()
						.loginPage("/login")
						.failureUrl("/login?error=true") // if user enter invalid credentials
						.defaultSuccessUrl("/").permitAll()
						.usernameParameter("email")
						.passwordParameter("password")
				.and()
					.logout()
					.deleteCookies("remove")
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // no need to mapping in ctrl this well be done through spring
					.logoutSuccessUrl("/")
					.and()
					.exceptionHandling()
				    .accessDeniedPage("/access-denied"); // still we have to make this page
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web
	       .ignoring() // we allow page to access these resources for these no need to authenticate
	       .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
	}

}
