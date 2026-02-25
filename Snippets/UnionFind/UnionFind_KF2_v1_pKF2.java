package com.thealgorithms.searches;

import java.util.Arrays;

public class UnionFind {

    /** Parent links: parent[i] is the parent of element i. */
    private final int[] parent;

    /** Rank (approximate tree height) used for union by rank. */
    private final int[] rank;

    /**
     * Creates a new Union-Find (Disjoint Set Union) structure with {@code n} elements
     * labeled from {@code 0} to {@code n - 1}. Initially, each element is in its own set.
     *
     * @param n number of elements
     */
    public UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];

        // Initially, each element is its own parent (each element is a separate set).
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }

    /**
     * Finds and returns the representative (root) of the set that contains {@code i}.
     * Uses path compression to flatten the structure for faster future queries.
     *
     * @param i element whose set representative is to be found
     * @return representative of the set containing {@code i}
     */
    public int find(int i) {
        int parentOfI = parent[i];

        // If the element is its own parent, it is the root of its set.
        if (i == parentOfI) {
            return i;
        }

        // Recursively find the root and compress the path.
        int root = find(parentOfI);
        parent[i] = root;
        return root;
    }

    /**
     * Unites the sets that contain {@code x} and {@code y}.
     * Uses union by rank to keep trees shallow.
     *
     * @param x first element
     * @param y second element
     */
    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        // Elements are already in the same set.
        if (rootX == rootY) {
            return;
        }

        // Attach the tree with smaller rank under the one with larger rank.
        if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else if (rank[rootY] > rank[rootX]) {
            parent[rootX] = rootY;
        } else {
            // Same rank: choose one as new root and increase its rank.
            parent[rootY] = rootX;
            rank[rootX]++;
        }
    }

    /**
     * Counts the number of distinct sets (connected components).
     *
     * @return number of distinct sets
     */
    public int count() {
        int distinctRoots = 0;

        for (int i = 0; i < parent.length; i++) {
            // An element is a root if it is its own parent.
            if (find(i) == i) {
                distinctRoots++;
            }
        }

        return distinctRoots;
    }

    @Override
    public String toString() {
        return "parent " + Arrays.toString(parent) + " rank " + Arrays.toString(rank) + "\n";
    }
}