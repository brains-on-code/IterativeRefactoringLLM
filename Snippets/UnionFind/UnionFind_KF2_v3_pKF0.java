package com.thealgorithms.searches;

import java.util.Arrays;

public class UnionFind {

    private final int[] parent;
    private final int[] rank;
    private final int size;

    public UnionFind(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be positive");
        }

        this.size = size;
        this.parent = new int[size];
        this.rank = new int[size];

        initializeParents();
    }

    private void initializeParents() {
        for (int i = 0; i < size; i++) {
            parent[i] = i;
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
            return;
        }

        linkRoots(rootFirst, rootSecond);
    }

    private void linkRoots(int rootFirst, int rootSecond) {
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
        for (int i = 0; i < size; i++) {
            if (find(i) == i) {
                components++;
            }
        }
        return components;
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                "Index " + index + " is out of bounds for size " + size
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