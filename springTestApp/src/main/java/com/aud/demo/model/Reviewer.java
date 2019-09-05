package com.aud.demo.model;


import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Reviewer extends User {
	
	String pancard;
	
	@ManyToMany
	@JoinTable(name = "reviewer_paper", joinColumns = @JoinColumn(name = "reviewer_id"), inverseJoinColumns = @JoinColumn(name = "paper_id"))
	List<Paper> papers;

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

	public List<Paper> getPapers() {
		return papers;
	}

	public void setPapers(List<Paper> papers) {
		this.papers = papers;
	}

	

	
	
	
	

}


