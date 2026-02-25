package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Utility methods for traversing binary trees.
 */
public final class TreeTraversals {

    private TreeTraversals() {
        // Utility class; prevent instantiation
    }

    public static List<Integer> postOrderTraversal(BinaryTree.Node root) {
        List<Integer> traversalResult = new ArrayList<>();
        performPostOrderTraversal(root, traversalResult);
        return traversalResult;
    }

    public static List<Integer> reverseLevelOrderTraversal(BinaryTree.Node root) {
        LinkedList<Integer> traversalResult = new LinkedList<>();
        if (root == null) {
            return traversalResult;
        }

        Deque<BinaryTree.Node> nodeStack = new ArrayDeque<>();
        nodeStack.push(root);

        while (!nodeStack.isEmpty()) {
            BinaryTree.Node currentNode = nodeStack.pop();
            traversalResult.addFirst(currentNode.data);

            if (currentNode.left != null) {
                nodeStack.push(currentNode.left);
            }
            if (currentNode.right != null) {
                nodeStack.push(currentNode.right);
            }
        }

        return traversalResult;
    }

    private static void performPostOrderTraversal(BinaryTree.Node currentNode, List<Integer> traversalResult) {
        if (currentNode == null) {
            return;
        }
        performPostOrderTraversal(currentNode.left, traversalResult);
        performPostOrderTraversal(currentNode.right, traversalResult);
        traversalResult.add(currentNode.data);
    }
}