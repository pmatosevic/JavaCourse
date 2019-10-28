package hr.fer.zemris.java.gui.calc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

/**
 * A button that adds a digit to the model
 * @author Patrik
 *
 */
public class DigitButton extends CalcButton implements ActionListener {

	private static final long serialVersionUID = 1L;

	/**
	 * Digit that this button adds
	 */
	private int digit;
	
	/**
	 * Creates a new button
	 * @param model model
	 * @param digit digit
	 * @param calc calculator
	 */
	public DigitButton(CalcModel model, int digit, Calculator calc) {
		super(model, Integer.toString(digit), calc);
		this.digit = digit;
		
		setFont(getFont().deriveFont(30f));
		addActionListener(this);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			model.insertDigit(digit);
		} catch (CalculatorInputException ex) {
			JOptionPane.showMessageDialog(calc, "Calculator is not editable.");
		}
		
	}
	
	
	
	
	
}
