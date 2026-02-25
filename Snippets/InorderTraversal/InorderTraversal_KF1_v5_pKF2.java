package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Utility class providing inorder traversal methods for a binary tree.
 */
public final class InorderTraversal {

    private InorderTraversal() {
        // Utility class; prevent instantiation.
    }

    /**
     * Performs a recursive inorder traversal of the binary tree.
     *
     * @param root the root node of the binary tree
     * @return list of node values in inorder sequence
     */
    public static List<Integer> recursiveInorder(BinaryTree.Node root) {
        List<Integer> result = new ArrayList<>();
        recursiveInorder(root, result);
        return result;
    }

    /**
     * Performs an iterative inorder traversal of the binary tree using a stack.
     *
     * @param root the root node of the binary tree
     * @return list of node values in inorder sequence
     */
    public static List<Integer> iterativeInorder(BinaryTree.Node root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Deque<BinaryTree.Node> stack = new ArrayDeque<>();
        BinaryTree.Node current = root;

        while (!stack.isEmpty() || current != null) {
            // Move down to the leftmost node.
            while (current != null) {
                stack.push(current);
                current = current.left;
            }

            // Visit node, then traverse its right subtree.
            current = stack.pop();
            result.add(current.data);
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