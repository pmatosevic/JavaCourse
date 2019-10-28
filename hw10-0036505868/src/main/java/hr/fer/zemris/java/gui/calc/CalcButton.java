package hr.fer.zemris.java.gui.calc;

import java.awt.Color;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * A button in the calculator window.
 * 
 * @author Patrik
 *
 */
public class CalcButton extends JButton {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Calculator model
	 */
	protected CalcModel model;
	
	/**
	 * Button text
	 */
	protected String text;

	/**
	 * The calculator window
	 */
	protected Calculator calc;

	public CalcButton(CalcModel model, String text, Calculator calc) {
		this.model = model;
		this.text = text;
		this.calc = calc;
		
		setBackground(new Color(220, 220, 255));
		setText(text);
	}
	
	
}
