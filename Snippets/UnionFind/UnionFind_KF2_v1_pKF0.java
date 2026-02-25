package com.thealgorithms.searches;

import java.util.Arrays;

public class UnionFind {

    private final int[] parent;
    private final int[] rank;

    public UnionFind(int size) {
        parent = new int[size];
        rank = new int[size];

        for (int i = 0; i < size; i++) {
            parent[i] = i;
        }
    }

    public int find(int element) {
        int currentParent = parent[element];

        if (element == currentParent) {
            return element;
        }

        int root = find(currentParent);
        parent[element] = root;
        return root;
    }

    public void union(int first, int second) {
        int rootFirst = find(first);
        int rootSecond = find(second);

        if (rootFirst == rootSecond) {
            return;
        }

        if (rank[rootFirst] > rank[rootSecond]) {
            parent[rootSecond] = rootFirst;
        } else if (rank[rootSecond] > rank[rootFirst]) {
            parent[rootFirst] = rootSecond;
        } else {
            parent[rootSecond] = rootFirst;
            rank[rootFirst]++;
        }
    }

    public int count() {
        int components = 0;
        for (int i = 0; i < parent.length; i++) {
            if (find(i) == i) {
                components++;
            }
        }
        return components;
    }

    @Override
    public String toString() {
        return "parent " + Arrays.toString(parent) + " rank " + Arrays.toString(rank) + "\n";
    }
}