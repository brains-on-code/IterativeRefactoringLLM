package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public final class PostOrderTraversal {

    private PostOrderTraversal() {
        // Utility class; prevent instantiation
    }

    /**
     * Performs a recursive post-order traversal of the binary tree.
     *
     * @param root the root node of the binary tree
     * @return a list of node values in post-order (left, right, root)
     */
    public static List<Integer> recursivePostOrder(BinaryTree.Node root) {
        List<Integer> result = new ArrayList<>();
        recursivePostOrder(root, result);
        return result;
    }

    /**
     * Performs an iterative post-order traversal of the binary tree.
     *
     * @param root the root node of the binary tree
     * @return a list of node values in post-order (left, right, root)
     */
    public static List<Integer> iterativePostOrder(BinaryTree.Node root) {
        LinkedList<Integer> result = new LinkedList<>();
        if (root == null) {
            return result;
        }

        Deque<BinaryTree.Node> stack = new ArrayDeque<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            BinaryTree.Node current = stack.pop();
            // Add to the front to simulate post-order when using a modified pre-order traversal
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

    private static void recursivePostOrder(BinaryTree.Node node, List<Integer> result) {
        if (node == null) {
            return;
        }

        recursivePostOrder(node.left, result);
        recursivePostOrder(node.right, result);
        result.add(node.data);
    }
}