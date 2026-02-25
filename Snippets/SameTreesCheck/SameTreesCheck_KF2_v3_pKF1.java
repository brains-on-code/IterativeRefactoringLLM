package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.Deque;

public final class SameTreesCheck {

    private SameTreesCheck() {
    }

    public static boolean check(BinaryTree.Node firstRoot, BinaryTree.Node secondRoot) {
        if (firstRoot == null && secondRoot == null) {
            return true;
        }
        if (firstRoot == null || secondRoot == null) {
            return false;
        }

        Deque<BinaryTree.Node> firstNodesToVisit = new ArrayDeque<>();
        Deque<BinaryTree.Node> secondNodesToVisit = new ArrayDeque<>();
        firstNodesToVisit.add(firstRoot);
        secondNodesToVisit.add(secondRoot);

        while (!firstNodesToVisit.isEmpty() && !secondNodesToVisit.isEmpty()) {
            BinaryTree.Node firstCurrentNode = firstNodesToVisit.poll();
            BinaryTree.Node secondCurrentNode = secondNodesToVisit.poll();

            if (!haveSameValue(firstCurrentNode, secondCurrentNode)) {
                return false;
            }

            if (firstCurrentNode != null) {
                if (!haveSameValue(firstCurrentNode.left, secondCurrentNode.left)) {
                    return false;
                }
                if (firstCurrentNode.left != null) {
                    firstNodesToVisit.add(firstCurrentNode.left);
                    secondNodesToVisit.add(secondCurrentNode.left);
                }

                if (!haveSameValue(firstCurrentNode.right, secondCurrentNode.right)) {
                    return false;
                }
                if (firstCurrentNode.right != null) {
                    firstNodesToVisit.add(firstCurrentNode.right);
                    secondNodesToVisit.add(secondCurrentNode.right);
                }
            }
        }
        return true;
    }

    private static boolean haveSameValue(BinaryTree.Node firstNode, BinaryTree.Node secondNode) {
        if (firstNode == null && secondNode == null) {
            return true;
        }
        if (firstNode == null || secondNode == null) {
            return false;
        }
        return firstNode.data == secondNode.data;
    }
}