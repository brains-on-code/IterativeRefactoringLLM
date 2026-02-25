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

            if (!processChildPair(firstNode.left, secondNode.left, firstQueue, secondQueue)) {
                return false;
            }

            if (!processChildPair(firstNode.right, secondNode.right, firstQueue, secondQueue)) {
                return false;
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

    private static boolean processChildPair(
            BinaryTree.Node firstChild,
            BinaryTree.Node secondChild,
            Deque<BinaryTree.Node> firstQueue,
            Deque<BinaryTree.Node> secondQueue) {

        if (firstChild == null || secondChild == null) {
            return firstChild == secondChild;
        }

        if (!nodesAreEqual(firstChild, secondChild)) {
            return false;
        }

        firstQueue.add(firstChild);
        secondQueue.add(secondChild);
        return true;
    }
}