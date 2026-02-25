package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public final class PostOrderTraversal {

    private PostOrderTraversal() {
        // Utility class; prevent instantiation
    }

    public static List<Integer> recursivePostOrder(BinaryTree.Node root) {
        List<Integer> result = new ArrayList<>();
        traverseRecursively(root, result);
        return result;
    }

    public static List<Integer> iterativePostOrder(BinaryTree.Node root) {
        LinkedList<Integer> result = new LinkedList<>();
        if (root == null) {
            return result;
        }

        Deque<BinaryTree.Node> stack = new ArrayDeque<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            BinaryTree.Node currentNode = stack.pop();
            result.addFirst(currentNode.data);

            if (currentNode.left != null) {
                stack.push(currentNode.left);
            }
            if (currentNode.right != null) {
                stack.push(currentNode.right);
            }
        }

        return result;
    }

    private static void traverseRecursively(BinaryTree.Node node, List<Integer> result) {
        if (node == null) {
            return;
        }

        traverseRecursively(node.left, result);
        traverseRecursively(node.right, result);
        result.add(node.data);
    }
}