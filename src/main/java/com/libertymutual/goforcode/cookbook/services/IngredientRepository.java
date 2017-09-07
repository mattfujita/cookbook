package com.libertymutual.goforcode.cookbook.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.libertymutual.goforcode.cookbook.models.Ingredient;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

}