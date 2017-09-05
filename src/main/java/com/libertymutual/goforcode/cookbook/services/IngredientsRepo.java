package com.libertymutual.goforcode.cookbook.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.libertymutual.goforcode.cookbook.models.Ingredients;

@Repository
public interface IngredientsRepo extends JpaRepository<Ingredients, Long> {

}