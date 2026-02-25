package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public final class InorderTraversal {

    private InorderTraversal() {
        // Utility class; prevent instantiation
    }

    public static List<Integer> recursiveInorder(BinaryTree.Node root) {
        List<Integer> traversalResult = new ArrayList<>();
        traverseRecursively(root, traversalResult);
        return traversalResult;
    }

    public static List<Integer> iterativeInorder(BinaryTree.Node root) {
        List<Integer> traversalResult = new ArrayList<>();
        Deque<BinaryTree.Node> nodeStack = new ArrayDeque<>();
        BinaryTree.Node currentNode = root;

        while (!nodeStack.isEmpty() || currentNode != null) {
            moveToLeftmostNode(currentNode, nodeStack);

            currentNode = nodeStack.pop();
            traversalResult.add(currentNode.data);
            currentNode = currentNode.right;
        }

        return traversalResult;
    }

    private static void traverseRecursively(BinaryTree.Node node, List<Integer> traversalResult) {
        if (node == null) {
            return;
        }

        traverseRecursively(node.left, traversalResult);
        traversalResult.add(node.data);
        traverseRecursively(node.right, traversalResult);
    }

    private static void moveToLeftmostNode(BinaryTree.Node node, Deque<BinaryTree.Node> nodeStack) {
        while (node != null) {
            nodeStack.push(node);
            node = node.left;
        }
    }
}