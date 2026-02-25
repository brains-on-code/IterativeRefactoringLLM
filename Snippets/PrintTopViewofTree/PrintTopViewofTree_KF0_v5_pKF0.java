package com.thealgorithms.datastructures.trees;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

final class TreeNode {

    final int key;
    TreeNode left;
    TreeNode right;

    TreeNode(int key) {
        this.key = key;
    }
}

final class QueueItem {

    final TreeNode node;
    final int horizontalDistance;

    QueueItem(TreeNode node, int horizontalDistance) {
        this.node = Objects.requireNonNull(node, "node must not be null");
        this.horizontalDistance = horizontalDistance;
    }
}

final class Tree {

    private final TreeNode root;

    Tree() {
        this(null);
    }

    Tree(TreeNode root) {
        this.root = root;
    }

    public void printTopView() {
        if (root == null) {
            return;
        }

        HashSet<Integer> visitedDistances = new HashSet<>();
        Queue<QueueItem> queue = new LinkedList<>();

        queue.offer(new QueueItem(root, 0));

        while (!queue.isEmpty()) {
            QueueItem currentItem = queue.poll();
            int currentDistance = currentItem.horizontalDistance;
            TreeNode currentNode = currentItem.node;

            if (visitedDistances.add(currentDistance)) {
                System.out.print(currentNode.key + " ");
            }

            enqueueChild(queue, currentNode.left, currentDistance - 1);
            enqueueChild(queue, currentNode.right, currentDistance + 1);
        }
    }

    private void enqueueChild(Queue<QueueItem> queue, TreeNode child, int distance) {
        if (child == null) {
            return;
        }
        queue.offer(new QueueItem(child, distance));
    }
}

public final class PrintTopViewofTree {

    private PrintTopViewofTree() {
        // Utility class
    }

    public static void main(String[] args) {
        /*
         * Create following Binary Tree
         *        1
         *      /   \
         *     2     3
         *      \
         *       4
         *        \
         *         5
         *          \
         *           6
         */
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.right = new TreeNode(4);
        root.left.right.right = new TreeNode(5);
        root.left.right.right.right = new TreeNode(6);

        Tree tree = new Tree(root);
        System.out.println("Following are nodes in top view of Binary Tree");
        tree.printTopView();
    }
}