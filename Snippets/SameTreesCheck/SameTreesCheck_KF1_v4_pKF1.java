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

        Deque<BinaryTree.Node> firstTreeNodes = new ArrayDeque<>();
        Deque<BinaryTree.Node> secondTreeNodes = new ArrayDeque<>();
        firstTreeNodes.add(firstRoot);
        secondTreeNodes.add(secondRoot);

        while (!firstTreeNodes.isEmpty() && !secondTreeNodes.isEmpty()) {
            BinaryTree.Node firstCurrent = firstTreeNodes.poll();
            BinaryTree.Node secondCurrent = secondTreeNodes.poll();

            if (!areNodesEqual(firstCurrent, secondCurrent)) {
                return false;
            }

            if (firstCurrent != null) {
                if (!areNodesEqual(firstCurrent.left, secondCurrent.left)) {
                    return false;
                }
                if (firstCurrent.left != null) {
                    firstTreeNodes.add(firstCurrent.left);
                    secondTreeNodes.add(secondCurrent.left);
                }

                if (!areNodesEqual(firstCurrent.right, secondCurrent.right)) {
                    return false;
                }
                if (firstCurrent.right != null) {
                    firstTreeNodes.add(firstCurrent.right);
                    secondTreeNodes.add(secondCurrent.right);
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