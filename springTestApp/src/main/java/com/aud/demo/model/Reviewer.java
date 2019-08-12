package com.aud.demo.model;

import javax.persistence.Entity;

@Entity
public class Reviewer extends Person {
	
	String pancard;

	public String getPancard() {
		return pancard;
	}

	public void setPancard(String pancard) {
		this.pancard = pancard;
	}

	@Override
	public String toString() {
		return "Reviewer [pancard=" + pancard + ", id=" + id + ", fname=" + fname + ", lname=" + lname + ", email="
				+ email + ", mobile=" + mobile + ", line1=" + line1 + ", line2=" + line2
				+ ", city=" + city + ", state=" + state + ", country=" + country + ", pincode=" + pincode + "]";
	}

	
	
	
	

}


