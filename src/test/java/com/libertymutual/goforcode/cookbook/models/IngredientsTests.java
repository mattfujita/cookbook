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

public class IngredientsTests {
	
	Ingredients ingredients; 
	
	@Before
	public void setUp() {
		ingredients = new Ingredients();
	}

	@Test
	public void test_all_getters_and_setters() {
		new BeanTester().testBean(Ingredients.class);
	}
	
	@Test
	public void test_recipe_constructor() {
		Ingredients ingredients = new Ingredients("Tomatoes");
		
		assertThat(ingredients.getIngredientName()).isEqualTo("Tomatoes");
		
	}
}
