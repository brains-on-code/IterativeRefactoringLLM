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

        initialize();
    }

    private void initialize() {
        for (int index = 0; index < size; index++) {
            parent[index] = index;
            rank[index] = 0;
        }
    }

    public int find(int element) {
        validateIndex(element);
        if (parent[element] != element) {
            parent[element] = find(parent[element]); // Path compression
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

        unionByRank(rootFirst, rootSecond);
    }

    private void unionByRank(int rootFirst, int rootSecond) {
        int rankFirst = rank[rootFirst];
        int rankSecond = rank[rootSecond];

        if (rankFirst > rankSecond) {
            parent[rootSecond] = rootFirst;
        } else if (rankSecond > rankFirst) {
            parent[rootFirst] = rootSecond;
        } else {
            parent[rootSecond] = rootFirst;
            rank[rootFirst]++;
        }
    }

    public int count() {
        int components = 0;
        for (int index = 0; index < size; index++) {
            if (find(index) == index) {
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