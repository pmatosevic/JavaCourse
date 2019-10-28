package hr.fer.zemris.java.gui.layouts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.jupiter.api.Test;

public class CalcLayoutTest {
	
	@Test
	void testOutOfBounds() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l = new JLabel();
		assertThrows(CalcLayoutException.class, () -> p.add(l, new RCPosition(0, 1)));
		assertThrows(CalcLayoutException.class, () -> p.add(l, new RCPosition(3, -2)));
		assertThrows(CalcLayoutException.class, () -> p.add(l, new RCPosition(6, 2)));
		assertThrows(CalcLayoutException.class, () -> p.add(l, new RCPosition(2, 8)));
	}
	
	@Test
	void testFirstRow() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l = new JLabel();
		assertThrows(CalcLayoutException.class, () -> p.add(l, new RCPosition(1, 2)));
		assertThrows(CalcLayoutException.class, () -> p.add(l, new RCPosition(1, 3)));
		assertThrows(CalcLayoutException.class, () -> p.add(l, new RCPosition(1, 4)));
		assertThrows(CalcLayoutException.class, () -> p.add(l, new RCPosition(1, 5)));
	}
	
	@Test
	void testDuplicateAdding() {
		JPanel p = new JPanel(new CalcLayout(2));
		p.add(new JLabel(), new RCPosition(2, 3));
		p.add(new JLabel(), new RCPosition(1, 1));
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel(), new RCPosition(2, 3)));
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel(), new RCPosition(1, 1)));
	}

	@Test
	void testPreferredSize() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(10,30));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(20,15));
		p.add(l1, new RCPosition(2,2));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();
		assertEquals(new Dimension(152, 158), dim);
	}
	
	@Test
	void testPreferredSizeAdvanced() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(108,15));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(16,30));
		p.add(l1, new RCPosition(1,1));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();
		assertEquals(new Dimension(152, 158), dim);
	}
	
}
