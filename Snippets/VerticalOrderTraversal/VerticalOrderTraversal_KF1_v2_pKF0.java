package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public final class Class1 {

    private Class1() {}

    public static ArrayList<Integer> method1(BinaryTree.Node root) {
        if (root == null) {
            return new ArrayList<>();
        }

        Queue<BinaryTree.Node> nodes = new LinkedList<>();
        Queue<Integer> columns = new LinkedList<>();
        Map<Integer, ArrayList<Integer>> columnValues = new HashMap<>();

        int maxColumn = 0;
        int minColumn = 0;

        nodes.offer(root);
        columns.offer(0);

        while (!nodes.isEmpty()) {
            BinaryTree.Node currentNode = nodes.poll();
            int currentColumn = columns.poll();

            if (currentNode.left != null) {
                nodes.offer(currentNode.left);
                columns.offer(currentColumn - 1);
            }

            if (currentNode.right != null) {
                nodes.offer(currentNode.right);
                columns.offer(currentColumn + 1);
            }

            columnValues
                .computeIfAbsent(currentColumn, k -> new ArrayList<>())
                .add(currentNode.data);

            maxColumn = Math.max(maxColumn, currentColumn);
            minColumn = Math.min(minColumn, currentColumn);
        }

        ArrayList<Integer> result = new ArrayList<>();
        for (int col = minColumn; col <= maxColumn; col++) {
            ArrayList<Integer> values = columnValues.get(col);
            if (values != null) {
                result.addAll(values);
            }
        }

        return result;
    }
}