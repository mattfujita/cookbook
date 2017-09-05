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
		Recipe recipe = new Recipe("Spaghetti", "How to make some spaghetti", 30);
		
		assertThat(recipe.getTitle()).isEqualTo("Spaghetti");
		assertThat(recipe.getDescription()).isEqualTo("How to make some spaghetti");
		assertThat(recipe.getMinutes()).isEqualTo(30);
		
	}

}
