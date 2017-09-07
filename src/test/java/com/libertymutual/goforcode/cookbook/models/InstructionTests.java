package com.libertymutual.goforcode.cookbook.models;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.meanbean.test.BeanTester;

public class InstructionTests {
	
	Instruction instruction;

	@Before
	public void setUp() {
		instruction = new Instruction();
	}

	@Test
	public void test_all_getters_and_setters() {
		new BeanTester().testBean(Instruction.class);
	}
	
	@Test
	public void test_instruction_constructor() {
		Instruction instruction = new Instruction(1, "some instruction");
		
		assertThat(instruction.getStepNumber()).isEqualTo(1);
		assertThat(instruction.getInstructionText()).isEqualTo("some instruction");

		
	}

}
