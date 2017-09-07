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

public class IngredientTests {
	
	Ingredient ingredients; 
	
	@Before
	public void setUp() {
		ingredients = new Ingredient();
	}

	@Test
	public void test_all_getters_and_setters() {
		new BeanTester().testBean(Ingredient.class);
	}
	
	@Test
	public void test_recipe_constructor() {
		Ingredient ingredients = new Ingredient("Tomatoes");
		
		assertThat(ingredients.getIngredientName()).isEqualTo("Tomatoes");
		
	}
}
