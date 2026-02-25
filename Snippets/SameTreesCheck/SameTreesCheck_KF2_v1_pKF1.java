package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.Deque;

public final class SameTreesCheck {

    private SameTreesCheck() {
    }

    public static boolean check(BinaryTree.Node root1, BinaryTree.Node root2) {
        if (root1 == null && root2 == null) {
            return true;
        }
        if (root1 == null || root2 == null) {
            return false;
        }

        Deque<BinaryTree.Node> queue1 = new ArrayDeque<>();
        Deque<BinaryTree.Node> queue2 = new ArrayDeque<>();
        queue1.add(root1);
        queue2.add(root2);

        while (!queue1.isEmpty() && !queue2.isEmpty()) {
            BinaryTree.Node currentNode1 = queue1.poll();
            BinaryTree.Node currentNode2 = queue2.poll();

            if (!areNodesEqual(currentNode1, currentNode2)) {
                return false;
            }

            if (currentNode1 != null) {
                if (!areNodesEqual(currentNode1.left, currentNode2.left)) {
                    return false;
                }
                if (currentNode1.left != null) {
                    queue1.add(currentNode1.left);
                    queue2.add(currentNode2.left);
                }

                if (!areNodesEqual(currentNode1.right, currentNode2.right)) {
                    return false;
                }
                if (currentNode1.right != null) {
                    queue1.add(currentNode1.right);
                    queue2.add(currentNode2.right);
                }
            }
        }
        return true;
    }

    private static boolean areNodesEqual(BinaryTree.Node node1, BinaryTree.Node node2) {
        if (node1 == null && node2 == null) {
            return true;
        }
        if (node1 == null || node2 == null) {
            return false;
        }
        return node1.data == node2.data;
    }
}