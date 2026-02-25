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

        Deque<BinaryTree.Node> firstTreeQueue = new ArrayDeque<>();
        Deque<BinaryTree.Node> secondTreeQueue = new ArrayDeque<>();
        firstTreeQueue.add(firstRoot);
        secondTreeQueue.add(secondRoot);

        while (!firstTreeQueue.isEmpty() && !secondTreeQueue.isEmpty()) {
            BinaryTree.Node firstCurrent = firstTreeQueue.poll();
            BinaryTree.Node secondCurrent = secondTreeQueue.poll();

            if (!nodesHaveSameValue(firstCurrent, secondCurrent)) {
                return false;
            }

            if (firstCurrent != null) {
                if (!nodesHaveSameValue(firstCurrent.left, secondCurrent.left)) {
                    return false;
                }
                if (firstCurrent.left != null) {
                    firstTreeQueue.add(firstCurrent.left);
                    secondTreeQueue.add(secondCurrent.left);
                }

                if (!nodesHaveSameValue(firstCurrent.right, secondCurrent.right)) {
                    return false;
                }
                if (firstCurrent.right != null) {
                    firstTreeQueue.add(firstCurrent.right);
                    secondTreeQueue.add(secondCurrent.right);
                }
            }
        }
        return true;
    }

    private static boolean nodesHaveSameValue(BinaryTree.Node firstNode, BinaryTree.Node secondNode) {
        if (firstNode == null && secondNode == null) {
            return true;
        }
        if (firstNode == null || secondNode == null) {
            return false;
        }
        return firstNode.data == secondNode.data;
    }
}