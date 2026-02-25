package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Provides recursive and iterative inorder traversal methods for a binary tree.
 */
public final class InorderTraversal {

    private InorderTraversal() {
        // Prevent instantiation of utility class.
    }

    /**
     * Returns the inorder traversal of the binary tree using recursion.
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
     * Returns the inorder traversal of the binary tree using an explicit stack.
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
            // Traverse to the leftmost node.
            while (current != null) {
                stack.push(current);
                current = current.left;
            }

            // Process the node and move to the right subtree.
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