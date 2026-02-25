package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Utility class for pre-order (ROOT → LEFT → RIGHT) traversal of a binary tree.
 *
 * <p>Provides both recursive and iterative implementations.</p>
 *
 * <p>Time complexity (both): O(n), where n is the number of nodes.</p>
 * <p>Space complexity:</p>
 * <ul>
 *   <li>Recursive: O(n) in the worst case due to call stack depth.</li>
 *   <li>Iterative: O(h), where h is the height of the tree (O(n) in the worst case).</li>
 * </ul>
 */
public final class PreOrderTraversal {

    private PreOrderTraversal() {
        // Utility class; prevent instantiation.
    }

    /**
     * Performs a recursive pre-order traversal.
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
     * Performs an iterative pre-order traversal using an explicit stack.
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

            // Push right child first so the left child is processed next.
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
     * Recursive helper for pre-order traversal.
     *
     * @param node   current node
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