package com.libertymutual.goforcode.cookbook.models;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.meanbean.test.BeanTester;

public class InstructionsTests {
	
	Instructions instruction;

	@Before
	public void setUp() {
		instruction = new Instructions();
	}

	@Test
	public void test_all_getters_and_setters() {
		new BeanTester().testBean(Instructions.class);
	}
	
	@Test
	public void test_instruction_constructor() {
		Instructions instruction = new Instructions(1, "some instruction");
		
		assertThat(instruction.getStepNumber()).isEqualTo(1);
		assertThat(instruction.getInstructionText()).isEqualTo("some instruction");

		
	}

}
