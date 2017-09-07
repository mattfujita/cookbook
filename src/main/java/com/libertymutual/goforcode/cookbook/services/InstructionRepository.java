package com.libertymutual.goforcode.cookbook.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.libertymutual.goforcode.cookbook.models.Instruction;

@Repository
public interface InstructionRepository extends JpaRepository<Instruction, Long> {

}
