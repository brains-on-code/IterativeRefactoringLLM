package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Inorder traversal of a binary tree: LEFT -> ROOT -> RIGHT.
 *
 * Recursive: O(n) time, O(n) space (call stack), where n is the number of nodes.
 * Iterative: O(n) time, O(h) space, where h is the height of the tree.
 */
public final class InorderTraversal {

    private InorderTraversal() {
        // Utility class; prevent instantiation
    }

    public static List<Integer> recursiveInorder(BinaryTree.Node root) {
        List<Integer> traversalResult = new ArrayList<>();
        performRecursiveInorder(root, traversalResult);
        return traversalResult;
    }

    public static List<Integer> iterativeInorder(BinaryTree.Node root) {
        List<Integer> traversalResult = new ArrayList<>();
        if (root == null) {
            return traversalResult;
        }

        Deque<BinaryTree.Node> nodeStack = new ArrayDeque<>();
        BinaryTree.Node currentNode = root;

        while (currentNode != null || !nodeStack.isEmpty()) {
            pushLeftBranch(currentNode, nodeStack);

            BinaryTree.Node visitedNode = nodeStack.pop();
            traversalResult.add(visitedNode.data);

            currentNode = visitedNode.right;
        }

        return traversalResult;
    }

    private static void performRecursiveInorder(BinaryTree.Node node, List<Integer> traversalResult) {
        if (node == null) {
            return;
        }

        performRecursiveInorder(node.left, traversalResult);
        traversalResult.add(node.data);
        performRecursiveInorder(node.right, traversalResult);
    }

    private static void pushLeftBranch(BinaryTree.Node node, Deque<BinaryTree.Node> nodeStack) {
        BinaryTree.Node currentNode = node;
        while (currentNode != null) {
            nodeStack.push(currentNode);
            currentNode = currentNode.left;
        }
    }
}