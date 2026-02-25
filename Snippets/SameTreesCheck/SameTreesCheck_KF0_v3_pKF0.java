package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Utility class for checking whether two binary trees are structurally identical
 * and contain the same values.
 */
public final class SameTreesCheck {

    private SameTreesCheck() {
        // Utility class
    }

    public static boolean check(BinaryTree.Node firstRoot, BinaryTree.Node secondRoot) {
        if (firstRoot == null || secondRoot == null) {
            return firstRoot == secondRoot;
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

    private static boolean nodesEqual(BinaryTree.Node firstNode, BinaryTree.Node secondNode) {
        if (firstNode == null || secondNode == null) {
            return firstNode == secondNode;
        }
        return firstNode.data == secondNode.data;
    }
}