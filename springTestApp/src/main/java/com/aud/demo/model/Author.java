package com.aud.demo.model;

import javax.persistence.Entity;

@Entity
public class Author extends Person {

	
	
	public Author() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Author(Long id, String fname, String lname, String email, String password, String line1, String line2,
			String city, String state, String country, String pincode) {
		super(id, fname, lname, email, password, line1, line2, city, state, country, pincode);
		// TODO Auto-generated constructor stub
	}

	public Author(String fname, String lname, String email, String mobile, String password, String line1, String line2,
			String city, String state, String country, String pincode) {
		super(fname, lname, email, mobile, password, line1, line2, city, state, country, pincode);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Author [id=" + id + ", fname=" + fname + ", lname=" + lname + ", email=" + email + ", mobile=" + mobile + ""
				+ ", line1=" + line1 + ", line2=" + line2 + ", city=" + city + ", state=" + state
				+ ", country=" + country + ", pincode=" + pincode + "]";
	}
	
	

}


