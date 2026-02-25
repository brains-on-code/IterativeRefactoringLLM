package com.thealgorithms.searches;

import java.util.Arrays;

/**
 * The Union-Find data structure, also known as Disjoint Set Union (DSU),
 * is a data structure that tracks a set of elements partitioned into
 * disjoint (non-overlapping) subsets. It supports two main operations:
 *
 * 1. Find: Determine which subset a particular element is in.
 * 2. Union: Join two subsets into a single subset.
 *
 * This implementation uses path compression in the {@code find} operation
 * and union by rank in the {@code union} operation for efficiency.
 */
public class UnionFind {

    /** Parent array: parent[i] is the parent of element i. */
    private final int[] parent;

    /** Rank array: rank[i] is an upper bound on the height of the tree rooted at i. */
    private final int[] rank;

    /** Current number of disjoint sets. */
    private int setCount;

    /**
     * Initializes a Union-Find data structure with {@code n} elements.
     * Each element is its own parent initially.
     *
     * @param n the number of elements; must be non-negative
     * @throws IllegalArgumentException if {@code n} is negative
     */
    public UnionFind(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Number of elements must be non-negative");
        }

        parent = new int[n];
        rank = new int[n];
        setCount = n;

        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }

    /**
     * Finds the root of the set containing the element {@code i}.
     * Uses path compression to flatten the structure.
     *
     * @param i the element to find
     * @return the root of the set
     * @throws IndexOutOfBoundsException if {@code i} is out of range
     */
    public int find(int i) {
        validateIndex(i);
        if (parent[i] != i) {
            parent[i] = find(parent[i]); // Path compression
        }
        return parent[i];
    }

    /**
     * Unites the sets containing elements {@code x} and {@code y}.
     * Uses union by rank to attach the smaller tree under the larger tree.
     *
     * @param x the first element
     * @param y the second element
     * @throws IndexOutOfBoundsException if {@code x} or {@code y} is out of range
     */
    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX == rootY) {
            return;
        }

        // Union by rank
        if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else if (rank[rootY] > rank[rootX]) {
            parent[rootX] = rootY;
        } else {
            parent[rootY] = rootX;
            rank[rootX]++;
        }

        setCount--;
    }

    /**
     * Returns the number of disjoint sets.
     *
     * @return the number of disjoint sets
     */
    public int count() {
        return setCount;
    }

    private void validateIndex(int i) {
        if (i < 0 || i >= parent.length) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + i);
        }
    }

    @Override
    public String toString() {
        return "parent=" + Arrays.toString(parent) + ", rank=" + Arrays.toString(rank);
    }
}