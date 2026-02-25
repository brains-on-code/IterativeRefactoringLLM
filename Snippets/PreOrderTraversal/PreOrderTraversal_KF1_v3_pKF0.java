package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Utility class for preorder traversal of a binary tree.
 *
 * Provides both recursive and iterative implementations.
 */
public final class PreorderTraversal {

    private PreorderTraversal() {
        // Utility class; prevent instantiation
    }

    /**
     * Recursive preorder traversal.
     *
     * @param root the root node of the binary tree
     * @return list of node values in preorder (root, left, right)
     */
    public static List<Integer> recursivePreorder(BinaryTree.Node root) {
        List<Integer> result = new ArrayList<>();
        recursivePreorder(root, result);
        return result;
    }

    /**
     * Iterative preorder traversal using a stack.
     *
     * @param root the root node of the binary tree
     * @return list of node values in preorder (root, left, right)
     */
    public static List<Integer> iterativePreorder(BinaryTree.Node root) {
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

    private static void recursivePreorder(BinaryTree.Node node, List<Integer> result) {
        if (node == null) {
            return;
        }

        result.add(node.data);
        recursivePreorder(node.left, result);
        recursivePreorder(node.right, result);
    }
}