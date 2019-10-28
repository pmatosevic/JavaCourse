package hr.fer.zemris.java.hw01;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw01.UniqueNumbers.TreeNode;
import static hr.fer.zemris.java.hw01.UniqueNumbers.*;

/**
 * Class with tests for class {@code UniqueNumbers}
 * @author Patrik
 *
 */
public class UniqueNumbersTest {

	@Test
	public void testConstructionBasic() {
		TreeNode root = constructTreeBasic();
		
		assertNotNull(root);
		assertEquals(1, root.value);
		assertNull(root.left);
		assertNull(root.right);
	}
	
	@Test
	public void testConstructionSmall() {
		TreeNode root = constructTreeSmall();
		
		assertEquals(42, root.value);
		assertEquals(21, root.left.value);
		assertEquals(35, root.left.right.value);
		assertEquals(76, root.right.value);
	}
	
	@Test
	public void testConstructionBig() {
		TreeNode root = constructTreeBig();
		
		assertEquals(125, root.value);
		assertEquals(26, root.left.value);
		assertEquals(-10, root.left.left.left.value);
		assertEquals(300, root.right.value);
	}
	
	
	@Test
	public void testContainsEmpty() {
		TreeNode root = null;
		
		assertFalse(containsValue(root, 0));
	}

	@Test
	public void testContainsBasic() {
		TreeNode root = constructTreeBasic();
		
		assertTrue(containsValue(root, 1));
		assertFalse(containsValue(root, 0));
	}
	
	@Test
	public void testContainsSmall() {
		TreeNode root = constructTreeSmall();
		
		assertTrue(containsValue(root, 42));
		assertTrue(containsValue(root, 76));
		assertFalse(containsValue(root, 0));
	}
	
	@Test
	public void testContainsBig() {
		TreeNode root = constructTreeBig();
		
		assertTrue(containsValue(root, -10));
		assertTrue(containsValue(root, 125));
		assertTrue(containsValue(root, 70));
		assertFalse(containsValue(root, 0));
	}
	
	
	@Test
	public void testSizeEmpty() {
		TreeNode root = null;
		
		assertEquals(0, treeSize(root));
	}

	@Test
	public void testSizeBasic() {
		TreeNode root = constructTreeBasic();
		
		assertEquals(1, treeSize(root));
	}
	
	@Test
	public void testSizeSmall() {
		TreeNode root = constructTreeSmall();
		
		assertEquals(4, treeSize(root));
	}
	
	
	@Test
	public void testSizeBig() {
		TreeNode root = constructTreeBig();
		
		assertEquals(7, treeSize(root));
	}
	
	
	
	/**
	 * Constructs an example of a binary tree
	 * @return root node of the binary tree
	 */
	private TreeNode constructTreeBasic() {
		TreeNode root = null;
		root = addNode(root, 1);
		return root;
	}
	
	/**
	 * Constructs an example of a binary tree
	 * @return root node of the binary tree
	 */
	private TreeNode constructTreeSmall() {
		TreeNode root = null;
		root = addNode(root, 42);
		root = addNode(root, 76);
		root = addNode(root, 21);
		root = addNode(root, 76);
		root = addNode(root, 35);
		return root;
	}
	
	/**
	 * Constructs an example of a binary tree
	 * @return root node of the binary tree
	 */
	private TreeNode constructTreeBig() {
		TreeNode root = null;
		root = addNode(root, 125);
		root = addNode(root, 26);
		root = addNode(root, 11);
		root = addNode(root, -10);
		root = addNode(root, 300);
		root = addNode(root, 200);
		root = addNode(root, 70);
		return root;
	}
	
}
