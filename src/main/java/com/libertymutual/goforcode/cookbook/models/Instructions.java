package com.libertymutual.goforcode.cookbook.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Instructions {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false)
	private int stepNumber;
	
	@Column(nullable=false, length = 255)
	private String instructionText;
	
	@ManyToOne
	Recipe recipe;
	
	public Instructions() {}
	
	public Instructions(int stepNumber, String instructionText) {
		this.stepNumber = stepNumber;
		this.instructionText = instructionText;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getStepNumber() {
		return stepNumber;
	}

	public void setStepNumber(int stepNumber) {
		this.stepNumber = stepNumber;
	}

	public String getInstructionText() {
		return instructionText;
	}

	public void setInstructionText(String instructionText) {
		this.instructionText = instructionText;
	}

}
