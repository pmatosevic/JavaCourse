package hr.fer.zemris.java.gui.calc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.DoubleUnaryOperator;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * A button that performs unary operation.
 * 
 * @author Patrik
 *
 */
public class UnaryOperationButton extends CalcButton implements ActionListener, InverseOperationButton {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Default operation
	 */
	private DoubleUnaryOperator oper;
	
	/**
	 * Inverse operation text
	 */
	private String invText;
	
	/**
	 * Inverse operation
	 */
	private DoubleUnaryOperator invOper;
	
	/**
	 * inverse flag
	 */
	private boolean inverse = false;

	/**
	 * Creates a new button
	 * @param model model
	 * @param text text
	 * @param oper operation
	 * @param invText inverse operation text
	 * @param invOper inverse operation
	 * @param calc calculator
	 */
	public UnaryOperationButton(CalcModel model, String text, DoubleUnaryOperator oper, String invText, DoubleUnaryOperator invOper, Calculator calc) {
		super(model, text, calc);
		this.oper = oper;
		this.invText = invText;
		this.invOper = invOper;
		
		addActionListener(this);
	}
	
	@Override
	public void setInverse(boolean inverse) {
		this.inverse = inverse;
		setText(inverse ? invText : text);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		double operand = model.getValue();
		double result = inverse ? invOper.applyAsDouble(operand) : oper.applyAsDouble(operand);
		model.setValue(result);
	}

}
