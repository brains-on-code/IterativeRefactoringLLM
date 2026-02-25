package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public final class InorderTraversal {

    private InorderTraversal() {
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

    private static void performRecursiveInorder(BinaryTree.Node currentNode, List<Integer> inorderTraversalResult) {
        if (currentNode == null) {
            return;
        }
        performRecursiveInorder(currentNode.left, inorderTraversalResult);
        inorderTraversalResult.add(currentNode.data);
        performRecursiveInorder(currentNode.right, inorderTraversalResult);
    }
}