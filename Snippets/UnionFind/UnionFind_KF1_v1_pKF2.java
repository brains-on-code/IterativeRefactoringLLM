package com.thealgorithms.searches;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Disjoint Set Union (Union-Find) data structure with path compression
 * and union by rank.
 */
public class DisjointSetUnion {

    /** parent[i] is the parent of element i in the disjoint set forest */
    private final int[] parent;

    /** rank[i] is an upper bound on the height of the subtree rooted at i */
    private final int[] rank;

    /**
     * Creates a new Disjoint Set Union structure with {@code size} elements
     * labeled from 0 to {@code size - 1}. Initially, each element is in its
     * own set.
     *
     * @param size number of elements
     */
    public DisjointSetUnion(int size) {
        parent = new int[size];
        rank = new int[size];

        for (int i = 0; i < size; i++) {
            parent[i] = i; // each element is its own parent initially
        }
    }

    /**
     * Finds and returns the representative (root) of the set that contains
     * {@code x}. Applies path compression to flatten the structure for
     * faster subsequent queries.
     *
     * @param x element whose set representative is to be found
     * @return representative of the set containing {@code x}
     */
    public int find(int x) {
        int root = parent[x];

        if (x == root) {
            return x;
        }

        // Path compression: recursively find root and update parent[x]
        final int compressedRoot = find(root);
        parent[x] = compressedRoot;
        return compressedRoot;
    }

    /**
     * Unites the sets that contain {@code x} and {@code y}. Uses union by
     * rank to keep the tree shallow.
     *
     * @param x first element
     * @param y second element
     */
    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX == rootY) {
            return; // already in the same set
        }

        // Attach the tree with smaller rank under the one with larger rank
        if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else if (rank[rootY] > rank[rootX]) {
            parent[rootX] = rootY;
        } else {
            parent[rootY] = rootX;
            rank[rootX]++; // increase rank when both have the same rank
        }
    }

    /**
     * Returns the number of disjoint sets currently represented in this
     * structure.
     *
     * @return number of distinct sets
     */
    public int countSets() {
        List<Integer> roots = new ArrayList<>();
        for (int i = 0; i < parent.length; i++) {
            int root = find(i);
            if (!roots.contains(root)) {
                roots.add(root);
            }
        }
        return roots.size();
    }

    @Override
    public String toString() {
        return "parent " + Arrays.toString(parent) + " rank " + Arrays.toString(rank) + "\n";
    }
}