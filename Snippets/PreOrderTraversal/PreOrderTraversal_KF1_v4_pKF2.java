package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Utility class providing preorder traversal algorithms for binary trees.
 *
 * <p>Both recursive and iterative implementations are provided. Each method
 * returns a list of node values in preorder: root, left subtree, right subtree.
 */
public final class PreorderTraversal {

    private PreorderTraversal() {
        // Prevent instantiation of utility class.
    }

    /**
     * Performs a recursive preorder traversal of the given binary tree.
     *
     * @param root the root node of the binary tree
     * @return a list of node values in preorder (root, left, right)
     */
    public static List<Integer> recursivePreorder(BinaryTree.Node root) {
        List<Integer> result = new ArrayList<>();
        recursivePreorder(root, result);
        return result;
    }

    /**
     * Performs an iterative preorder traversal of the given binary tree.
     *
     * @param root the root node of the binary tree
     * @return a list of node values in preorder (root, left, right)
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

            // Push right child before left so that the left child is processed first.
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