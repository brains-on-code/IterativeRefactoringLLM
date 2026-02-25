package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.Deque;

public final class SameTreesCheck {

    private SameTreesCheck() {}

    /**
     * Returns {@code true} if two binary trees are structurally identical and
     * contain the same values in corresponding nodes.
     *
     * @param p root of the first tree
     * @param q root of the second tree
     */
    public static boolean check(BinaryTree.Node p, BinaryTree.Node q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }

        Deque<BinaryTree.Node> queue1 = new ArrayDeque<>();
        Deque<BinaryTree.Node> queue2 = new ArrayDeque<>();
        queue1.add(p);
        queue2.add(q);

        while (!queue1.isEmpty() && !queue2.isEmpty()) {
            BinaryTree.Node node1 = queue1.poll();
            BinaryTree.Node node2 = queue2.poll();

            if (!nodesEqual(node1, node2)) {
                return false;
            }

            if (!nodesEqual(node1.left, node2.left)) {
                return false;
            }
            if (node1.left != null) {
                queue1.add(node1.left);
                queue2.add(node2.left);
            }

            if (!nodesEqual(node1.right, node2.right)) {
                return false;
            }
            if (node1.right != null) {
                queue1.add(node1.right);
                queue2.add(node2.right);
            }
        }

        return queue1.isEmpty() && queue2.isEmpty();
    }

    /**
     * Returns {@code true} if both nodes are null, or both are non-null and
     * contain the same data value.
     */
    private static boolean nodesEqual(BinaryTree.Node p, BinaryTree.Node q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }
        return p.data == q.data;
    }
}