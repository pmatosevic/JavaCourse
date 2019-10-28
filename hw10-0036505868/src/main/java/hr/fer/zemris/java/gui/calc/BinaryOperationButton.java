package hr.fer.zemris.java.gui.calc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.DoubleBinaryOperator;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * A button that does a binary operation.
 * @author Patrik
 *
 */
public class BinaryOperationButton extends CalcButton implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Operation that this button performs
	 */
	protected DoubleBinaryOperator oper;

	/**
	 * Creates a new button
	 * @param model calculator model
	 * @param text button text
	 * @param oper operation
	 * @param calc calculator window
	 */
	public BinaryOperationButton(CalcModel model, String text, DoubleBinaryOperator oper, Calculator calc) {
		super(model, text, calc);
		this.oper = oper;
		addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (model.isActiveOperandSet()) {
			double result = model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue());
			model.setActiveOperand(result);
			model.clear();
			calc.display.setText(Double.toString(result));
		} else {
			model.setActiveOperand(model.getValue());
			model.clear();
		}
		model.setPendingBinaryOperation(oper);
	}

}
