package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.Deque;


public final class SameTreesCheck {
    private SameTreesCheck() {
    }
    public static boolean check(BinaryTree.Node p, BinaryTree.Node q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }

        Deque<BinaryTree.Node> q1 = new ArrayDeque<>();
        Deque<BinaryTree.Node> q2 = new ArrayDeque<>();
        q1.add(p);
        q2.add(q);
        while (!q1.isEmpty() && !q2.isEmpty()) {
            BinaryTree.Node first = q1.poll();
            BinaryTree.Node second = q2.poll();
            if (!equalNodes(first, second)) {
                return false;
            }

            if (first != null) {
                if (!equalNodes(first.left, second.left)) {
                    return false;
                }
                if (first.left != null) {
                    q1.add(first.left);
                    q2.add(second.left);
                }

                if (!equalNodes(first.right, second.right)) {
                    return false;
                }
                if (first.right != null) {
                    q1.add(first.right);
                    q2.add(second.right);
                }
            }
        }
        return true;
    }

    private static boolean equalNodes(BinaryTree.Node p, BinaryTree.Node q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }
        return p.data == q.data;
    }
}
