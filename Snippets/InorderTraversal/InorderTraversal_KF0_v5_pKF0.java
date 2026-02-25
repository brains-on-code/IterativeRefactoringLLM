package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Inorder traversal of a binary tree: LEFT -> ROOT -> RIGHT.
 *
 * Recursive: O(n) time, O(n) space (call stack), where n is the number of nodes.
 * Iterative: O(n) time, O(h) space, where h is the height of the tree.
 */
public final class InorderTraversal {

    private InorderTraversal() {
        // Utility class; prevent instantiation
    }

    public static List<Integer> recursiveInorder(BinaryTree.Node root) {
        List<Integer> result = new ArrayList<>();
        recursiveInorder(root, result);
        return result;
    }

    public static List<Integer> iterativeInorder(BinaryTree.Node root) {
        List<Integer> result = new ArrayList<>();
        Deque<BinaryTree.Node> stack = new ArrayDeque<>();
        BinaryTree.Node current = root;

        while (current != null || !stack.isEmpty()) {
            pushLeftBranch(current, stack);

            BinaryTree.Node node = stack.pop();
            result.add(node.data);
            current = node.right;
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

    private static void pushLeftBranch(BinaryTree.Node node, Deque<BinaryTree.Node> stack) {
        while (node != null) {
            stack.push(node);
            node = node.left;
        }
    }
}