package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Utility class for comparing binary trees.
 *
 * <p>Provides a method to check whether two binary trees are identical in both
 * structure and node values.</p>
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation.
    }

    /**
     * Compares two binary trees for structural and value equality using
     * iterative breadth-first traversal.
     *
     * @param root1 the root of the first tree
     * @param root2 the root of the second tree
     * @return {@code true} if both trees are identical in structure and node values,
     *         {@code false} otherwise
     */
    public static boolean method1(BinaryTree.Node root1, BinaryTree.Node root2) {
        if (root1 == null && root2 == null) {
            return true;
        }
        if (root1 == null || root2 == null) {
            return false;
        }

        Deque<BinaryTree.Node> queue1 = new ArrayDeque<>();
        Deque<BinaryTree.Node> queue2 = new ArrayDeque<>();
        queue1.add(root1);
        queue2.add(root2);

        while (!queue1.isEmpty() && !queue2.isEmpty()) {
            BinaryTree.Node node1 = queue1.poll();
            BinaryTree.Node node2 = queue2.poll();

            // Check current nodes
            if (!nodesEqual(node1, node2)) {
                return false;
            }

            if (node1 != null) {
                // Check and enqueue left children
                if (!nodesEqual(node1.left, node2.left)) {
                    return false;
                }
                if (node1.left != null) {
                    queue1.add(node1.left);
                    queue2.add(node2.left);
                }

                // Check and enqueue right children
                if (!nodesEqual(node1.right, node2.right)) {
                    return false;
                }
                if (node1.right != null) {
                    queue1.add(node1.right);
                    queue2.add(node2.right);
                }
            }
        }
        return true;
    }

    /**
     * Checks whether two nodes are both null or both non-null with equal data.
     *
     * @param node1 the first node
     * @param node2 the second node
     * @return {@code true} if both nodes are null, or both non-null with equal data;
     *         {@code false} otherwise
     */
    private static boolean nodesEqual(BinaryTree.Node node1, BinaryTree.Node node2) {
        if (node1 == null && node2 == null) {
            return true;
        }
        if (node1 == null || node2 == null) {
            return false;
        }
        return node1.data == node2.data;
    }
}