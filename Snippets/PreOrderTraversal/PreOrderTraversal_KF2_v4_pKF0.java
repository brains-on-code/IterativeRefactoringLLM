package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public final class PreOrderTraversal {

    private PreOrderTraversal() {
        // Utility class; prevent instantiation
    }

    public static List<Integer> recursivePreOrder(BinaryTree.Node root) {
        List<Integer> result = new ArrayList<>();
        recursivePreOrder(root, result);
        return result;
    }

    public static List<Integer> iterativePreOrder(BinaryTree.Node root) {
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

    private static void recursivePreOrder(BinaryTree.Node node, List<Integer> result) {
        if (node == null) {
            return;
        }

        result.add(node.data);
        recursivePreOrder(node.left, result);
        recursivePreOrder(node.right, result);
    }
}