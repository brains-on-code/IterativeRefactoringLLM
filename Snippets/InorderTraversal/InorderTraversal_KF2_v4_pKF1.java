package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public final class InorderTraversal {

    private InorderTraversal() {
    }

    public static List<Integer> recursiveInorder(BinaryTree.Node root) {
        List<Integer> inorderValues = new ArrayList<>();
        recursiveInorder(root, inorderValues);
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

    private static void recursiveInorder(BinaryTree.Node node, List<Integer> inorderValues) {
        if (node == null) {
            return;
        }
        recursiveInorder(node.left, inorderValues);
        inorderValues.add(node.data);
        recursiveInorder(node.right, inorderValues);
    }
}