package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.Deque;

public final class SameTreesCheck {

    private SameTreesCheck() {
        // Utility class; prevent instantiation
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

            if (!nodesAreEqual(firstNode, secondNode)) {
                return false;
            }

            if (!nodesAreEqual(firstNode.left, secondNode.left)) {
                return false;
            }
            if (firstNode.left != null) {
                firstQueue.add(firstNode.left);
                secondQueue.add(secondNode.left);
            }

            if (!nodesAreEqual(firstNode.right, secondNode.right)) {
                return false;
            }
            if (firstNode.right != null) {
                firstQueue.add(firstNode.right);
                secondQueue.add(secondNode.right);
            }
        }

        return firstQueue.isEmpty() && secondQueue.isEmpty();
    }

    private static boolean nodesAreEqual(BinaryTree.Node firstNode, BinaryTree.Node secondNode) {
        if (firstNode == null || secondNode == null) {
            return firstNode == secondNode;
        }
        return firstNode.data == secondNode.data;
    }
}