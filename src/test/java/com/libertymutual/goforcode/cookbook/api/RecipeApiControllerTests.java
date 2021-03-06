package com.libertymutual.goforcode.cookbook.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;

import com.libertymutual.goforcode.cookbook.models.Ingredient;
import com.libertymutual.goforcode.cookbook.models.Instruction;
import com.libertymutual.goforcode.cookbook.models.Recipe;
import com.libertymutual.goforcode.cookbook.services.IngredientRepository;
import com.libertymutual.goforcode.cookbook.services.InstructionRepository;
import com.libertymutual.goforcode.cookbook.services.RecipeRepository;

public class RecipeApiControllerTests {
	private RecipeRepository recipeRepo;
	private IngredientRepository ingredientsRepo;
	private InstructionRepository instructionsRepo;
	private RecipeApiController recipeController;

	

	@Before
	public void setUp() {
		//use mock to tell it what you want to happen; verify down below
		recipeRepo = mock(RecipeRepository.class);
		ingredientsRepo = mock(IngredientRepository.class);
		instructionsRepo = mock(InstructionRepository.class);
		recipeController = new RecipeApiController(recipeRepo, ingredientsRepo, instructionsRepo);
	}
	
	@Test
	public void test_getAll_returns_all_Recipe_returned_by_the_repo() {
		// Arrange
		ArrayList<Recipe> recipes = new ArrayList<Recipe>();
		recipes.add(new Recipe());
		recipes.add(new Recipe());
		when(recipeRepo.findAll()).thenReturn(recipes);
	
		// Act
		List<Recipe> actual = recipeController.getAll(null);
		
		// Assert
		assertThat(actual.size()).isEqualTo(2);
		assertThat(actual.get(0)).isSameAs(recipes.get(0));
		verify(recipeRepo).findAll(); //verify that mock is the mock you've created with mockito
									//and method findAll() has been called;	
	}
	
	@Test
	public void test_getAll_creates_new_returnList_when_title_of_recipe_is_passed() {
		List<Recipe> recipes = new ArrayList<Recipe>();
		recipes.add(new Recipe("something", "good", "30"));
		when(recipeRepo.findByTitleContainingIgnoreCase("something")).thenReturn(recipes);
		
		List<Recipe> result = recipeController.getAll("something");
		
		assertThat(result).isSameAs(recipes);
		
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
			recipeController.getOne(1L);
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
	public void test_update_sets_recipe_id_and_saves_to_recipe_Repo() {
		//Arrange
		Recipe recipe = new Recipe();
		when(recipeRepo.save(recipe)).thenReturn(recipe);
		
		//Act
		Recipe actualRecipe = recipeController.update(recipe, 3L);
		
		//Assert
		assertThat(actualRecipe.getId()).isSameAs(recipe.getId()); //test that id was updated	
		verify(recipeRepo).save(recipe); //verify that method got called with this exact argument		
	}	
	
	@Test
	public void test_that_you_can_add_ingredient_to_recipe() {
		Recipe recipe = new Recipe();
		Ingredient ingredient = new Ingredient();
		ingredient.setRecipes(recipe);
		when(recipeRepo.findOne(12L)).thenReturn(recipe);
		
		Recipe result = recipeController.associateAnIngredient(ingredient, 12L);
		
		assertThat(result).isSameAs(recipe);
		verify(recipeRepo).findOne(12L);
		verify(ingredientsRepo).save(ingredient);
		
	}
	
	@Test
	public void test_that_you_can_add_instruction_to_recipe() {
		Recipe recipe = new Recipe();
		Instruction instruction = new Instruction();
		instruction.setRecipe(recipe);
		when(recipeRepo.findOne(13L)).thenReturn(recipe);
		
		Recipe result = recipeController.associateAnInstruction(instruction, 13L);
		
		assertThat(result).isSameAs(recipe);
		verify(recipeRepo).findOne(13L);
		verify(instructionsRepo).save(instruction);
		
	}
	
	@Test
	public void test_deletion_of_ingredient_from_recipe() {
		Recipe recipe = new Recipe();
		Ingredient ingredient = new Ingredient();
		when(recipeRepo.findOne(9L)).thenReturn(recipe);
		when(ingredientsRepo.findOne(5L)).thenReturn(ingredient);
		
		Recipe result = recipeController.deleteIngredient(9L, 5L);
		
		assertThat(result).isSameAs(recipe);
		verify(recipeRepo).findOne(9L);
		verify(ingredientsRepo).delete(5L);
		
	}
	
	
	@Test
	public void test_delete_EmptyResultDataAccessException_when_no_ingredient_found( ) {
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		doThrow(new EmptyResultDataAccessException(0)).when(ingredientsRepo).delete(3L);
		
		Recipe result = recipeController.deleteIngredient(1L, 3L);
		
		assertThat(result).isNull();
		verify(recipeRepo).findOne(1L);
	}
	
	@Test
	public void test_deletion_of_instruction_from_recipe() {
		Recipe recipe = new Recipe();
		Instruction instruction = new Instruction();
		when(recipeRepo.findOne(6L)).thenReturn(recipe);
		when(instructionsRepo.findOne(7L)).thenReturn(instruction);
		
		Recipe result = recipeController.deleteInstruction(6L, 7L);
		
		assertThat(result).isSameAs(recipe);
		verify(recipeRepo).findOne(6L);
		verify(instructionsRepo).delete(7L);
	}
	
	
	@Test
	public void test_delete_EmptyResultDataAccessException_when_no_instruction_found( ) {
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		doThrow(new EmptyResultDataAccessException(0)).when(instructionsRepo).delete(3L);
		
		Recipe result = recipeController.deleteInstruction(1L, 3L);
		
		assertThat(result).isNull();
		verify(recipeRepo).findOne(1L);
	}
	
	@Test
	public void test_updating_ingredient_on_recipe() {
		Recipe recipe = new Recipe();
		Ingredient ingredient = new Ingredient();
		ingredient.setId(3L);
		ingredient.setRecipes(recipe);
		when(recipeRepo.findOne(2L)).thenReturn(recipe);
		when(ingredientsRepo.save(ingredient)).thenReturn(ingredient);
		
		Recipe result = recipeController.updateIngredient(2L, ingredient, 3L);
		
		assertThat(result).isSameAs(recipe);
		verify(recipeRepo).findOne(2L);
		verify(ingredientsRepo).save(ingredient);
		
	}
	
	@Test
	public void test_updating_instruction_on_recipe() {
		Recipe recipe = new Recipe();
		Instruction instruction = new Instruction();
		instruction.setId(8L);
		instruction.setRecipe(recipe);
		when(recipeRepo.findOne(2L)).thenReturn(recipe);
		when(instructionsRepo.save(instruction)).thenReturn(instruction);
		
		Recipe result = recipeController.updateInstruction(2L, 8L, instruction);
		
		assertThat(result).isSameAs(recipe);
		verify(recipeRepo).findOne(2L);
		verify(instructionsRepo).save(instruction);
		
	}
}
	

