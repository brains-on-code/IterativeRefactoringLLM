package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public final class BoundaryTraversal {

    private BoundaryTraversal() {
        // Utility class
    }

    public static List<Integer> boundaryTraversal(BinaryTree.Node root) {
        List<Integer> boundary = new ArrayList<>();
        if (root == null) {
            return boundary;
        }

        if (!isLeaf(root)) {
            boundary.add(root.data);
        }

        addLeftBoundary(root, boundary);
        addLeaves(root, boundary);
        addRightBoundary(root, boundary);

        return boundary;
    }

    private static void addLeftBoundary(BinaryTree.Node root, List<Integer> boundary) {
        BinaryTree.Node current = getFirstChild(root);

        while (current != null) {
            if (!isLeaf(current)) {
                boundary.add(current.data);
            }
            current = getFirstChild(current);
        }
    }

    private static void addLeaves(BinaryTree.Node node, List<Integer> boundary) {
        if (node == null) {
            return;
        }
        if (isLeaf(node)) {
            boundary.add(node.data);
            return;
        }
        addLeaves(node.left, boundary);
        addLeaves(node.right, boundary);
    }

    private static void addRightBoundary(BinaryTree.Node root, List<Integer> boundary) {
        if (root.right == null && root.left != null) {
            return;
        }

        BinaryTree.Node current = root.right;
        List<Integer> temp = new ArrayList<>();

        while (current != null) {
            if (!isLeaf(current)) {
                temp.add(current.data);
            }
            current = getSecondChild(current);
        }

        for (int i = temp.size() - 1; i >= 0; i--) {
            boundary.add(temp.get(i));
        }
    }

    private static boolean isLeaf(BinaryTree.Node node) {
        return node.left == null && node.right == null;
    }

    private static BinaryTree.Node getFirstChild(BinaryTree.Node node) {
        return (node.left != null) ? node.left : node.right;
    }

    private static BinaryTree.Node getSecondChild(BinaryTree.Node node) {
        return (node.right != null) ? node.right : node.left;
    }

    public static List<Integer> iterativeBoundaryTraversal(BinaryTree.Node root) {
        List<Integer> boundary = new ArrayList<>();
        if (root == null) {
            return boundary;
        }

        if (!isLeaf(root)) {
            boundary.add(root.data);
        }

        BinaryTree.Node current = getFirstChild(root);
        while (current != null) {
            if (!isLeaf(current)) {
                boundary.add(current.data);
            }
            current = getFirstChild(current);
        }

        addLeaves(root, boundary);

        if (root.right == null && root.left != null) {
            return boundary;
        }

        current = root.right;
        Deque<Integer> stack = new LinkedList<>();
        while (current != null) {
            if (!isLeaf(current)) {
                stack.push(current.data);
            }
            current = getSecondChild(current);
        }

        while (!stack.isEmpty()) {
            boundary.add(stack.pop());
        }

        return boundary;
    }
}