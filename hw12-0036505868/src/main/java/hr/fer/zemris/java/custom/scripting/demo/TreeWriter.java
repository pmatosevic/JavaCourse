package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * A program that demonstrates visiting the nodes of the smart script document
 * @author Patrik
 *
 */
public class TreeWriter {

	/**
	 * Program entry point
	 * @param args command line arguments
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		String docBody = Files.readString(Paths.get(args[0]));
		SmartScriptParser p = new SmartScriptParser(docBody);
		WriterVisitor visitor = new WriterVisitor();
		p.getDocumentNode().accept(visitor);
	}
	
	/**
	 * A visitor that prints the content of the nodes.
	 * @author Patrik
	 *
	 */
	private static class WriterVisitor implements INodeVisitor {

		@Override
		public void visitTextNode(TextNode node) {
			System.out.print(node.getAsPlainText());
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			System.out.print(node.getStartText());
			int n = node.numberOfChildren();
			for (int i=0; i<n; i++) {
				node.getChild(i).accept(this);
			}
			System.out.print(node.getEndText());
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			System.out.print(node.getAsPlainText());
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			int n = node.numberOfChildren();
			for (int i=0; i<n; i++) {
				node.getChild(i).accept(this);
			}
		}
		
	}
	
}
