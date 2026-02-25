package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Utility class providing inorder traversal methods for {@link BinaryTree}.
 */
public final class InorderTraversal {

    private InorderTraversal() {
        // Utility class; prevent instantiation
    }

    /**
     * Performs a recursive inorder traversal of the binary tree.
     *
     * @param root the root node of the tree
     * @return a list of node values in inorder sequence
     */
    public static List<Integer> recursiveInorder(BinaryTree.Node root) {
        List<Integer> result = new ArrayList<>();
        recursiveInorder(root, result);
        return result;
    }

    /**
     * Performs an iterative inorder traversal of the binary tree.
     *
     * @param root the root node of the tree
     * @return a list of node values in inorder sequence
     */
    public static List<Integer> iterativeInorder(BinaryTree.Node root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Deque<BinaryTree.Node> stack = new ArrayDeque<>();
        BinaryTree.Node current = root;

        while (!stack.isEmpty() || current != null) {
            // Reach the leftmost node of the current node
            while (current != null) {
                stack.push(current);
                current = current.left;
            }

            // Current must be null at this point
            current = stack.pop();
            result.add(current.data);

            // Visit the right subtree
            current = current.right;
        }

        return result;
    }

    private static void recursiveInorder(BinaryTree.Node node, List<Integer> result) {
        if (node == null) {
            return;
        }
        recursiveInorder(node.left, result);
        result.add(node.data);
        recursiveInorder(node.right, result);
    }
}