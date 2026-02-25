package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Utility class for checking whether two binary trees are structurally identical
 * and contain the same values.
 */
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

            if (!haveSameValue(firstNode, secondNode)) {
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

    private static boolean haveSameValue(BinaryTree.Node firstNode, BinaryTree.Node secondNode) {
        if (firstNode == null || secondNode == null) {
            return firstNode == secondNode;
        }
        return firstNode.data == secondNode.data;
    }

    private static boolean enqueueChildrenIfEqual(
        BinaryTree.Node firstChild,
        BinaryTree.Node secondChild,
        Deque<BinaryTree.Node> firstQueue,
        Deque<BinaryTree.Node> secondQueue
    ) {
        if (!haveSameValue(firstChild, secondChild)) {
            return false;
        }
        if (firstChild != null) {
            firstQueue.add(firstChild);
            secondQueue.add(secondChild);
        }
        return true;
    }
}