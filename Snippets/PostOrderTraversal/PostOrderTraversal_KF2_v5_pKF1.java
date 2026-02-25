package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public final class PostOrderTraversal {

    private PostOrderTraversal() {
    }

    public static List<Integer> recursivePostOrder(BinaryTree.Node root) {
        List<Integer> traversalResult = new ArrayList<>();
        performRecursivePostOrder(root, traversalResult);
        return traversalResult;
    }

    public static List<Integer> iterativePostOrder(BinaryTree.Node root) {
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

    private static void performRecursivePostOrder(BinaryTree.Node currentNode, List<Integer> traversalResult) {
        if (currentNode == null) {
            return;
        }
        performRecursivePostOrder(currentNode.left, traversalResult);
        performRecursivePostOrder(currentNode.right, traversalResult);
        traversalResult.add(currentNode.data);
    }
}