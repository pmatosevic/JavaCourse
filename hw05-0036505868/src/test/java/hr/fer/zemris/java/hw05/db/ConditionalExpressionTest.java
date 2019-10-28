package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConditionalExpressionTest {

	ConditionalExpression expr;
	
	@Test
	void testGetFieldGetter() {
		assertEquals(FieldValueGetters.FIRST_NAME, expr.getFieldGetter());
	}
	
	@Test
	void testGetStringLiteral() {
		assertEquals("test_string", expr.getStringLiteral());
	}
	
	@Test
	void testGetComparisonOperator() {
		assertEquals(ComparisonOperators.EQUALS, expr.getComparisonOperator());
	}
	
	@BeforeEach
	void create() {
		expr = new ConditionalExpression(
				FieldValueGetters.FIRST_NAME, "test_string", ComparisonOperators.EQUALS);
	}
	
}
