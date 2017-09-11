package com.libertymutual.goforcode.cookbook.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")

@Entity
public class Ingredient {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false)
	private String ingredientName;
	
	private String measureUnit;
	
	private String ingredientQuantity;
	
	@ManyToOne
	private Recipe recipes;
	
	public Ingredient() {}
	
	public Ingredient(String ingredientName) {
		this.ingredientName = ingredientName;
	}

	public String getIngredientName() {
		return ingredientName; 
	}

	public void setIngredientName(String ingredientName) {
		this.ingredientName = ingredientName;
	}

	public String getMeasureUnit() {
		return measureUnit;
	}

	public void setMeasureUnit(String measureUnit) {
		this.measureUnit = measureUnit;
	}

	public String getIngredientQuantity() {
		return ingredientQuantity;
	}

	public void setIngredientQuantity(String ingredientQuantity) {
		this.ingredientQuantity = ingredientQuantity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Recipe getRecipes() {
		return recipes;
	}

	public void setRecipes(Recipe recipes) {
		this.recipes = recipes;
	}

}
