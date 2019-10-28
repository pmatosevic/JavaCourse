package hr.fer.zemris.lsystems.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;

public class LSystemBuilderImplTest {

	@Test
	void testGenerate0() {
		LSystemBuilder builder = new LSystemBuilderImpl()
				.setAxiom("F")
				.registerProduction('F', "F+F--F+F");
		LSystem system = builder.build();
		assertEquals("F", system.generate(0));
	}
	
	@Test
	void testGenerate1() {
		LSystemBuilder builder = new LSystemBuilderImpl()
				.setAxiom("F")
				.registerProduction('F', "F+F--F+F");
		LSystem system = builder.build();
		assertEquals("F+F--F+F", system.generate(1));
	}
	
	@Test
	void testGenerate2() {
		LSystemBuilder builder = new LSystemBuilderImpl()
				.setAxiom("F")
				.registerProduction('F', "F+F--F+F");
		LSystem system = builder.build();
		assertEquals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F", system.generate(2));
	}
	
}
