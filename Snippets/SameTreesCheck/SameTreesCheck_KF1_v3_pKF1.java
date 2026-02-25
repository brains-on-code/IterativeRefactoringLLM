package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.Deque;

public final class TreeEqualityChecker {

    private TreeEqualityChecker() {
    }

    public static boolean areTreesIdentical(BinaryTree.Node firstTreeRoot, BinaryTree.Node secondTreeRoot) {
        if (firstTreeRoot == null && secondTreeRoot == null) {
            return true;
        }
        if (firstTreeRoot == null || secondTreeRoot == null) {
            return false;
        }

        Deque<BinaryTree.Node> firstTreeQueue = new ArrayDeque<>();
        Deque<BinaryTree.Node> secondTreeQueue = new ArrayDeque<>();
        firstTreeQueue.add(firstTreeRoot);
        secondTreeQueue.add(secondTreeRoot);

        while (!firstTreeQueue.isEmpty() && !secondTreeQueue.isEmpty()) {
            BinaryTree.Node firstCurrentNode = firstTreeQueue.poll();
            BinaryTree.Node secondCurrentNode = secondTreeQueue.poll();

            if (!areNodesEqual(firstCurrentNode, secondCurrentNode)) {
                return false;
            }

            if (firstCurrentNode != null) {
                if (!areNodesEqual(firstCurrentNode.left, secondCurrentNode.left)) {
                    return false;
                }
                if (firstCurrentNode.left != null) {
                    firstTreeQueue.add(firstCurrentNode.left);
                    secondTreeQueue.add(secondCurrentNode.left);
                }

                if (!areNodesEqual(firstCurrentNode.right, secondCurrentNode.right)) {
                    return false;
                }
                if (firstCurrentNode.right != null) {
                    firstTreeQueue.add(firstCurrentNode.right);
                    secondTreeQueue.add(secondCurrentNode.right);
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