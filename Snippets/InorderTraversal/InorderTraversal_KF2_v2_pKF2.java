package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Utility class providing inorder traversal algorithms for a binary tree.
 */
public final class InorderTraversal {

    private InorderTraversal() {
        // Utility class; prevent instantiation
    }

    /**
     * Performs a recursive inorder traversal of the given binary tree.
     *
     * @param root the root node of the binary tree
     * @return a list of node values in inorder sequence
     */
    public static List<Integer> recursiveInorder(BinaryTree.Node root) {
        List<Integer> result = new ArrayList<>();
        recursiveInorder(root, result);
        return result;
    }

    /**
     * Performs an iterative inorder traversal of the given binary tree.
     *
     * @param root the root node of the binary tree
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
            // Move down to the leftmost node, pushing nodes along the path
            while (current != null) {
                stack.push(current);
                current = current.left;
            }

            // Visit the node at the top of the stack
            current = stack.pop();
            result.add(current.data);

            // Explore the right subtree
            current = current.right;
        }

        return result;
    }

    /**
     * Helper method for recursive inorder traversal.
     *
     * @param node   the current node
     * @param result the list collecting node values in inorder sequence
     */
    private static void recursiveInorder(BinaryTree.Node node, List<Integer> result) {
        if (node == null) {
            return;
        }

        recursiveInorder(node.left, result);
        result.add(node.data);
        recursiveInorder(node.right, result);
    }
}