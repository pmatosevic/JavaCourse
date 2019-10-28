package hr.fer.zemris.java.gui.layouts;

import java.util.Objects;

/**
 * Represents a position in the {@link CalcLayout}.
 * @author Patrik
 *
 */
public class RCPosition {

	/**
	 * Row
	 */
	private int row;
	
	/**
	 * Column
	 */
	private int column;

	/**
	 * Creates a new object
	 * @param row row
	 * @param column column
	 */
	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}

	/**
	 * @return the row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @return the column
	 */
	public int getColumn() {
		return column;
	}
	
	/**
	 * Constructs a new object from its string representation.
	 * @param str string
	 * @return {@link RCPosition} object
	 */
	public static RCPosition fromString(String str) {
		String[] parts = str.split(",");
		if (parts.length != 2) {
			throw new IllegalArgumentException("Invalid format.");
		}
		return new RCPosition(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(column, row);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof RCPosition))
			return false;
		RCPosition other = (RCPosition) obj;
		return column == other.column && row == other.row;
	}
	
	
	
	
	
}
