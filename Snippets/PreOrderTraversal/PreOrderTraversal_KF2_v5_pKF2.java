package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Provides pre-order (root, left, right) traversal methods for a binary tree.
 */
public final class PreOrderTraversal {

    private PreOrderTraversal() {
        // Utility class; prevent instantiation.
    }

    /**
     * Returns a list of node values from a recursive pre-order traversal.
     *
     * @param root the root node of the binary tree
     * @return list of node values in pre-order
     */
    public static List<Integer> recursivePreOrder(BinaryTree.Node root) {
        List<Integer> result = new ArrayList<>();
        recursivePreOrder(root, result);
        return result;
    }

    /**
     * Returns a list of node values from an iterative pre-order traversal.
     *
     * @param root the root node of the binary tree
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

            // Push right child first so left child is processed first.
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
     * Performs recursive pre-order traversal, adding node values to {@code result}.
     *
     * @param node   current node
     * @param result accumulator for node values
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