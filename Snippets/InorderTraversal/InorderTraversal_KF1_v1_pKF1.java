package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Utility class for inorder traversal of a binary tree.
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
        if (root == null) {
            return result;
        }

        Deque<BinaryTree.Node> stack = new ArrayDeque<>();
        BinaryTree.Node current = root;

        while (!stack.isEmpty() || current != null) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
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