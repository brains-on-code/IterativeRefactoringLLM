package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.Deque;

public final class SameTreesCheck {

    private SameTreesCheck() {}

    /**
     * Checks whether two binary trees are structurally identical and contain
     * the same values in corresponding nodes.
     *
     * @param firstRoot  root of the first tree
     * @param secondRoot root of the second tree
     * @return {@code true} if both trees are the same, {@code false} otherwise
     */
    public static boolean check(BinaryTree.Node firstRoot, BinaryTree.Node secondRoot) {
        if (firstRoot == null && secondRoot == null) {
            return true;
        }
        if (firstRoot == null || secondRoot == null) {
            return false;
        }

        Deque<BinaryTree.Node> firstQueue = new ArrayDeque<>();
        Deque<BinaryTree.Node> secondQueue = new ArrayDeque<>();
        firstQueue.add(firstRoot);
        secondQueue.add(secondRoot);

        while (!firstQueue.isEmpty() && !secondQueue.isEmpty()) {
            BinaryTree.Node firstNode = firstQueue.poll();
            BinaryTree.Node secondNode = secondQueue.poll();

            if (!nodesEqual(firstNode, secondNode)) {
                return false;
            }

            if (!nodesEqual(firstNode.left, secondNode.left)) {
                return false;
            }
            if (firstNode.left != null) {
                firstQueue.add(firstNode.left);
                secondQueue.add(secondNode.left);
            }

            if (!nodesEqual(firstNode.right, secondNode.right)) {
                return false;
            }
            if (firstNode.right != null) {
                firstQueue.add(firstNode.right);
                secondQueue.add(secondNode.right);
            }
        }

        return firstQueue.isEmpty() && secondQueue.isEmpty();
    }

    /**
     * Returns {@code true} if both nodes are {@code null}, or both are
     * non-{@code null} and contain the same data value.
     *
     * @param firstNode  first node to compare
     * @param secondNode second node to compare
     * @return {@code true} if the nodes are equal as defined above
     */
    private static boolean nodesEqual(BinaryTree.Node firstNode, BinaryTree.Node secondNode) {
        if (firstNode == null && secondNode == null) {
            return true;
        }
        if (firstNode == null || secondNode == null) {
            return false;
        }
        return firstNode.data == secondNode.data;
    }
}