package com.libertymutual.goforcode.cookbook.services;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.libertymutual.goforcode.cookbook.models.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

	List<Recipe> findByTitleContaining(String title);
}
