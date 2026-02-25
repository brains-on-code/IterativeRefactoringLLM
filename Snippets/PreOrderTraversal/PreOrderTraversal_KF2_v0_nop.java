package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;


public final class PreOrderTraversal {
    private PreOrderTraversal() {
    }
    public static List<Integer> recursivePreOrder(BinaryTree.Node root) {
        List<Integer> result = new ArrayList<>();
        recursivePreOrder(root, result);
        return result;
    }

    public static List<Integer> iterativePreOrder(BinaryTree.Node root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Deque<BinaryTree.Node> stack = new LinkedList<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            BinaryTree.Node node = stack.pop();
            result.add(node.data);
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }

        return result;
    }

    private static void recursivePreOrder(BinaryTree.Node root, List<Integer> result) {
        if (root == null) {
            return;
        }
        result.add(root.data);
        recursivePreOrder(root.left, result);
        recursivePreOrder(root.right, result);
    }
}
