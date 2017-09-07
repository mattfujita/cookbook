package com.libertymutual.goforcode.cookbook.models;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")

@Entity
public class Recipe {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false)
	private String title;
	
	@Column(nullable=false, length= 255)
	private String description;
	
	@Column(nullable=false)
	private String minutes;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="recipes")
	private List<Ingredient> ingredients;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="recipe")
	private List<Instruction> instructions;
	 
	public Recipe() {}
	
	public Recipe(String title, String description, String minutes) {
		this.title = title;
		this.description = description;
		this.minutes = minutes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMinutes() {
		return minutes;
	}

	public void setMinutes(String minutes) {
		this.minutes = minutes;
	}
	
	public void addIngredient(Ingredient ingredient) {
		if(ingredients == null) {
			ingredients = new ArrayList<Ingredient>();
		}
		ingredients.add(ingredient);
	}
	
	public void addInstruction(Instruction instruction) {
		if(instructions == null) {
			instructions = new ArrayList<Instruction>();
		}
		instructions.add(instruction);
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public List<Instruction> getInstructions() {
		return instructions;
	}

	public void setInstructions(List<Instruction> instructions) {
		this.instructions = instructions;
	}
	
}
