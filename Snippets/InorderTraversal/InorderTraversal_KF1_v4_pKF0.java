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
        traverseRecursively(root, result);
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
        Deque<BinaryTree.Node> stack = new ArrayDeque<>();
        BinaryTree.Node current = root;

        while (current != null || !stack.isEmpty()) {
            pushLeftBranch(current, stack);
            current = stack.pop();
            result.add(current.data);
            current = current.right;
        }

        return result;
    }

    private static void traverseRecursively(BinaryTree.Node node, List<Integer> result) {
        if (node == null) {
            return;
        }
        traverseRecursively(node.left, result);
        result.add(node.data);
        traverseRecursively(node.right, result);
    }

    private static void pushLeftBranch(BinaryTree.Node node, Deque<BinaryTree.Node> stack) {
        while (node != null) {
            stack.push(node);
            node = node.left;
        }
    }
}