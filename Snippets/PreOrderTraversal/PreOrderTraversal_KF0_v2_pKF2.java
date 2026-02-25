package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Utility class for pre-order traversal (ROOT -> LEFT -> RIGHT) of a binary tree.
 *
 * <p>Provides both recursive and iterative implementations.</p>
 *
 * <p>Complexities:</p>
 * <ul>
 *   <li>Recursive: O(n) time, O(n) space (call stack), where n is the number of nodes.</li>
 *   <li>Iterative: O(n) time, O(h) space, where h is the height of the tree.
 *       In the worst case (completely unbalanced tree), h can be O(n).</li>
 * </ul>
 */
public final class PreOrderTraversal {

    private PreOrderTraversal() {
        // Prevent instantiation of utility class.
    }

    /**
     * Performs a recursive pre-order traversal of the given binary tree.
     *
     * @param root the root node of the tree
     * @return list of node values in pre-order
     */
    public static List<Integer> recursivePreOrder(BinaryTree.Node root) {
        List<Integer> result = new ArrayList<>();
        recursivePreOrder(root, result);
        return result;
    }

    /**
     * Performs an iterative pre-order traversal of the given binary tree.
     *
     * @param root the root node of the tree
     * @return list of node values in pre-order
     */
    public static List<Integer> iterativePreOrder(BinaryTree.Node root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Deque<BinaryTree.Node> stack = new LinkedList<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            BinaryTree.Node current = stack.pop();
            result.add(current.data);

            // Push right child first so that the left child is processed first.
            if (current.right != null) {
                stack.push(current.right);
            }
            if (current.left != null) {
                stack.push(current.left);
            }
        }

        return result;
    }

    /**
     * Helper method for recursive pre-order traversal.
     *
     * @param node   current node being visited
     * @param result accumulator for traversal output
     */
    private static void recursivePreOrder(BinaryTree.Node node, List<Integer> result) {
        if (node == null) {
            return;
        }

        result.add(node.data);
        recursivePreOrder(node.left, result);
        recursivePreOrder(node.right, result);
    }
}