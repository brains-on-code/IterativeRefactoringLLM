package com.thealgorithms.datastructures.trees;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

class TreeNode {


    int key;
    TreeNode left;
    TreeNode right;

    TreeNode(int key) {
        this.key = key;
        left = null;
        right = null;
    }
}

class QItem {

    TreeNode node;
    int hd;

    QItem(TreeNode n, int h) {
        node = n;
        hd = h;
    }
}

class Tree {

    TreeNode root;

    Tree() {
        root = null;
    }

    Tree(TreeNode n) {
        root = n;
    }

    public void printTopView() {
        if (root == null) {
            return;
        }

        HashSet<Integer> set = new HashSet<>();

        Queue<QItem> queue = new LinkedList<QItem>();
        queue.add(new QItem(root, 0));

        while (!queue.isEmpty()) {
            QItem qi = queue.remove();
            int hd = qi.hd;
            TreeNode n = qi.node;

            if (!set.contains(hd)) {
                set.add(hd);
                System.out.print(n.key + " ");
            }

            if (n.left != null) {
                queue.add(new QItem(n.left, hd - 1));
            }
            if (n.right != null) {
                queue.add(new QItem(n.right, hd + 1));
            }
        }
    }
}

public final class PrintTopViewofTree {
    private PrintTopViewofTree() {
    }

    public static void main(String[] args) {

        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.right = new TreeNode(4);
        root.left.right.right = new TreeNode(5);
        root.left.right.right.right = new TreeNode(6);
        Tree t = new Tree(root);
        System.out.println("Following are nodes in top view of Binary Tree");
        t.printTopView();
    }
}
