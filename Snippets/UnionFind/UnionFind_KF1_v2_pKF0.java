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
     */
    public DisjointSetUnion(int size) {
        parent = new int[size];
        rank = new int[size];

        for (int i = 0; i < size; i++) {
            parent[i] = i;
        }
    }

    /**
     * Finds the representative (root) of the set containing {@code x},
     * applying path compression.
     *
     * @param x element whose set representative is to be found
     * @return representative of the set containing {@code x}
     */
    public int find(int x) {
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
     */
    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX == rootY) {
            return;
        }

        if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else if (rank[rootY] > rank[rootX]) {
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
            if (find(i) == i) {
                count++;
            }
        }
        return count;
    }

    @Override
    public String toString() {
        return "Parents: " + Arrays.toString(parent) + ", Ranks: " + Arrays.toString(rank);
    }
}