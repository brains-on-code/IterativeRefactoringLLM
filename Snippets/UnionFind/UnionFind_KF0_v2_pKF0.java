package com.thealgorithms.searches;

import java.util.Arrays;

/**
 * The Union-Find data structure, also known as Disjoint Set Union (DSU),
 * tracks a set of elements partitioned into disjoint (non-overlapping) subsets.
 * It supports:
 * <ul>
 *     <li><b>find</b>: Determine which subset a particular element is in.</li>
 *     <li><b>union</b>: Join two subsets into a single subset.</li>
 * </ul>
 *
 * This implementation uses:
 * <ul>
 *     <li>Path compression in {@link #find(int)} for near-constant-time finds.</li>
 *     <li>Union by rank in {@link #union(int, int)} to keep trees shallow.</li>
 * </ul>
 */
public class UnionFind {

    /** parent[i] is the parent of element i; roots have parent[i] == i. */
    private final int[] parent;

    /** rank[i] is an upper bound on the height of the tree rooted at i. */
    private final int[] rank;

    /** Current number of disjoint sets. */
    private int setCount;

    /**
     * Initializes a Union-Find data structure with {@code n} elements (0 to n-1).
     * Each element starts in its own set.
     *
     * @param n the number of elements; must be non-negative
     * @throws IllegalArgumentException if {@code n} is negative
     */
    public UnionFind(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Number of elements must be non-negative");
        }

        this.parent = new int[n];
        this.rank = new int[n];
        this.setCount = n;

        for (int i = 0; i < n; i++) {
            parent[i] = i; // each element is its own parent initially
        }
    }

    /**
     * Finds the representative (root) of the set containing element {@code i}.
     * Applies path compression to flatten the tree structure.
     *
     * @param i the element whose set representative is to be found
     * @return the root of the set containing {@code i}
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
     * Uses union by rank to attach the shallower tree under the deeper tree.
     *
     * @param x the first element
     * @param y the second element
     * @throws IndexOutOfBoundsException if {@code x} or {@code y} is out of range
     */
    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX == rootY) {
            return; // already in the same set
        }

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
     * Returns the current number of disjoint sets.
     *
     * @return the number of disjoint sets
     */
    public int count() {
        return setCount;
    }

    /**
     * Ensures that the given index is within the valid range.
     *
     * @param i the index to validate
     * @throws IndexOutOfBoundsException if {@code i} is out of range
     */
    private void validateIndex(int i) {
        if (i < 0 || i >= parent.length) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + i);
        }
    }

    @Override
    public String toString() {
        return "UnionFind{" +
            "parent=" + Arrays.toString(parent) +
            ", rank=" + Arrays.toString(rank) +
            ", setCount=" + setCount +
            '}';
    }
}