package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;


public final class BoundaryTraversal {
    private BoundaryTraversal() {
    }

    public static List<Integer> boundaryTraversal(BinaryTree.Node root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        if (!isLeaf(root)) {
            result.add(root.data);
        }

        addLeftBoundary(root, result);

        addLeaves(root, result);

        addRightBoundary(root, result);

        return result;
    }

    private static void addLeftBoundary(BinaryTree.Node node, List<Integer> result) {
        BinaryTree.Node cur = node.left;

        if (cur == null && node.right != null) {
            cur = node.right;
        }

        while (cur != null) {
            if (!isLeaf(cur)) {
                result.add(cur.data);
            }
            if (cur.left != null) {
                cur = cur.left;
            } else if (cur.right != null) {
                cur = cur.right;
            } else {
                break;
            }
        }
    }

    private static void addLeaves(BinaryTree.Node node, List<Integer> result) {
        if (node == null) {
            return;
        }
        if (isLeaf(node)) {
            result.add(node.data);
        } else {
            addLeaves(node.left, result);
            addLeaves(node.right, result);
        }
    }

    private static void addRightBoundary(BinaryTree.Node node, List<Integer> result) {
        BinaryTree.Node cur = node.right;
        List<Integer> temp = new ArrayList<>();

        if (cur != null && node.left == null) {
            return;
        }
        while (cur != null) {
            if (!isLeaf(cur)) {
                temp.add(cur.data);
            }
            if (cur.right != null) {
                cur = cur.right;
            } else if (cur.left != null) {
                cur = cur.left;
            } else {
                break;
            }
        }

        for (int i = temp.size() - 1; i >= 0; i--) {
            result.add(temp.get(i));
        }
    }

    private static boolean isLeaf(BinaryTree.Node node) {
        return node.left == null && node.right == null;
    }

    public static List<Integer> iterativeBoundaryTraversal(BinaryTree.Node root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        if (!isLeaf(root)) {
            result.add(root.data);
        }

        BinaryTree.Node cur = root.left;
        if (cur == null && root.right != null) {
            cur = root.right;
        }
        while (cur != null) {
            if (!isLeaf(cur)) {
                result.add(cur.data);
            }
            cur = (cur.left != null) ? cur.left : cur.right;
        }

        addLeaves(root, result);

        cur = root.right;
        Deque<Integer> stack = new LinkedList<>();
        if (cur != null && root.left == null) {
            return result;
        }
        while (cur != null) {
            if (!isLeaf(cur)) {
                stack.push(cur.data);
            }
            cur = (cur.right != null) ? cur.right : cur.left;
        }

        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }
        return result;
    }
}
