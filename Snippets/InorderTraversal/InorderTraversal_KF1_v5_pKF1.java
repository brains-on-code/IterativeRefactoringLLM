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
        List<Integer> traversalResult = new ArrayList<>();
        traverseInorderRecursively(root, traversalResult);
        return traversalResult;
    }

    public static List<Integer> iterativeInorder(BinaryTree.Node root) {
        List<Integer> traversalResult = new ArrayList<>();
        if (root == null) {
            return traversalResult;
        }

        Deque<BinaryTree.Node> stack = new ArrayDeque<>();
        BinaryTree.Node current = root;

        while (!stack.isEmpty() || current != null) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            current = stack.pop();
            traversalResult.add(current.data);
            current = current.right;
        }
        return traversalResult;
    }

    private static void traverseInorderRecursively(BinaryTree.Node node, List<Integer> traversalResult) {
        if (node == null) {
            return;
        }
        traverseInorderRecursively(node.left, traversalResult);
        traversalResult.add(node.data);
        traverseInorderRecursively(node.right, traversalResult);
    }
}