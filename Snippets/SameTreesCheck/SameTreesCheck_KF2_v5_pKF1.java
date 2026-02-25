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

        Deque<BinaryTree.Node> firstTreeNodes = new ArrayDeque<>();
        Deque<BinaryTree.Node> secondTreeNodes = new ArrayDeque<>();
        firstTreeNodes.add(firstRoot);
        secondTreeNodes.add(secondRoot);

        while (!firstTreeNodes.isEmpty() && !secondTreeNodes.isEmpty()) {
            BinaryTree.Node firstCurrentNode = firstTreeNodes.poll();
            BinaryTree.Node secondCurrentNode = secondTreeNodes.poll();

            if (!nodesHaveSameValue(firstCurrentNode, secondCurrentNode)) {
                return false;
            }

            if (firstCurrentNode != null) {
                if (!nodesHaveSameValue(firstCurrentNode.left, secondCurrentNode.left)) {
                    return false;
                }
                if (firstCurrentNode.left != null) {
                    firstTreeNodes.add(firstCurrentNode.left);
                    secondTreeNodes.add(secondCurrentNode.left);
                }

                if (!nodesHaveSameValue(firstCurrentNode.right, secondCurrentNode.right)) {
                    return false;
                }
                if (firstCurrentNode.right != null) {
                    firstTreeNodes.add(firstCurrentNode.right);
                    secondTreeNodes.add(secondCurrentNode.right);
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