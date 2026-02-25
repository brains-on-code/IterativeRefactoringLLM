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
     * Returns the values of the nodes visited in a recursive pre-order traversal.
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
     * Returns the values of the nodes visited in an iterative pre-order traversal.
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
     * Recursively visits nodes in pre-order and appends their values to {@code result}.
     *
     * @param node   the current node
     * @param result the list collecting node values
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