package com.libertymutual.goforcode.cookbook.models;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.meanbean.test.BeanTester;
import org.springframework.dao.EmptyResultDataAccessException;

import org.junit.Test;

public class RecipeTests {
	
	Recipe recipe;
	
	@Before
	public void setUp() {
		recipe = new Recipe();
	}

	@Test
	public void test_all_getters_and_setters() {
		new BeanTester().testBean(Recipe.class);
	}
	
	@Test
	public void test_recipe_constructor() {
		Recipe recipe = new Recipe("Spaghetti", "How to make some spaghetti", "30");
		
		assertThat(recipe.getTitle()).isEqualTo("Spaghetti");
		assertThat(recipe.getDescription()).isEqualTo("How to make some spaghetti");
		assertThat(recipe.getMinutes()).isEqualTo("30");
		
	}
	
	@Test
	public void test_addIngredient_works() {
		//Arrange
		ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
		Ingredient ingredient = new Ingredient();
		ingredients.add(ingredient);
		
		//Act
		recipe.addIngredient(ingredient);
		
		//Assert
		assertThat(ingredients.size()).isEqualTo(1);
		assertThat(ingredients.get(0)).isSameAs(ingredient);
	}
	
	@Test
	public void test_addInstruction_works() {
		//Arrange
		ArrayList<Instruction> instructions = new ArrayList<Instruction>();
		Instruction instruction = new Instruction();
		instructions.add(instruction);
		
		//Act
		recipe.addInstruction(instruction);
			
		//Assert
		assertThat(instructions.size()).isEqualTo(1);
		assertThat(instructions.get(0)).isSameAs(instruction);
	}

}
