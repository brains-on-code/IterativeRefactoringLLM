package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;


public final class InorderTraversal {
    private InorderTraversal() {
    }
    public static List<Integer> recursiveInorder(BinaryTree.Node root) {
        List<Integer> result = new ArrayList<>();
        recursiveInorder(root, result);
        return result;
    }

    public static List<Integer> iterativeInorder(BinaryTree.Node root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Deque<BinaryTree.Node> stack = new ArrayDeque<>();
        while (!stack.isEmpty() || root != null) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            result.add(root.data);
            root = root.right;
        }
        return result;
    }

    private static void recursiveInorder(BinaryTree.Node root, List<Integer> result) {
        if (root == null) {
            return;
        }
        recursiveInorder(root.left, result);
        result.add(root.data);
        recursiveInorder(root.right, result);
    }
}
