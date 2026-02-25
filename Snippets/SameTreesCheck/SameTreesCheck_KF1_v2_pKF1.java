package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.Deque;

public final class TreeEqualityChecker {

    private TreeEqualityChecker() {
    }

    public static boolean areTreesIdentical(BinaryTree.Node firstRoot, BinaryTree.Node secondRoot) {
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
            BinaryTree.Node firstCurrent = firstQueue.poll();
            BinaryTree.Node secondCurrent = secondQueue.poll();

            if (!areNodesEqual(firstCurrent, secondCurrent)) {
                return false;
            }

            if (firstCurrent != null) {
                if (!areNodesEqual(firstCurrent.left, secondCurrent.left)) {
                    return false;
                }
                if (firstCurrent.left != null) {
                    firstQueue.add(firstCurrent.left);
                    secondQueue.add(secondCurrent.left);
                }

                if (!areNodesEqual(firstCurrent.right, secondCurrent.right)) {
                    return false;
                }
                if (firstCurrent.right != null) {
                    firstQueue.add(firstCurrent.right);
                    secondQueue.add(secondCurrent.right);
                }
            }
        }
        return true;
    }

    private static boolean areNodesEqual(BinaryTree.Node firstNode, BinaryTree.Node secondNode) {
        if (firstNode == null && secondNode == null) {
            return true;
        }
        if (firstNode == null || secondNode == null) {
            return false;
        }
        return firstNode.data == secondNode.data;
    }
}