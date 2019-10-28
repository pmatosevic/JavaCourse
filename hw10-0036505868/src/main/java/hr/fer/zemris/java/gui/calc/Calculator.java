package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Calculator program. Performs calculations using GUI.
 * 
 * @author Patrik
 *
 */
public class Calculator extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Space between rows and columns
	 */
	private static final int SPACE = 2;
	
	/**
	 * The calculator model
	 */
	private CalcModel model;
	
	/**
	 * The stack
	 */
	private Deque<Double> stack = new LinkedList<>();
	
	/**
	 * The display
	 */
	protected JLabel display;
	
	/**
	 * List of buttons for inverse operations
	 */
	private List<InverseOperationButton> invButtons = new ArrayList<>();
	
	/**
	 * Inverse operation check box
	 */
	private JCheckBox invSelect;
	
	
	/**
	 * Program entry point
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Calculator().setVisible(true);
		});
	}
	
	
	/**
	 * Creates a new window
	 */
	public Calculator() {
		model = new CalcModelImpl();
		model.addCalcValueListener(m -> display.setText(m.toString()));
		
		setTitle("Java Calculator v1.0");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(100, 100);
		setSize(600, 300);
		initGUI();
	}

	/**
	 * Inits the GUI
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(SPACE));
		
		display = new JLabel();
		display.setBackground(Color.YELLOW);
		display.setOpaque(true);
		display.setHorizontalAlignment(JLabel.RIGHT);
		display.setFont(display.getFont().deriveFont(30f));
		display.setText("0");
		cp.add(display, "1,1");
		
		CalcButton eqButton = new CalcButton(model, "=", this);
		eqButton.addActionListener(e -> {
			if (!model.isActiveOperandSet() || model.getPendingBinaryOperation() == null) {
				JOptionPane.showMessageDialog(this, "No operand given.");
				return;
			}
			double result = model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue());
			model.clearActiveOperand();
			model.setPendingBinaryOperation(null);
			model.setValue(result);
		});
		cp.add(eqButton, "1,6");
		
		CalcButton clrButton = new CalcButton(model, "clr", this);
		clrButton.addActionListener(e -> model.clear());
		cp.add(clrButton, "1,7");
		
		CalcButton resetButton = new CalcButton(model, "reset", this);
		resetButton.addActionListener(e -> model.clearAll());
		cp.add(resetButton, "2,7");
		
		CalcButton pushButton = new CalcButton(model, "push", this);
		pushButton.addActionListener(e -> stack.push(model.getValue()));
		cp.add(pushButton, "3,7");
		
		CalcButton popButton = new CalcButton(model, "pop", this);
		popButton.addActionListener(e -> {
			if (stack.isEmpty()) {
				JOptionPane.showMessageDialog(this, "The stack is empty.");
				return;
			}
			model.setValue(stack.pop());
		});
		cp.add(popButton, "4,7");
		
		CalcButton signButton = new CalcButton(model, "+/-", this);
		signButton.addActionListener(e -> {
			try {
				model.swapSign();
			} catch (CalculatorInputException ex) {
				JOptionPane.showMessageDialog(this, "Calculator is not editable");
			}
		});
		cp.add(signButton, "5,4");
		
		CalcButton periodButton = new CalcButton(model, ".", this);
		periodButton.addActionListener(e -> {
			try {
				model.insertDecimalPoint();
			} catch (CalculatorInputException ex) {
				JOptionPane.showMessageDialog(this, "Calculator is not editable");
			}
		});
		cp.add(periodButton, "5,5");
		
		ExpRootButton expRootButton = new ExpRootButton(model, this);
		cp.add(expRootButton, "5,1");
		invButtons.add(expRootButton);
		
		addDigitButtons(cp);
		addUnaryButtons();
		addBinaryButtons(cp);
		
		invSelect = new JCheckBox("Inv");
		invSelect.addActionListener(e -> {
			boolean sel = ((JCheckBox)e.getSource()).isSelected();
			for (InverseOperationButton b : invButtons) {
				b.setInverse(sel);
			}
		});
		cp.add(invSelect, "5,7");
	}

	/**
	 * Adds all binary operation buttons.
	 * 
	 * @param cp container
	 */
	private void addBinaryButtons(Container cp) {
		cp.add(new BinaryOperationButton(model, "/", (a, b) -> a/b, this), "2,6");
		cp.add(new BinaryOperationButton(model, "*", (a, b) -> a*b, this), "3,6");
		cp.add(new BinaryOperationButton(model, "-", (a, b) -> a-b, this), "4,6");
		cp.add(new BinaryOperationButton(model, "+", (a, b) -> a+b, this), "5,6");
	}

	/**
	 * Adds all unary operation buttons.
	 */
	private void addUnaryButtons() {
		addInverseButton(new UnaryOperationButton(model, "1/x", x -> 1/x, "1/x", x -> 1/x, this), "2,1");
		addInverseButton(new UnaryOperationButton(model, "log", Math::log10, "10^", x -> Math.pow(10, x), this), "3,1");
		addInverseButton(new UnaryOperationButton(model, "ln", Math::log, "e^", Math::exp, this), "4,1");
		addInverseButton(new UnaryOperationButton(model, "sin", Math::sin, "arcsin", Math::asin, this), "2,2");
		addInverseButton(new UnaryOperationButton(model, "cos", Math::cos, "arccos", Math::acos, this), "3,2");
		addInverseButton(new UnaryOperationButton(model, "tan", Math::tan, "arctan", Math::atan, this), "4,2");
		addInverseButton(new UnaryOperationButton(model, "ctg", x -> 1/Math.tan(x), "arcctg", x -> Math.atan(1/x), this), "5,2");
	}

	/**
	 * Adds a button to the content pane and the list of inverse operation buttons
	 * @param b button
	 * @param str position
	 */
	private void addInverseButton(UnaryOperationButton b, String str) {
		getContentPane().add(b, str);
		invButtons.add(b);
	}

	/**
	 * Adds all digit buttons
	 * @param cp container
	 */
	private void addDigitButtons(Container cp) {
		cp.add(new DigitButton(model, 0, this), "5,3");
		for (int i=0; i<3; i++) {
			for (int j=0; j<3; j++) {
				cp.add(new DigitButton(model, 3*i+j+1, this), new RCPosition(4-i, 3+j));
			}
		}
	}
	
	
	/**
	 * A button that performs power and n-th root operations.
	 * 
	 * @author Patrik
	 *
	 */
	private static class ExpRootButton extends BinaryOperationButton implements InverseOperationButton {
		private static final long serialVersionUID = 1L;
		
		/**
		 * Default text
		 */
		private static final String text1 = "x^n";
		
		/**
		 * Inverse operation text
		 */
		private static final String text2 = "x^(1/n)";
		
		/**
		 * Default operator
		 */
		private final static DoubleBinaryOperator oper1 = (x, n) -> Math.pow(x, n);
		
		/**
		 * Inverse operator
		 */
		private final static DoubleBinaryOperator oper2 = (x, n) -> Math.pow(x, 1/n);

		/**
		 * Creates a new button
		 * @param model model
		 * @param calc calculator
		 */
		public ExpRootButton(CalcModel model, Calculator calc) {
			super(model, text1, oper1, calc);
		}

		@Override
		public void setInverse(boolean inverse) {
			setText(inverse ? text2 : text1);
			this.oper = inverse ? oper2 : oper1;
		}
		
		
		
	}
	
	
}
