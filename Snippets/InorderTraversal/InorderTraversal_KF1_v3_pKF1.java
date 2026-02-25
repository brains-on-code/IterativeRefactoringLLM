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
        List<Integer> inorderValues = new ArrayList<>();
        traverseInorderRecursively(root, inorderValues);
        return inorderValues;
    }

    public static List<Integer> iterativeInorder(BinaryTree.Node root) {
        List<Integer> inorderValues = new ArrayList<>();
        if (root == null) {
            return inorderValues;
        }

        Deque<BinaryTree.Node> stack = new ArrayDeque<>();
        BinaryTree.Node current = root;

        while (!stack.isEmpty() || current != null) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            current = stack.pop();
            inorderValues.add(current.data);
            current = current.right;
        }
        return inorderValues;
    }

    private static void traverseInorderRecursively(BinaryTree.Node node, List<Integer> inorderValues) {
        if (node == null) {
            return;
        }
        traverseInorderRecursively(node.left, inorderValues);
        inorderValues.add(node.data);
        traverseInorderRecursively(node.right, inorderValues);
    }
}