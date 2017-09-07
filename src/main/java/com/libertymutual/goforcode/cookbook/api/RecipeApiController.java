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

import com.libertymutual.goforcode.cookbook.models.Ingredient;
import com.libertymutual.goforcode.cookbook.models.Instruction;
import com.libertymutual.goforcode.cookbook.models.Recipe;
import com.libertymutual.goforcode.cookbook.services.IngredientRepository;
import com.libertymutual.goforcode.cookbook.services.InstructionRepository;
import com.libertymutual.goforcode.cookbook.services.RecipeRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/recipes")
@Api(description = "Use this to create/get recipes")
public class RecipeApiController {
	
	private RecipeRepository recipeRepo;
	private IngredientRepository ingredientsRepo;
	private InstructionRepository instructionRepo;
	
	public RecipeApiController(RecipeRepository recipeRepo, IngredientRepository ingredientsRepo, InstructionRepository instructionRepo) {
		this.recipeRepo = recipeRepo;
		this.ingredientsRepo = ingredientsRepo;
		this.instructionRepo = instructionRepo;
		
	}
	
	
	@ApiOperation(value="Get a list of all of the recipes or search by title")	
	@GetMapping("")
	public List<Recipe> getAll(String title) {
		List<Recipe> returnList;
		if (title != null) {
			returnList = recipeRepo.findByTitleContaining(title);
		} else {
		returnList = recipeRepo.findAll();
		}
		return returnList;
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
	public Recipe associateAnIngredient(@RequestBody Ingredient ingredient, @PathVariable long id) {
		Recipe recipe = recipeRepo.findOne(id);
		ingredient.setRecipes(recipe);
		ingredient = ingredientsRepo.save(ingredient);
		
		recipe.addIngredient(ingredient);

		return recipe;
	}
	
	@PostMapping("{id}/instructions")
	@ApiOperation(value="Create new instruction for recipe", notes = "This will add a new instruction to the specified recipe.")
	public Recipe associateAnInstruction(@RequestBody Instruction instruction, @PathVariable long id) {
		Recipe recipe = recipeRepo.findOne(id);
		instruction.setRecipe(recipe);
		instruction = instructionRepo.save(instruction);
		
		recipe.addInstruction(instruction);

		return recipe;
	}
	
	@DeleteMapping("{id}/ingredients/{ing_id}")
	@ApiOperation(value="Delete ingredient from recipe", notes = "This will delete a new instruction to the specified recipe.")
	public Recipe deleteIngredient(@PathVariable long id, @PathVariable long ing_id) {
		try {
			Recipe recipe = recipeRepo.findOne(id);
			ingredientsRepo.delete(ing_id);
			return recipe;
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		}
		
	}
	
	@DeleteMapping("{id}/instructions/{ins_id}")
	public Recipe deleteInstruction(@PathVariable long id, @PathVariable long ins_id) {
		try {
			Recipe recipe = recipeRepo.findOne(id);
			instructionRepo.delete(ins_id);		
			return recipe;
			
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		}
	}

}
