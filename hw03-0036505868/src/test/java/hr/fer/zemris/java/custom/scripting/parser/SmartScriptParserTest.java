package hr.fer.zemris.java.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

public class SmartScriptParserTest {

	@Test
	void testConstructionNull() {
		assertThrows(NullPointerException.class, () -> new SmartScriptParser(null));
	}
	
	@Test
	void testParserExampleDocument() {
		String text = loader("doc1.txt");
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode parsed = parser.getDocumentNode();
		
		DocumentNode expected = new DocumentNode();
		expected.addChildNode(new TextNode("This is sample text.\r\n"));
		
		ForLoopNode forNode1 = new ForLoopNode(new ElementVariable("i"), 
				new ElementConstantInteger(1), new ElementConstantInteger(10), new ElementConstantInteger(1));
		forNode1.addChildNode(new TextNode("\r\nThis is "));
		forNode1.addChildNode(new EchoNode(new Element[] {new ElementVariable("i")}));
		forNode1.addChildNode(new TextNode("-th time this message is generated.\r\n"));
		
		expected.addChildNode(forNode1);
		expected.addChildNode(new TextNode("\r\n"));
		
		ForLoopNode forNode2 = new ForLoopNode(new ElementVariable("i"), 
				new ElementConstantInteger(0), new ElementConstantInteger(10), new ElementConstantInteger(2));
		forNode2.addChildNode(new TextNode("\r\nsin("));
		forNode2.addChildNode(new EchoNode(new Element[] {new ElementVariable("i")}));
		forNode2.addChildNode(new TextNode("^2) = "));
		forNode2.addChildNode(new EchoNode(new Element[] {new ElementVariable("i"), 
				new ElementVariable("i"), new ElementOperator("*"), new ElementFunction("sin"), 
				new ElementString("0.000"), new ElementFunction("decfmt")}));
		forNode2.addChildNode(new TextNode("\r\n"));
		
		expected.addChildNode(forNode2);
		
		assertEquals(expected, parsed);
		
		checkReParsingForText(text);
	}
	
	
	
	@Test
	void testParserPlainTextEscaping() {
		String text = loader("doc2.txt");
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode parsed = parser.getDocumentNode();
		
		DocumentNode expected = new DocumentNode();
		expected.addChildNode(new TextNode("text text \\ {$ text "));
		expected.addChildNode(new EchoNode(new Element[] {new ElementVariable("i")}));
		expected.addChildNode(new TextNode(" end"));
		
		assertEquals(expected, parsed);
		
		checkReParsingForText(text);
	}
	

	
	@Test
	void testParserStringEscaping() {
		String text = loader("doc3.txt");
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode parsed = parser.getDocumentNode();
		
		DocumentNode expected = new DocumentNode();
		expected.addChildNode(new TextNode("text"));
		expected.addChildNode(new EchoNode(new Element[] {new ElementString("\" \\ \n\r\taaa")}));
		expected.addChildNode(new TextNode("end"));
		
		assertEquals(expected, parsed);
		
		checkReParsingForText(text);
	}
	
	
	@Test
	void testParserForNode() {
		String text = loader("doc4.txt");
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode parsed = parser.getDocumentNode();
		
		DocumentNode expected = new DocumentNode();
		expected.addChildNode(new ForLoopNode(new ElementVariable("i"), 
				new ElementConstantDouble(-1.35), new ElementVariable("bbb"), new ElementString("1")));
		
		assertEquals(expected, parsed);
		
		checkReParsingForText(text);
	}
	
	
	@Test
	void testParserTagNotClosed() {
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(loader("doc5.txt")));
	}
	
	
	@Test
	void testParserInvalidNumberOfEnd() {
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(loader("doc6.txt")));
	}
	
	@Test
	void testParserInvalidNumberOfEnd2() {
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(loader("doc6b.txt")));
	}
	
	
	@Test
	void testParserInvalidEscape() {
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("This is \\ invalid"));
	}
	
	@Test
	void testParserInvalidForTag() {
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("{$ FOR i @sin @sin @sin $}{$ END $}"));
	}
	
	@Test
	void testParserInvalidForTag2() {
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("{$ FOR i 1 2 3 4 5 $}{$ END $}"));
	}
	
	@Test
	void testParserInvalidForTag3() {
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("{$ FOR 56 abc 1 5 $}aaa{$ END $}"));
	}
	
	@Test
	void testParserInvalidForTag4() {
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("{$ FOR a abc 1 5 $}{$ = bffbfbfbf {$ END $}"));
	}
	
	@Test
	void testParserCombined1() {
		checkReParsingForText(loader("doc7.txt"));
	}
	
	@Test
	void testParserCombined2() {
		checkReParsingForText(loader("doc8.txt"));
	}
	
	
	
	
	
	private void checkReParsingForText(String docBody) {
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		
		String originalDocumentBody = document.getAsPlainText();
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		
		assertEquals(document, document2);
	}
	
	private String loader(String filename) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename)) {
			byte[] buffer = new byte[1024];
			while (true) {
				int read = is.read(buffer);
				if (read < 1)
					break;
				bos.write(buffer, 0, read);
			}
			return new String(bos.toByteArray(), StandardCharsets.UTF_8);
		} catch (IOException ex) {
			return null;
		}
	}
	
	
}
