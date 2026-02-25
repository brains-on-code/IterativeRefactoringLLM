package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Utility methods for traversing binary trees.
 */
public final class TreeTraversals {

    private TreeTraversals() {
        // Utility class; prevent instantiation
    }

    public static List<Integer> postOrderTraversal(BinaryTree.Node root) {
        List<Integer> result = new ArrayList<>();
        postOrderTraversalHelper(root, result);
        return result;
    }

    public static List<Integer> reverseLevelOrderTraversal(BinaryTree.Node root) {
        LinkedList<Integer> result = new LinkedList<>();
        if (root == null) {
            return result;
        }

        Deque<BinaryTree.Node> stack = new ArrayDeque<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            BinaryTree.Node node = stack.pop();
            result.addFirst(node.data);

            if (node.left != null) {
                stack.push(node.left);
            }
            if (node.right != null) {
                stack.push(node.right);
            }
        }

        return result;
    }

    private static void postOrderTraversalHelper(BinaryTree.Node node, List<Integer> result) {
        if (node == null) {
            return;
        }
        postOrderTraversalHelper(node.left, result);
        postOrderTraversalHelper(node.right, result);
        result.add(node.data);
    }
}