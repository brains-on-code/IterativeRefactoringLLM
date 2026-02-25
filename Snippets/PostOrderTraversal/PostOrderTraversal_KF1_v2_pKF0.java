package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Returns the post-order traversal of the binary tree rooted at {@code root}.
     */
    public static List<Integer> postOrderTraversal(BinaryTree.Node root) {
        List<Integer> traversalResult = new ArrayList<>();
        traversePostOrder(root, traversalResult);
        return traversalResult;
    }

    /**
     * Returns the reverse level-order traversal of the binary tree rooted at {@code root}.
     */
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

    private static void traversePostOrder(BinaryTree.Node node, List<Integer> traversalResult) {
        if (node == null) {
            return;
        }
        traversePostOrder(node.left, traversalResult);
        traversePostOrder(node.right, traversalResult);
        traversalResult.add(node.data);
    }
}