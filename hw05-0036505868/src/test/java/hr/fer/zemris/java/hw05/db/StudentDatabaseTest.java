package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StudentDatabaseTest {

	
	StudentDatabase db;
	
	@Test
	void testForJMBAG() throws IOException {
		assertEquals("0000000015", db.forJMBAG("0000000015").getJmbag());
		assertEquals("0000000020", db.forJMBAG("0000000020").getJmbag());
		assertEquals(null, db.forJMBAG("0000000100"));
	}
	
	@Test
	void testFilterAllTrue() {
		IFilter filter = r -> true;
		List<StudentRecord> records = db.filter(filter);
		assertEquals(63, records.size());
	}
	
	@Test
	void testFilterAllFalse() {
		IFilter filter = r -> false;
		List<StudentRecord> records = db.filter(filter);
		assertEquals(0, records.size());
	}
	
	@BeforeEach
	void loadDb() throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("database.txt"), StandardCharsets.UTF_8);
		db = new StudentDatabase(lines);
	}
	
}
