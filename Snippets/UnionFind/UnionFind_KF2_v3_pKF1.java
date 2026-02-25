package com.thealgorithms.searches;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UnionFind {

    private final int[] parent;
    private final int[] rank;

    public UnionFind(int size) {
        parent = new int[size];
        rank = new int[size];

        for (int index = 0; index < size; index++) {
            parent[index] = index;
        }
    }

    public int find(int element) {
        int parentElement = parent[element];

        if (element == parentElement) {
            return element;
        }

        int root = find(parentElement);
        parent[element] = root;
        return root;
    }

    public void union(int firstElement, int secondElement) {
        int firstRoot = find(firstElement);
        int secondRoot = find(secondElement);

        if (firstRoot == secondRoot) {
            return;
        }

        if (rank[firstRoot] > rank[secondRoot]) {
            parent[secondRoot] = firstRoot;
        } else if (rank[secondRoot] > rank[firstRoot]) {
            parent[firstRoot] = secondRoot;
        } else {
            parent[secondRoot] = firstRoot;
            rank[firstRoot]++;
        }
    }

    public int count() {
        List<Integer> uniqueRoots = new ArrayList<>();
        for (int element = 0; element < parent.length; element++) {
            int root = find(element);
            if (!uniqueRoots.contains(root)) {
                uniqueRoots.add(root);
            }
        }
        return uniqueRoots.size();
    }

    @Override
    public String toString() {
        return "parent " + Arrays.toString(parent) + " rank " + Arrays.toString(rank) + "\n";
    }
}