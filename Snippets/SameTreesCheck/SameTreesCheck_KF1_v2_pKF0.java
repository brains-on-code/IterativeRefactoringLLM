package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Utility class for comparing binary trees.
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Compares two binary trees for structural and data equality using
     * iterative breadth-first traversal.
     *
     * @param root1 root of the first tree
     * @param root2 root of the second tree
     * @return {@code true} if both trees are structurally identical and contain
     *         the same data in corresponding nodes; {@code false} otherwise
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

            if (!nodesEqual(node1, node2)) {
                return false;
            }

            if (!childrenEqualAndEnqueue(node1.left, node2.left, queue1, queue2)) {
                return false;
            }

            if (!childrenEqualAndEnqueue(node1.right, node2.right, queue1, queue2)) {
                return false;
            }
        }

        return queue1.isEmpty() && queue2.isEmpty();
    }

    private static boolean childrenEqualAndEnqueue(
            BinaryTree.Node child1,
            BinaryTree.Node child2,
            Deque<BinaryTree.Node> queue1,
            Deque<BinaryTree.Node> queue2
    ) {
        if (!nodesEqual(child1, child2)) {
            return false;
        }
        if (child1 != null) {
            queue1.add(child1);
            queue2.add(child2);
        }
        return true;
    }

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