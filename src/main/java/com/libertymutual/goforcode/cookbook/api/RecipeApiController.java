package com.libertymutual.goforcode.cookbook.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libertymutual.goforcode.cookbook.models.Recipe;
import com.libertymutual.goforcode.cookbook.services.RecipeRepository;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/recipes")
@Api(description = "Use this to create/get recipes")
public class RecipeApiController {
	
	private RecipeRepository recipeRepo;
	
	public RecipeApiController(RecipeRepository recipeRepo) {
		this.recipeRepo = recipeRepo;
	}
	
	
	@GetMapping("")
	public List<Recipe> getAll() {
		return recipeRepo.findAll();
	}

}
