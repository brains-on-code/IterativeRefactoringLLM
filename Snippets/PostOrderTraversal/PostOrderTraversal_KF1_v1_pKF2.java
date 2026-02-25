package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Utility class providing postorder traversal implementations for a binary tree.
 *
 * <p>Both recursive and iterative approaches are provided. The iterative version
 * uses a modified preorder traversal and a deque to build the result in
 * postorder.</p>
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Returns the postorder traversal of the given binary tree using recursion.
     *
     * @param root the root node of the binary tree
     * @return a list of node values in postorder (left, right, root)
     */
    public static List<Integer> method3(BinaryTree.Node root) {
        List<Integer> result = new ArrayList<>();
        method3(root, result);
        return result;
    }

    /**
     * Returns the postorder traversal of the given binary tree using an
     * iterative approach.
     *
     * @param root the root node of the binary tree
     * @return a list of node values in postorder (left, right, root)
     */
    public static List<Integer> method2(BinaryTree.Node root) {
        LinkedList<Integer> result = new LinkedList<>();
        if (root == null) {
            return result;
        }

        Deque<BinaryTree.Node> stack = new ArrayDeque<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            BinaryTree.Node current = stack.pop();
            // Add to the front to simulate postorder when using root-right-left traversal
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

    /**
     * Helper method for recursive postorder traversal.
     *
     * @param node   current node
     * @param result list collecting the traversal result
     */
    private static void method3(BinaryTree.Node node, List<Integer> result) {
        if (node == null) {
            return;
        }
        method3(node.left, result);
        method3(node.right, result);
        result.add(node.data);
    }
}