package hr.fer.zemris.java.gui.calc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

/**
 * An implementation of the {@link CalcModel}.
 * 
 * @author Patrik
 *
 */
public class CalcModelImpl implements CalcModel {

	/**
	 * List of listeners
	 */
	private List<CalcValueListener> listeners = new ArrayList<>();
	
	/**
	 * Editable flag
	 */
	private boolean editable = true;
	
	/**
	 * Negative flag
	 */
	private boolean negative = false;
	
	/**
	 * The input string
	 */
	private String inputString = "";
	
	/**
	 * The input value
	 */
	private double inputValue = 0.0;
	
	/**
	 * The active operand
	 */
	private Double activeOperand = null;
	
	/**
	 * The next pending operation
	 */
	private DoubleBinaryOperator pendingOperation = null;
	
	/**
	 * Notfies all listeners about the change.
	 */
	private void notifyListeners() {
		for (CalcValueListener l : listeners) {
			l.valueChanged(this);
		}
	}
	
	@Override
	public void addCalcValueListener(CalcValueListener l) {
		Objects.requireNonNull(l);
		if (!listeners.contains(l)) {
			listeners.add(l);
		}
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		listeners.remove(l);
	}

	@Override
	public double getValue() {
		return inputValue * (negative ? -1 : 1);
	}

	@Override
	public void setValue(double value) {
		inputValue = value;
		inputString = Double.toString(inputValue);
		editable = false;
		notifyListeners();
	}

	@Override
	public boolean isEditable() {
		return editable;
	}

	@Override
	public void clear() {
		negative = false;
		inputString = "";
		inputValue = 0.0;
		editable = true;
		notifyListeners();
	}

	@Override
	public void clearAll() {
		clear();
		clearActiveOperand();
		pendingOperation = null;
		notifyListeners();
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		checkEditable();
		negative = !negative;
		notifyListeners();
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		checkEditable();
		if (inputString.isEmpty()) {
			throw new CalculatorInputException("Input is empty.");
		}
		if (inputString.contains(".")) {
			throw new CalculatorInputException("Input already contains point.");
		}
		
		inputString = inputString + ".";
		inputValue = Double.parseDouble(inputString);
		notifyListeners();
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		checkEditable();
		if (digit < 0 || digit > 9) {
			throw new IllegalArgumentException("Expected one-digit number.");
		}
		
		if (inputString.equals("0")) {
			if (digit == 0) {
				return;
			}
			inputString = "";
		}
		
		inputValue = Double.parseDouble(inputString + digit);
		if (Double.isInfinite(inputValue)) {
			throw new CalculatorInputException("Input cannot be parsed into a number.");
		}
		inputString = inputString + digit;
		notifyListeners();
	}

	@Override
	public boolean isActiveOperandSet() {
		return activeOperand != null;
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if (activeOperand == null) {
			throw new IllegalStateException("Active operand was not set.");
		}
		return activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
	}

	@Override
	public void clearActiveOperand() {
		activeOperand = null;
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		pendingOperation = op;
	}
	
	private void checkEditable() {
		if (!editable) {
			throw new CalculatorInputException("Calculator is not editable.");
		}
	}
	
	@Override
	public String toString() {
		String ret = inputString.isEmpty() ? "0" : inputString;
		return (negative ? "-" : "") + ret;
	}

}
