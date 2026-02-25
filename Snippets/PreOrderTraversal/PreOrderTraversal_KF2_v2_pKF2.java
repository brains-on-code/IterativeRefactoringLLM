package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Utility class providing pre-order traversal (root, left, right)
 * implementations for a binary tree.
 */
public final class PreOrderTraversal {

    private PreOrderTraversal() {
        // Prevent instantiation
    }

    /**
     * Performs a recursive pre-order traversal of the binary tree.
     *
     * @param root the root node of the binary tree
     * @return a list of node values in pre-order (root, left, right)
     */
    public static List<Integer> recursivePreOrder(BinaryTree.Node root) {
        List<Integer> result = new ArrayList<>();
        recursivePreOrder(root, result);
        return result;
    }

    /**
     * Performs an iterative pre-order traversal of the binary tree.
     *
     * @param root the root node of the binary tree
     * @return a list of node values in pre-order (root, left, right)
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
     * Helper method for the recursive pre-order traversal.
     *
     * @param node   the current node being visited
     * @param result the list collecting node values in pre-order
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