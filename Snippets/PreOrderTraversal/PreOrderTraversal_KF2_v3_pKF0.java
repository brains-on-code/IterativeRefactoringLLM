package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public final class PreOrderTraversal {

    private PreOrderTraversal() {
        // Utility class; prevent instantiation
    }

    public static List<Integer> recursivePreOrder(BinaryTree.Node root) {
        List<Integer> traversalResult = new ArrayList<>();
        performRecursivePreOrder(root, traversalResult);
        return traversalResult;
    }

    public static List<Integer> iterativePreOrder(BinaryTree.Node root) {
        List<Integer> traversalResult = new ArrayList<>();
        if (root == null) {
            return traversalResult;
        }

        Deque<BinaryTree.Node> nodeStack = new LinkedList<>();
        nodeStack.push(root);

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

    private static void performRecursivePreOrder(BinaryTree.Node node, List<Integer> traversalResult) {
        if (node == null) {
            return;
        }

        traversalResult.add(node.data);
        performRecursivePreOrder(node.left, traversalResult);
        performRecursivePreOrder(node.right, traversalResult);
    }
}