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
        if (root1 == root2) {
            return true;
        }
        if (root1 == null || root2 == null) {
            return false;
        }

        Deque<BinaryTree.Node> firstQueue = new ArrayDeque<>();
        Deque<BinaryTree.Node> secondQueue = new ArrayDeque<>();
        firstQueue.add(root1);
        secondQueue.add(root2);

        while (!firstQueue.isEmpty() && !secondQueue.isEmpty()) {
            BinaryTree.Node firstNode = firstQueue.poll();
            BinaryTree.Node secondNode = secondQueue.poll();

            if (!nodesEqual(firstNode, secondNode)) {
                return false;
            }

            if (!enqueueChildrenIfEqual(firstNode.left, secondNode.left, firstQueue, secondQueue)) {
                return false;
            }

            if (!enqueueChildrenIfEqual(firstNode.right, secondNode.right, firstQueue, secondQueue)) {
                return false;
            }
        }

        return firstQueue.isEmpty() && secondQueue.isEmpty();
    }

    private static boolean enqueueChildrenIfEqual(
            BinaryTree.Node firstChild,
            BinaryTree.Node secondChild,
            Deque<BinaryTree.Node> firstQueue,
            Deque<BinaryTree.Node> secondQueue
    ) {
        if (!nodesEqual(firstChild, secondChild)) {
            return false;
        }
        if (firstChild != null) {
            firstQueue.add(firstChild);
            secondQueue.add(secondChild);
        }
        return true;
    }

    private static boolean nodesEqual(BinaryTree.Node firstNode, BinaryTree.Node secondNode) {
        if (firstNode == secondNode) {
            return true;
        }
        if (firstNode == null || secondNode == null) {
            return false;
        }
        return firstNode.data == secondNode.data;
    }
}