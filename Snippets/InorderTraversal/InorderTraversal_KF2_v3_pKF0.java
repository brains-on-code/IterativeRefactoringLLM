package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public final class InorderTraversal {

    private InorderTraversal() {
        // Utility class; prevent instantiation
    }

    public static List<Integer> recursiveInorder(BinaryTree.Node root) {
        List<Integer> result = new ArrayList<>();
        recursiveHelper(root, result);
        return result;
    }

    public static List<Integer> iterativeInorder(BinaryTree.Node root) {
        List<Integer> result = new ArrayList<>();
        Deque<BinaryTree.Node> stack = new ArrayDeque<>();
        BinaryTree.Node current = root;

        while (!stack.isEmpty() || current != null) {
            pushLeftBranch(current, stack);
            current = stack.pop();
            result.add(current.data);
            current = current.right;
        }

        return result;
    }

    private static void recursiveHelper(BinaryTree.Node node, List<Integer> result) {
        if (node == null) {
            return;
        }
        recursiveHelper(node.left, result);
        result.add(node.data);
        recursiveHelper(node.right, result);
    }

    private static void pushLeftBranch(BinaryTree.Node node, Deque<BinaryTree.Node> stack) {
        while (node != null) {
            stack.push(node);
            node = node.left;
        }
    }
}