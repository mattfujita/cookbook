package com.libertymutual.goforcode.cookbook.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.libertymutual.goforcode.cookbook.models.Instructions;

@Repository
public interface InstructionsRepository extends JpaRepository<Instructions, Long> {

}
