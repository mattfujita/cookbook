package com.libertymutual.goforcode.cookbook.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libertymutual.goforcode.cookbook.models.Ingredients;
import com.libertymutual.goforcode.cookbook.models.Instructions;
import com.libertymutual.goforcode.cookbook.models.Recipe;
import com.libertymutual.goforcode.cookbook.services.RecipeRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/recipes")
@Api(description = "Use this to create/get recipes")
public class RecipeApiController {
	
	private RecipeRepository recipeRepo;
	
	public RecipeApiController(RecipeRepository recipeRepo) {
		this.recipeRepo = recipeRepo;
	}
	
	
	@ApiOperation(value="Get a list of all of the recipes")	
	@GetMapping("")
	public List<Recipe> getAll() {
		return recipeRepo.findAll();
	}
	
	@ApiOperation(value="Get the details of a single recipe by its id")	
	@GetMapping("{id}") // naming {id} the same as pathvariable connects them
	public Recipe getOne(@PathVariable long id) throws StuffNotFoundException {
		Recipe recipe = recipeRepo.findOne(id);
		if (recipe == null) {
			throw new StuffNotFoundException();
		}
		return recipe;
	}
	
	@ApiOperation(value="Create a new recipe. Provide a the title, description, "
						+ "and # of minutes at minimum. Do not provide an id.")	
	@PostMapping("")
	public Recipe create(@RequestBody Recipe recipe) {
		return recipeRepo.save(recipe);
	}

	@ApiOperation(value="Delete the recipe with the given id")	
	@DeleteMapping("{id}")
	public Recipe delete(@PathVariable long id) {
		try {
			Recipe recipe = recipeRepo.findOne(id);
			recipeRepo.delete(id);
			return recipe;
		} catch (EmptyResultDataAccessException erdae) {
			System.err.println("Could not find id to delete.");
			return null;
		}
	}
	
	@ApiOperation(value="Update the title, description, and # of minutes for a recipe")
	@PutMapping("{id}")
	public Recipe update(@RequestBody Recipe recipe, @PathVariable long id){
		recipe.setId(id);
		return recipeRepo.save(recipe);
	}
	
	@PostMapping("{id}/ingredients")
	@ApiOperation(value="Create new ingredient for recipe", notes = "This will add a new ingredient to the specified recipe.")
	public Recipe associateAnIngredient(@RequestBody Ingredients ingredient, @PathVariable long id) {
		Recipe recipe = recipeRepo.findOne(id);
		recipe= recipeRepo.findOne(recipe.getId());
		
		recipe.addIngredient(ingredient);
		recipeRepo.save(recipe);

		return recipe;
	}
	
	@PostMapping("{id}/instructions")
	@ApiOperation(value="Create new instruction for recipe", notes = "This will add a new instruction to the specified recipe.")
	public Recipe associateAnIngredient(@RequestBody Instructions instruction, @PathVariable long id) {
		Recipe recipe = recipeRepo.findOne(id);
		recipe= recipeRepo.findOne(recipe.getId());
		
		recipe.addInstruction(instruction);
		recipeRepo.save(recipe);

		return recipe;
	}

}
