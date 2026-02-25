package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    public static List<List<Integer>> method1(BinaryTree.Node root) {
        if (root == null) {
            return List.of();
        }

        List<List<Integer>> result = new ArrayList<>();
        Deque<BinaryTree.Node> queue = new ArrayDeque<>();
        queue.offer(root);

        boolean isRightToLeft = false;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> levelValues = new LinkedList<>();

            for (int i = 0; i < levelSize; i++) {
                BinaryTree.Node node = queue.poll();

                if (isRightToLeft) {
                    levelValues.add(0, node.data);
                } else {
                    levelValues.add(node.data);
                }

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }

            isRightToLeft = !isRightToLeft;
            result.add(levelValues);
        }

        return result;
    }
}