package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public final class BoundaryTraversal {

    private BoundaryTraversal() {
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
        BinaryTree.Node current = root.left;

        if (current == null && root.right != null) {
            current = root.right;
        }

        while (current != null) {
            if (!isLeaf(current)) {
                boundary.add(current.data);
            }
            if (current.left != null) {
                current = current.left;
            } else if (current.right != null) {
                current = current.right;
            } else {
                break;
            }
        }
    }

    private static void addLeaves(BinaryTree.Node node, List<Integer> leaves) {
        if (node == null) {
            return;
        }
        if (isLeaf(node)) {
            leaves.add(node.data);
        } else {
            addLeaves(node.left, leaves);
            addLeaves(node.right, leaves);
        }
    }

    private static void addRightBoundary(BinaryTree.Node root, List<Integer> boundary) {
        BinaryTree.Node current = root.right;
        List<Integer> rightBoundary = new ArrayList<>();

        if (current != null && root.left == null) {
            return;
        }

        while (current != null) {
            if (!isLeaf(current)) {
                rightBoundary.add(current.data);
            }
            if (current.right != null) {
                current = current.right;
            } else if (current.left != null) {
                current = current.left;
            } else {
                break;
            }
        }

        for (int i = rightBoundary.size() - 1; i >= 0; i--) {
            boundary.add(rightBoundary.get(i));
        }
    }

    private static boolean isLeaf(BinaryTree.Node node) {
        return node.left == null && node.right == null;
    }

    public static List<Integer> iterativeBoundaryTraversal(BinaryTree.Node root) {
        List<Integer> boundary = new ArrayList<>();
        if (root == null) {
            return boundary;
        }

        if (!isLeaf(root)) {
            boundary.add(root.data);
        }

        BinaryTree.Node current = root.left;
        if (current == null && root.right != null) {
            current = root.right;
        }
        while (current != null) {
            if (!isLeaf(current)) {
                boundary.add(current.data);
            }
            current = (current.left != null) ? current.left : current.right;
        }

        addLeaves(root, boundary);

        current = root.right;
        Deque<Integer> rightBoundaryStack = new LinkedList<>();
        if (current != null && root.left == null) {
            return boundary;
        }
        while (current != null) {
            if (!isLeaf(current)) {
                rightBoundaryStack.push(current.data);
            }
            current = (current.right != null) ? current.right : current.left;
        }

        while (!rightBoundaryStack.isEmpty()) {
            boundary.add(rightBoundaryStack.pop());
        }
        return boundary;
    }
}