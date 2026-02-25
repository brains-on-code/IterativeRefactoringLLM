package com.thealgorithms.datastructures.trees;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

class TreeNode {

    int value;
    TreeNode left;
    TreeNode right;

    TreeNode(int value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }
}

class NodeWithHorizontalDistance {

    TreeNode node;
    int horizontalDistance;

    NodeWithHorizontalDistance(TreeNode node, int horizontalDistance) {
        this.node = node;
        this.horizontalDistance = horizontalDistance;
    }
}

class BinaryTreeTopView {

    TreeNode root;

    BinaryTreeTopView() {
        this.root = null;
    }

    BinaryTreeTopView(TreeNode root) {
        this.root = root;
    }

    public void printTopView() {
        if (root == null) {
            return;
        }

        HashSet<Integer> seenHorizontalDistances = new HashSet<>();
        Queue<NodeWithHorizontalDistance> queue = new LinkedList<>();
        queue.add(new NodeWithHorizontalDistance(root, 0));

        while (!queue.isEmpty()) {
            NodeWithHorizontalDistance current = queue.remove();
            int currentDistance = current.horizontalDistance;
            TreeNode currentNode = current.node;

            if (!seenHorizontalDistances.contains(currentDistance)) {
                seenHorizontalDistances.add(currentDistance);
                System.out.print(currentNode.value + " ");
            }

            if (currentNode.left != null) {
                queue.add(
                    new NodeWithHorizontalDistance(
                        currentNode.left,
                        currentDistance - 1
                    )
                );
            }
            if (currentNode.right != null) {
                queue.add(
                    new NodeWithHorizontalDistance(
                        currentNode.right,
                        currentDistance + 1
                    )
                );
            }
        }
    }
}

public final class TopViewBinaryTreeDemo {
    private TopViewBinaryTreeDemo() {}

    public static void main(String[] args) {
        /*
           1
         /  \
        2    3
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

        BinaryTreeTopView binaryTreeTopView = new BinaryTreeTopView(root);
        System.out.println("Following are nodes in top view of Binary Tree");
        binaryTreeTopView.printTopView();
    }
}