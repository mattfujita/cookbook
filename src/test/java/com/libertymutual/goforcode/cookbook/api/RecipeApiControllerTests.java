package com.libertymutual.goforcode.cookbook.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;

import com.libertymutual.goforcode.cookbook.models.Recipe;
import com.libertymutual.goforcode.cookbook.services.IngredientsRepo;
import com.libertymutual.goforcode.cookbook.services.RecipeRepository;

public class RecipeApiControllerTests {
	private RecipeRepository recipeRepo;
	private IngredientsRepo ingredientsRepo;
	private RecipeApiController recipeController;

	

	@Before
	public void setUp() {
		//use mock to tell it what you want to happen; verify down below
		recipeRepo = mock(RecipeRepository.class);
		recipeController = new RecipeApiController(recipeRepo, ingredientsRepo);
	}
	
	@Test
	public void test_getAll_returns_all_Recipe_returned_by_the_repo() {
		// Arrange
		ArrayList<Recipe> recipes = new ArrayList<Recipe>();
		recipes.add(new Recipe());
		recipes.add(new Recipe());
		when(recipeRepo.findAll()).thenReturn(recipes);
	
		// Act
		List<Recipe> actual = recipeController.getAll();
		
		// Assert
		assertThat(actual.size()).isEqualTo(2);
		assertThat(actual.get(0)).isSameAs(recipes.get(0));
		verify(recipeRepo).findAll(); //verify that mock is the mock you've created with mockito
									//and method findAll() has been called;	
	}
	
	@Test
	public void test_getOne_returns_one_Recipe_returned_from_repo() throws StuffNotFoundException {
		// Arrange
		Recipe mattsfavoriteCupcakes = new Recipe();
		when(recipeRepo.findOne(22L)).thenReturn(mattsfavoriteCupcakes);
		
		// Act
		Recipe actual = recipeController.getOne(22L);
		
		// Assert
		assertThat(actual).isSameAs(mattsfavoriteCupcakes);
		verify(recipeRepo).findOne(22L);
	}
	
	@Test
	//expectation for this test is an exception is thrown; we need to catch one
	public void test_getOne_throws_StuffNotFound_when_no_Recipe_returned_from_repo() {
		try {
			recipeController.getOne(1);
			//asserting that this failed
			fail("The recipeController did not throw the StuffNotFoundException");	
		} catch(StuffNotFoundException snfe) {}
	}
	
	@Test
	public void test_deleteOne_returns_recipe_deleted_when_found() {
		//Arrange
		Recipe recipe = new Recipe();
		when(recipeRepo.findOne(3L)).thenReturn(recipe);
		
		//Act
		Recipe actual = recipeController.delete(3L);
		
		//Assert
		assertThat(actual).isSameAs(recipe);
		verify(recipeRepo).delete(3L);
		verify(recipeRepo).findOne(3L);	
	}	
	
	@Test
	public void test_that_null_is_returned_when_findOne_throwsEmptyResultDataAccess() throws StuffNotFoundException {
		//Arrange
		doThrow(new EmptyResultDataAccessException(0)).when(recipeRepo).delete(8L);
		
		//Act
		Recipe actual = recipeController.delete(8L);
		
		//Assert
		assertThat(actual).isNull();
		verify(recipeRepo).delete(8L);
	}
	
	@Test
	public void test_that_creates_a_new_Recipe_and_saves_it_to_RecipeRepo() {
		//Arrange
		Recipe recipe = new Recipe();
		when(recipeRepo.save(recipe)).thenReturn(recipe);
		
		//Act - the actual method you are testing
		Recipe actualRecipe = recipeController.create(recipe);
		
		//Assert
		assertThat(recipe).isSameAs(actualRecipe);
		verify(recipeRepo).save(recipe); //verify that method got called with this exact argument		
	}
	
	@Test
	public void test_update_sets_movie_id_and_saves_to_Movie_Repo() {
		//Arrange
		Recipe recipe = new Recipe();
		when(recipeRepo.save(recipe)).thenReturn(recipe);
		
		//Act
		Recipe actualRecipe = recipeController.update(recipe, 3L);
		
		//Assert
		assertThat(actualRecipe.getId()).isSameAs(recipe.getId()); //test that id was updated	
		verify(recipeRepo).save(recipe); //verify that method got called with this exact argument		
	}	
}
	

