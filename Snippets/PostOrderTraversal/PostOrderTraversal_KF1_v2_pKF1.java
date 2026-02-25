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
        postOrderTraversal(root, traversalResult);
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

    private static void postOrderTraversal(BinaryTree.Node node, List<Integer> traversalResult) {
        if (node == null) {
            return;
        }
        postOrderTraversal(node.left, traversalResult);
        postOrderTraversal(node.right, traversalResult);
        traversalResult.add(node.data);
    }
}