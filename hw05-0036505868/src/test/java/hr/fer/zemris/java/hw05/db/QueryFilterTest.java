package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class QueryFilterTest {

	@Test
	void testBasic() {
		List<ConditionalExpression> exprs = new ArrayList<>();
		exprs.add(new ConditionalExpression(FieldValueGetters.JMBAG, "12345", ComparisonOperators.EQUALS));
		QueryFilter f = new QueryFilter(exprs);
		assertTrue(f.accepts(new StudentRecord("12345", "test", "test", 5)));
		assertFalse(f.accepts(new StudentRecord("000000", "test", "test", 5)));
	}
	
	@Test
	void testMultipleRules() {
		List<ConditionalExpression> exprs = new ArrayList<>();
		exprs.add(new ConditionalExpression(FieldValueGetters.JMBAG, "12345", ComparisonOperators.EQUALS));
		exprs.add(new ConditionalExpression(FieldValueGetters.LAST_NAME, "ime", ComparisonOperators.EQUALS));
		exprs.add(new ConditionalExpression(FieldValueGetters.FIRST_NAME, "G", ComparisonOperators.GREATER));
		QueryFilter f = new QueryFilter(exprs);
		assertFalse(f.accepts(new StudentRecord("12345", "test", "test", 5)));
		assertFalse(f.accepts(new StudentRecord("000000", "test", "test", 5)));
		assertFalse(f.accepts(new StudentRecord("12345", "ime", "Aaaaa", 5)));
		assertTrue(f.accepts(new StudentRecord("12345", "ime", "Zzzz", 5)));
	}
	
}
