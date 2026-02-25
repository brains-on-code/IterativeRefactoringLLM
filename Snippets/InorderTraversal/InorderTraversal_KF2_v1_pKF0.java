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
        performRecursiveInorder(root, result);
        return result;
    }

    public static List<Integer> iterativeInorder(BinaryTree.Node root) {
        List<Integer> result = new ArrayList<>();
        Deque<BinaryTree.Node> stack = new ArrayDeque<>();
        BinaryTree.Node current = root;

        while (!stack.isEmpty() || current != null) {
            // Traverse to the leftmost node
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

    private static void performRecursiveInorder(BinaryTree.Node node, List<Integer> result) {
        if (node == null) {
            return;
        }

        performRecursiveInorder(node.left, result);
        result.add(node.data);
        performRecursiveInorder(node.right, result);
    }
}