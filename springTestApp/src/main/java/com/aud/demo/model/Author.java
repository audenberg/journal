package com.aud.demo.model;


import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Entity
public class Author extends User {
	
	@OneToMany(mappedBy = "author")
    List<Paper> papers;
	
	
	public Author() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public Author(Long id, @NotEmpty(message = "*Please provide First Name") String fname, String lname, String email,
			@Pattern(regexp = "^([1-9])\\d{9}", message = "*Please provide a valid contact number") String mobile,
			String password, String line1, String line2, String city, String state, String country, String pincode,
			int active, int otp, boolean verified, Set<Role> roles) {
		super(id, fname, lname, email, mobile, password, line1, line2, city, state, country, pincode, active, otp, verified,
				roles);
		// TODO Auto-generated constructor stub
	}



	public Author(Long id, @NotEmpty(message = "*Please provide First Name") String fname, String lname, String email,
			@Pattern(regexp = "^([1-9])\\d{9}", message = "*Please provide a valid contact number") String mobile,
			String password, String line1, String line2, String city, String state, String country, String pincode,
			int active, int otp, boolean verified) {
		super(id, fname, lname, email, mobile, password, line1, line2, city, state, country, pincode, active, otp, verified);
		// TODO Auto-generated constructor stub
	}



	public Author(@NotEmpty(message = "*Please provide First Name") String fname, String lname, String email,
			@Pattern(regexp = "^([1-9])\\d{9}", message = "*Please provide a valid contact number") String mobile,
			String password, String line1, String line2, String city, String state, String country, String pincode,
			int active, int otp, boolean verified, Set<Role> roles) {
		super(fname, lname, email, mobile, password, line1, line2, city, state, country, pincode, active, otp, verified, roles);
		// TODO Auto-generated constructor stub
	}



	@Override
	public String toString() {
		return "Author [id=" + id + ", fname=" + fname + ", lname=" + lname + ", email=" + email + ", mobile=" + mobile + ""
				+ ", line1=" + line1 + ", line2=" + line2 + ", city=" + city + ", state=" + state
				+ ", country=" + country + ", pincode=" + pincode + "]";
	}



	public List<Paper> getPapers() {
		return papers;
	}



	public void setPapers(List<Paper> papers) {
		this.papers = papers;
	}
	
	

}


