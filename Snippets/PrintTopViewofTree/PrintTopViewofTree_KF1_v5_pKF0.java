package com.thealgorithms.datastructures.trees;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

class TreeNode {

    final int value;
    TreeNode left;
    TreeNode right;

    TreeNode(int value) {
        this.value = value;
    }
}

class QueueNode {

    final TreeNode node;
    final int horizontalDistance;

    QueueNode(TreeNode node, int horizontalDistance) {
        this.node = Objects.requireNonNull(node, "node must not be null");
        this.horizontalDistance = horizontalDistance;
    }
}

class BinaryTreeTopView {

    private final TreeNode root;

    BinaryTreeTopView() {
        this(null);
    }

    BinaryTreeTopView(TreeNode root) {
        this.root = root;
    }

    public void printTopView() {
        if (root == null) {
            return;
        }

        HashSet<Integer> visitedDistances = new HashSet<>();
        Queue<QueueNode> queue = new LinkedList<>();
        queue.offer(new QueueNode(root, 0));

        while (!queue.isEmpty()) {
            QueueNode current = queue.poll();
            if (current == null) {
                continue;
            }

            int distance = current.horizontalDistance;
            TreeNode currentNode = current.node;

            if (visitedDistances.add(distance)) {
                System.out.print(currentNode.value + " ");
            }

            if (currentNode.left != null) {
                queue.offer(new QueueNode(currentNode.left, distance - 1));
            }
            if (currentNode.right != null) {
                queue.offer(new QueueNode(currentNode.right, distance + 1));
            }
        }
    }
}

public final class TopViewBinaryTreeDemo {

    private TopViewBinaryTreeDemo() {
        // Utility class
    }

    public static void main(String[] args) {
        /*
               1
             /   \
            2     3
             \
              4
               \
                5
                 \
                  6
        */
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.right = new TreeNode(4);
        root.left.right.right = new TreeNode(5);
        root.left.right.right.right = new TreeNode(6);

        BinaryTreeTopView tree = new BinaryTreeTopView(root);
        System.out.println("Following are nodes in top view of Binary Tree");
        tree.printTopView();
    }
}