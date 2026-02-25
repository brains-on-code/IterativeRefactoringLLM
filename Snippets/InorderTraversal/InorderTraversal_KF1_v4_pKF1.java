package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Utility class for inorder traversal of a binary tree.
 */
public final class InorderTraversal {

    private InorderTraversal() {
        // Utility class; prevent instantiation
    }

    public static List<Integer> recursiveInorder(BinaryTree.Node root) {
        List<Integer> inorderTraversalResult = new ArrayList<>();
        performRecursiveInorder(root, inorderTraversalResult);
        return inorderTraversalResult;
    }

    public static List<Integer> iterativeInorder(BinaryTree.Node root) {
        List<Integer> inorderTraversalResult = new ArrayList<>();
        if (root == null) {
            return inorderTraversalResult;
        }

        Deque<BinaryTree.Node> nodeStack = new ArrayDeque<>();
        BinaryTree.Node currentNode = root;

        while (!nodeStack.isEmpty() || currentNode != null) {
            while (currentNode != null) {
                nodeStack.push(currentNode);
                currentNode = currentNode.left;
            }
            currentNode = nodeStack.pop();
            inorderTraversalResult.add(currentNode.data);
            currentNode = currentNode.right;
        }
        return inorderTraversalResult;
    }

    private static void performRecursiveInorder(BinaryTree.Node node, List<Integer> inorderTraversalResult) {
        if (node == null) {
            return;
        }
        performRecursiveInorder(node.left, inorderTraversalResult);
        inorderTraversalResult.add(node.data);
        performRecursiveInorder(node.right, inorderTraversalResult);
    }
}