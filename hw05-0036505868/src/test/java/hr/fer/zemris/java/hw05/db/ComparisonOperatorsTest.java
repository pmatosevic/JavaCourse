package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ComparisonOperatorsTest {

	
	@Test
	void testLikeSimple() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertFalse(oper.satisfied("Zagreb", "Aba*"));
		assertTrue(oper.satisfied("", "*"));
		assertTrue(oper.satisfied("", ""));
		assertFalse(oper.satisfied("AAA", "AA*AA"));
		assertTrue(oper.satisfied("AAAA", "AA*AA"));
		assertTrue(oper.satisfied("ABCD", "ABCD"));
		
	}
	
	@Test
	void testLikeAdvanced() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertTrue(oper.satisfied("abcdxxefgh", "abcd*efgh"));
		assertTrue(oper.satisfied("abcdefgh", "abcd*efgh"));
		assertTrue(oper.satisfied("abcdefg", "*defg"));
		assertTrue(oper.satisfied("abcdefg", "abcd*"));
		assertFalse(oper.satisfied("abacac", "abc*c"));
		assertFalse(oper.satisfied("abacac", "*caca"));
	}
	
	@Test
	void testLess() {
		IComparisonOperator oper = ComparisonOperators.LESS;
		assertTrue(oper.satisfied("Ivan", "Zaaaaa"));
		assertFalse(oper.satisfied("Ivan", "Anaaaaaa"));
		assertFalse(oper.satisfied("Ivan", "A"));
		assertFalse(oper.satisfied("Ivan", "Ivan"));
		assertFalse(oper.satisfied("Ivan", "I"));
	}
	
	@Test
	void testLessOrEqual() {
		IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;
		assertTrue(oper.satisfied("Ivan", "Zaaaaa"));
		assertFalse(oper.satisfied("Ivan", "Anaaaaaa"));
		assertFalse(oper.satisfied("Ivan", "A"));
		assertTrue(oper.satisfied("Ivan", "Ivan"));
		assertFalse(oper.satisfied("Ivan", "I"));
	}
	
	@Test
	void testGreater() {
		IComparisonOperator oper = ComparisonOperators.GREATER;
		assertFalse(oper.satisfied("Ivan", "Zaaaaa"));
		assertTrue(oper.satisfied("Ivan", "Anaaaaaa"));
		assertTrue(oper.satisfied("Ivan", "A"));
		assertFalse(oper.satisfied("Ivan", "Ivan"));
		assertTrue(oper.satisfied("Ivan", "I"));
	}
	
	@Test
	void testGreaterOrEqual() {
		IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
		assertFalse(oper.satisfied("Ivan", "Zaaaaa"));
		assertTrue(oper.satisfied("Ivan", "Anaaaaaa"));
		assertTrue(oper.satisfied("Ivan", "A"));
		assertTrue(oper.satisfied("Ivan", "Ivan"));
		assertTrue(oper.satisfied("Ivan", "I"));
	}
	
	@Test
	void testEquals() {
		IComparisonOperator oper = ComparisonOperators.EQUALS;
		assertFalse(oper.satisfied("Ivan", "Zaaaaa"));
		assertFalse(oper.satisfied("Ivan", "Anaaaaaa"));
		assertFalse(oper.satisfied("Ivan", "A"));
		assertTrue(oper.satisfied("Ivan", "Ivan"));
		assertFalse(oper.satisfied("Ivan", "I"));
	}
	
	@Test
	void testNotEquals() {
		IComparisonOperator oper = ComparisonOperators.NOT_EQUALS;
		assertTrue(oper.satisfied("Ivan", "Zaaaaa"));
		assertTrue(oper.satisfied("Ivan", "Anaaaaaa"));
		assertTrue(oper.satisfied("Ivan", "A"));
		assertFalse(oper.satisfied("Ivan", "Ivan"));
		assertTrue(oper.satisfied("Ivan", "I"));
	}
	
}
