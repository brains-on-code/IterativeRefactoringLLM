package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public final class PreOrderTraversal {

    private PreOrderTraversal() {
        // Utility class; prevent instantiation
    }

    public static List<Integer> recursivePreOrder(BinaryTree.Node rootNode) {
        List<Integer> traversalResult = new ArrayList<>();
        performRecursivePreOrder(rootNode, traversalResult);
        return traversalResult;
    }

    public static List<Integer> iterativePreOrder(BinaryTree.Node rootNode) {
        List<Integer> traversalResult = new ArrayList<>();
        if (rootNode == null) {
            return traversalResult;
        }

        Deque<BinaryTree.Node> nodeStack = new LinkedList<>();
        nodeStack.push(rootNode);

        while (!nodeStack.isEmpty()) {
            BinaryTree.Node currentNode = nodeStack.pop();
            traversalResult.add(currentNode.data);

            if (currentNode.right != null) {
                nodeStack.push(currentNode.right);
            }
            if (currentNode.left != null) {
                nodeStack.push(currentNode.left);
            }
        }

        return traversalResult;
    }

    private static void performRecursivePreOrder(BinaryTree.Node currentNode, List<Integer> traversalResult) {
        if (currentNode == null) {
            return;
        }

        traversalResult.add(currentNode.data);
        performRecursivePreOrder(currentNode.left, traversalResult);
        performRecursivePreOrder(currentNode.right, traversalResult);
    }
}