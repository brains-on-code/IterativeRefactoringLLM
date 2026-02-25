package com.thealgorithms.searches;

import java.util.Arrays;

/**
 * Disjoint Set Union (Union-Find) data structure.
 * Supports efficient union and find operations on disjoint sets.
 */
public class UnionFind {

    /**
     * parent[i] is the parent of element i.
     * If parent[i] == i, then i is a root.
     */
    private final int[] parent;

    /**
     * rank[i] is an upper bound on the height of the tree rooted at i.
     * Used for union by rank.
     */
    private final int[] rank;

    /**
     * Creates a Union-Find structure with {@code n} elements labeled
     * from {@code 0} to {@code n - 1}. Initially, each element is in its own set.
     *
     * @param n number of elements
     */
    public UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];

        for (int i = 0; i < n; i++) {
            // Initially, each element is its own parent (each element is a separate set)
            parent[i] = i;
        }
    }

    /**
     * Returns the representative (root) of the set containing {@code i}.
     * Applies path compression to flatten the structure for future queries.
     *
     * @param i element whose set representative is to be found
     * @return representative of the set containing {@code i}
     */
    public int find(int i) {
        if (parent[i] != i) {
            // Recursively find the root and compress the path
            parent[i] = find(parent[i]);
        }
        return parent[i];
    }

    /**
     * Merges the sets containing {@code x} and {@code y}.
     * Uses union by rank to keep trees shallow.
     *
     * @param x first element
     * @param y second element
     */
    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        // Elements are already in the same set
        if (rootX == rootY) {
            return;
        }

        // Attach the tree with smaller rank under the tree with larger rank
        if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else if (rank[rootY] > rank[rootX]) {
            parent[rootX] = rootY;
        } else {
            // Same rank: choose one as new root and increase its rank
            parent[rootY] = rootX;
            rank[rootX]++;
        }
    }

    /**
     * Returns the number of distinct sets (connected components).
     *
     * @return number of distinct sets
     */
    public int count() {
        int distinctRoots = 0;

        for (int i = 0; i < parent.length; i++) {
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