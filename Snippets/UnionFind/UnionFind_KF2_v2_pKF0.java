package com.thealgorithms.searches;

import java.util.Arrays;

public class UnionFind {

    private final int[] parent;
    private final int[] rank;

    public UnionFind(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be positive");
        }

        parent = new int[size];
        rank = new int[size];

        for (int i = 0; i < size; i++) {
            parent[i] = i; // each element is its own parent initially
        }
    }

    public int find(int element) {
        validateIndex(element);

        if (parent[element] != element) {
            parent[element] = find(parent[element]); // path compression
        }
        return parent[element];
    }

    public void union(int first, int second) {
        validateIndex(first);
        validateIndex(second);

        int rootFirst = find(first);
        int rootSecond = find(second);

        if (rootFirst == rootSecond) {
            return; // already in the same set
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

    private void validateIndex(int index) {
        if (index < 0 || index >= parent.length) {
            throw new IndexOutOfBoundsException(
                "Index " + index + " is out of bounds for size " + parent.length
            );
        }
    }

    @Override
    public String toString() {
        return "UnionFind{" +
            "parent=" + Arrays.toString(parent) +
            ", rank=" + Arrays.toString(rank) +
            '}';
    }
}