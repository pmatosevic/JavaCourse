package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

public class QueryParserTest {

	@Test
	void testDirect() {
		QueryParser qp1 = new QueryParser(" jmbag       =\"0123456789\"    ");
		assertTrue(qp1.isDirectQuery());
		assertEquals("0123456789", qp1.getQueriedJMBAG());
		assertEquals(1, qp1.getQuery().size());
		assertEquals("0123456789", qp1.getQuery().get(0).getStringLiteral());
	}
	
	@Test
	void testIndirect() {
		QueryParser qp2 = new QueryParser("jmbag<=\"0123456789\" and lastName<\"J\"");
		assertFalse(qp2.isDirectQuery());
		assertThrows(IllegalStateException.class, () -> qp2.getQueriedJMBAG());
		assertEquals(2, qp2.getQuery().size());
	}
	
	@Test
	void testIndirectAdvanced() {
		QueryParser qp = new QueryParser("jmbag!=\"0123456789\" and firstName LIKE \"I*an\" aNd lastName>\"J\"");
		assertFalse(qp.isDirectQuery());
		assertThrows(IllegalStateException.class, () -> qp.getQueriedJMBAG());
		assertEquals(3, qp.getQuery().size());
		List<ConditionalExpression> exps = qp.getQuery();
		
		assertEquals(FieldValueGetters.JMBAG, exps.get(0).getFieldGetter());
		assertEquals(FieldValueGetters.FIRST_NAME, exps.get(1).getFieldGetter());
		assertEquals(FieldValueGetters.LAST_NAME, exps.get(2).getFieldGetter());
		
		assertEquals(ComparisonOperators.NOT_EQUALS, exps.get(0).getComparisonOperator());
		assertEquals(ComparisonOperators.LIKE, exps.get(1).getComparisonOperator());
		assertEquals(ComparisonOperators.GREATER, exps.get(2).getComparisonOperator());
		
		assertEquals("0123456789", exps.get(0).getStringLiteral());
		assertEquals("I*an", exps.get(1).getStringLiteral());
		assertEquals("J", exps.get(2).getStringLiteral());
	}
	
	@Test
	void testInvalid1() {
		assertThrows(ParserException.class, () -> new QueryParser("\"0123456789\" = jmbag"));
	}
	
	@Test
	void testInvalidMultipleStars() {
		assertThrows(ParserException.class, () -> new QueryParser("jmbag LIKE \"00*00*1\""));
	}
	
	@Test
	void testInvalid2() {
		assertThrows(ParserException.class, () -> new QueryParser("jmbag=\"00000"));
	}
	
	@Test
	void testInvalid3() {
		assertThrows(ParserException.class, () -> new QueryParser("lastName>\"A\" AND "));
	}
	
	@Test
	void testInvalid4() {
		assertThrows(ParserException.class, () -> new QueryParser("lastName >     "));
	}
	
}
