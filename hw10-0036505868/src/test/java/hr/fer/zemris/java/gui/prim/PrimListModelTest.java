package hr.fer.zemris.java.gui.prim;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.junit.jupiter.api.Test;

public class PrimListModelTest {

	@Test
	void testConstruction() {
		PrimListModel model = new PrimListModel();
		assertEquals(1, model.getElementAt(0));
	}
	
	@Test
	void testGetElementAt() {
		PrimListModel model = new PrimListModel();
		model.next();
		assertEquals(2, model.getSize());
		assertEquals(2, model.getElementAt(1));
	}
	
	
	@Test
	void testGetSize() {
		PrimListModel model = new PrimListModel();
		assertEquals(1, model.getSize());
		model.next();
		model.next();
		assertEquals(3, model.getSize());
	}
	
	@Test
	void testNext() {
		PrimListModel model = new PrimListModel();
		model.next();
		model.next();
		model.next();
		assertEquals(5, model.getElementAt(3));
	}
	
	@Test
	void testAddListDataListener() {
		PrimListModel model = new PrimListModel();
		var listener = new ListDataListener() {
			
			int cnt = 0;
			
			@Override
			public void intervalRemoved(ListDataEvent e) {
			}
			
			@Override
			public void intervalAdded(ListDataEvent e) {
				cnt++;
			}
			
			@Override
			public void contentsChanged(ListDataEvent e) {
			}
		};
		model.addListDataListener(listener);
		model.next();
		assertEquals(1, listener.cnt);
	}
	
	@Test
	void testRemoveListDataListener() {
		PrimListModel model = new PrimListModel();
		var listener = new ListDataListener() {
			
			int cnt = 0;
			
			@Override
			public void intervalRemoved(ListDataEvent e) {
			}
			
			@Override
			public void intervalAdded(ListDataEvent e) {
				cnt++;
			}
			
			@Override
			public void contentsChanged(ListDataEvent e) {
			}
		};
		model.addListDataListener(listener);
		model.removeListDataListener(listener);;
		model.next();
		assertEquals(0, listener.cnt);
	}
	
	
}
