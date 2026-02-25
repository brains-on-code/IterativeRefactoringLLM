package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public final class LevelOrderTraversal {

    private LevelOrderTraversal() {
    }

    public static List<List<Integer>> traverse(BinaryTree.Node root) {
        if (root == null) {
            return List.of();
        }

        List<List<Integer>> levels = new ArrayList<>();
        Queue<BinaryTree.Node> nodesToVisit = new LinkedList<>();

        nodesToVisit.add(root);

        while (!nodesToVisit.isEmpty()) {
            int levelSize = nodesToVisit.size();
            List<Integer> currentLevel = new LinkedList<>();

            for (int i = 0; i < levelSize; i++) {
                BinaryTree.Node currentNode = nodesToVisit.poll();
                currentLevel.add(currentNode.data);

                if (currentNode.left != null) {
                    nodesToVisit.add(currentNode.left);
                }

                if (currentNode.right != null) {
                    nodesToVisit.add(currentNode.right);
                }
            }

            levels.add(currentLevel);
        }

        return levels;
    }

    public static void printLevel(BinaryTree.Node root, int level) {
        if (root == null) {
            System.out.println("Root node must not be null! Exiting.");
            return;
        }

        if (level == 1) {
            System.out.print(root.data + " ");
        } else if (level > 1) {
            printLevel(root.left, level - 1);
            printLevel(root.right, level - 1);
        }
    }
}