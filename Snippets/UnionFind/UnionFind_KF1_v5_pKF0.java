package com.thealgorithms.searches;

import java.util.Arrays;

/**
 * Disjoint Set Union (Union-Find) data structure with path compression and union by rank.
 */
public class DisjointSetUnion {

    /** Parent links for each element. */
    private final int[] parent;

    /** Rank (approximate tree height) for each root. */
    private final int[] rank;

    /**
     * Initializes a new disjoint set with {@code size} elements (0..size-1),
     * each in its own set.
     *
     * @param size number of elements
     * @throws IllegalArgumentException if size is negative
     */
    public DisjointSetUnion(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Size must be non-negative, got: " + size);
        }

        parent = new int[size];
        rank = new int[size];

        initializeParents();
    }

    private void initializeParents() {
        for (int i = 0; i < parent.length; i++) {
            parent[i] = i; // each element is its own parent initially
        }
    }

    /**
     * Finds the representative (root) of the set containing {@code x},
     * applying path compression.
     *
     * @param x element whose set representative is to be found
     * @return representative of the set containing {@code x}
     * @throws IndexOutOfBoundsException if x is out of range
     */
    public int find(int x) {
        validateIndex(x);
        if (parent[x] != x) {
            parent[x] = find(parent[x]); // Path compression
        }
        return parent[x];
    }

    /**
     * Unites the sets containing {@code x} and {@code y} using union by rank.
     *
     * @param x first element
     * @param y second element
     * @throws IndexOutOfBoundsException if x or y is out of range
     */
    public void union(int x, int y) {
        validateIndex(x);
        validateIndex(y);

        int rootX = find(x);
        int rootY = find(y);

        if (rootX == rootY) {
            return; // already in the same set
        }

        unionRootsByRank(rootX, rootY);
    }

    private void unionRootsByRank(int rootX, int rootY) {
        int rankX = rank[rootX];
        int rankY = rank[rootY];

        if (rankX > rankY) {
            parent[rootY] = rootX;
        } else if (rankY > rankX) {
            parent[rootX] = rootY;
        } else {
            parent[rootY] = rootX;
            rank[rootX]++;
        }
    }

    /**
     * Counts the number of disjoint sets currently represented.
     *
     * @return number of distinct sets
     */
    public int countSets() {
        int count = 0;
        for (int i = 0; i < parent.length; i++) {
            if (isRoot(i)) {
                count++;
            }
        }
        return count;
    }

    private boolean isRoot(int index) {
        return find(index) == index;
    }

    private void validateIndex(int x) {
        if (x < 0 || x >= parent.length) {
            throw new IndexOutOfBoundsException(
                "Index out of bounds: " + x + ", valid range: 0.." + (parent.length - 1)
            );
        }
    }

    @Override
    public String toString() {
        return "DisjointSetUnion{" +
            "parent=" + Arrays.toString(parent) +
            ", rank=" + Arrays.toString(rank) +
            '}';
    }
}