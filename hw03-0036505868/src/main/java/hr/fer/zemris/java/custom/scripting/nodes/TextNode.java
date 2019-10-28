package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Objects;

/**
 * A node representing a piece of textual data
 * @author Patrik
 *
 */
public class TextNode extends Node {

	/**
	 * The text
	 */
	private String text;
	
	
	/**
	 * Creates a new {@code TextNode} with text
	 * @param text text
	 */
	public TextNode(String text) {
		this.text = text;
	}

	/**
	 * Returns the text
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	
	
	@Override
	public String getAsPlainText() {
		StringBuilder sb = new StringBuilder();
		char[] data = text.toCharArray();
		
		for (int pos = 0; pos < data.length; pos++) {
			switch (data[pos]) {
			case '\\':
				sb.append("\\\\");
				break;
			case '{':
				sb.append("\\{");
				break;
			default:
				sb.append(data[pos]);
			}
		}
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(text);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof TextNode))
			return false;
		TextNode other = (TextNode) obj;
		return Objects.equals(text, other.text);
	}
	
	
	
	
	
}
