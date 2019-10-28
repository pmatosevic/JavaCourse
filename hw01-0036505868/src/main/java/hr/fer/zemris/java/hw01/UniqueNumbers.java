package hr.fer.zemris.java.hw01;

import java.util.Scanner;
import java.util.function.IntConsumer;

/**
 * Program that demonstrates adding whole numbers to a binary search tree and
 * then printing them in ascending and descending order.
 * 
 * The user is asked to enter numbers, and these numbers are added to the tree
 * (unless they already exists). Entering numbers ends when user inputs "kraj".
 * After that, the values of the binary tree are printed in ascending, and then
 * is descending order.
 * 
 * @author Patrik
 *
 */
public class UniqueNumbers {
	
	/**
	 * Represents a node of a binary tree.
	 * Contains an integer value.
	 * 
	 * @author Patrik
	 *
	 */
	static class TreeNode {
		/**
		 * Reference to the left child.
		 */
		TreeNode left;
		
		/**
		 * Reference to the right child.
		 */
		TreeNode right;
		
		/**
		 * Value of the node.
		 */
		int value;
	}
	
	
	
	/**
	 * Program entry point
	 * @param args command-line arguments (not used)
	 */
	public static void main(String[] args) {
		TreeNode root = null;
		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.print("Unesite broj > ");
			String input = sc.next();

			if (input.equals("kraj")) {
				break;
			}

			try {
				int value = Integer.parseInt(input);

				if (containsValue(root, value)) {
					System.out.println("Broj već postoji. Preskačem.");
				} else {
					root = addNode(root, value);
					System.out.println("Dodano.");
				}

			} catch (NumberFormatException ex) {
				System.out.println("'" + input + "' nije cijeli broj.");
			}
		}
		sc.close();

		System.out.print("Ispis od najmanjeg:");
		printTree(root, true);
		System.out.println();

		System.out.print("Ispis od najvećeg:");
		printTree(root, false);
		System.out.println();
	}	
	
	
	
	/**
	 * Adds a node with given value to a binary search tree rooted at {@code root}.
	 * Since the root node can be changed (if the tree is empty), it is returned.
	 * If a node with given value already exists in the tree, nothing is added.
	 * 
	 * @param root the root of a binary tree
	 * @param value value to add to a binary tree
	 * @return the (new) root of the binary tree
	 */
	public static TreeNode addNode(TreeNode root, int value) {
		if (root == null) {
			TreeNode tmp = new TreeNode();
			tmp.value = value;
			return tmp;
		}
		
		if (value > root.value) {
			root.right = addNode(root.right, value);
		} else if (value < root.value) {
			root.left = addNode(root.left, value);
		}
		return root;
	}
	
	
	/**
	 * Returns the size of a binary search tree
	 * 
	 * @param root root of a binary tree
	 * @return the size of a binary tree
	 */
	public static int treeSize(TreeNode root) {
		if (root == null) {
			return 0;
		}
		
		int leftSize = treeSize(root.left);
		int rightSize = treeSize(root.right);
		
		return leftSize + rightSize + 1;
	}
	
	
	/**
	 * Checks whether given value exists in a binary search tree.
	 * 
	 * @param root root of a binary tree
	 * @param value value to check for existence
	 * @return {@code true} if value exists in the binary tree, {@code false} otherwise
	 */
	public static boolean containsValue(TreeNode root, int value) {
		while (root != null) {
			if (value == root.value) {
				return true;
			} else if (value > root.value) {
				root = root.right;
			} else {
				root = root.left;
			}
		}
		return false;
	}
	
	
	/**
	 * Prints values of nodes in a binary search tree rooted at root.
	 * Values are printed in ascending order if {@code ascending} is {@code true}, 
	 * or descending order otherwise.
	 * 
	 * @param root root of a binary tree
	 * @param ascending whether to print in ascending or descending order
	 */
	public static void printTree(TreeNode root, boolean ascending) {
		if (root == null) {
			return;
		}
		
		printTree(ascending ? root.left : root.right, ascending);
		System.out.print(" " + root.value);
		printTree(ascending ? root.right : root.left, ascending);
	}
	
	
	
	public static void inOrderVisit(TreeNode root, boolean ascending, IntConsumer action) {
		if (root == null) {
			return;
		}
		
		inOrderVisit(ascending ? root.left : root.right, ascending, action);
		action.accept(root.value);
		inOrderVisit(ascending ? root.right : root.left, ascending, action);
	}
	
	
}
