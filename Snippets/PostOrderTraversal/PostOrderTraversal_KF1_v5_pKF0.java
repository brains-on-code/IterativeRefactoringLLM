package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Returns the post-order traversal of the binary tree rooted at {@code root}.
     */
    public static List<Integer> postOrderTraversal(BinaryTree.Node root) {
        List<Integer> result = new ArrayList<>();
        traversePostOrder(root, result);
        return result;
    }

    /**
     * Returns the reverse level-order traversal of the binary tree rooted at {@code root}.
     */
    public static List<Integer> reverseLevelOrderTraversal(BinaryTree.Node root) {
        LinkedList<Integer> result = new LinkedList<>();
        if (root == null) {
            return result;
        }

        Deque<BinaryTree.Node> stack = new ArrayDeque<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            BinaryTree.Node current = stack.pop();
            result.addFirst(current.data);

            if (current.left != null) {
                stack.push(current.left);
            }
            if (current.right != null) {
                stack.push(current.right);
            }
        }

        return result;
    }

    private static void traversePostOrder(BinaryTree.Node node, List<Integer> result) {
        if (node == null) {
            return;
        }
        traversePostOrder(node.left, result);
        traversePostOrder(node.right, result);
        result.add(node.data);
    }
}