package com.thealgorithms.datastructures.trees;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

class TreeNode {

    final int key;
    TreeNode left;
    TreeNode right;

    TreeNode(int key) {
        this.key = key;
    }
}

class QueueItem {

    final TreeNode node;
    final int horizontalDistance;

    QueueItem(TreeNode node, int horizontalDistance) {
        this.node = node;
        this.horizontalDistance = horizontalDistance;
    }
}

class Tree {

    private final TreeNode root;

    Tree() {
        this.root = null;
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
            if (currentItem == null) {
                continue;
            }

            int currentDistance = currentItem.horizontalDistance;
            TreeNode currentNode = currentItem.node;

            if (visitedDistances.add(currentDistance)) {
                System.out.print(currentNode.key + " ");
            }

            if (currentNode.left != null) {
                queue.offer(new QueueItem(currentNode.left, currentDistance - 1));
            }
            if (currentNode.right != null) {
                queue.offer(new QueueItem(currentNode.right, currentDistance + 1));
            }
        }
    }
}

public final class PrintTopViewofTree {

    private PrintTopViewofTree() {}

    public static void main(String[] args) {
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