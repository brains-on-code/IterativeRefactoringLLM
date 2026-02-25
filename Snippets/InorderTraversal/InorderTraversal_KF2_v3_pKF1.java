package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public final class InorderTraversal {

    private InorderTraversal() {
    }

    public static List<Integer> recursiveInorder(BinaryTree.Node root) {
        List<Integer> traversalResult = new ArrayList<>();
        recursiveInorder(root, traversalResult);
        return traversalResult;
    }

    public static List<Integer> iterativeInorder(BinaryTree.Node root) {
        List<Integer> traversalResult = new ArrayList<>();
        if (root == null) {
            return traversalResult;
        }

        Deque<BinaryTree.Node> nodeStack = new ArrayDeque<>();
        BinaryTree.Node currentNode = root;

        while (!nodeStack.isEmpty() || currentNode != null) {
            while (currentNode != null) {
                nodeStack.push(currentNode);
                currentNode = currentNode.left;
            }
            currentNode = nodeStack.pop();
            traversalResult.add(currentNode.data);
            currentNode = currentNode.right;
        }
        return traversalResult;
    }

    private static void recursiveInorder(BinaryTree.Node node, List<Integer> traversalResult) {
        if (node == null) {
            return;
        }
        recursiveInorder(node.left, traversalResult);
        traversalResult.add(node.data);
        recursiveInorder(node.right, traversalResult);
    }
}