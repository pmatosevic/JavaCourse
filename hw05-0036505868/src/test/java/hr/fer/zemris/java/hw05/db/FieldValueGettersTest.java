package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FieldValueGettersTest {

	StudentDatabase db;
	StudentRecord record;
	
	@Test
	void testFirstName() {
		assertEquals("Luka", FieldValueGetters.FIRST_NAME.get(record));
	}
	
	@Test
	void testLastName() {
		assertEquals("Dokleja", FieldValueGetters.LAST_NAME.get(record));
	}
	
	@Test
	void testJmbag() {
		assertEquals("0000000010", FieldValueGetters.JMBAG.get(record));
	}
	
	
	@BeforeEach
	void loadDb() throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("database.txt"), StandardCharsets.UTF_8);
		db = new StudentDatabase(lines);
		record = db.forJMBAG("0000000010");
	}
	
	
}
