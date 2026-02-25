package com.thealgorithms.searches;

import java.util.Arrays;

/**
 * Disjoint Set Union (Union-Find) data structure with:
 * <ul>
 *     <li>Path compression in {@link #find(int)}</li>
 *     <li>Union by rank in {@link #union(int, int)}</li>
 * </ul>
 */
public class DisjointSetUnion {

    /**
     * For each element {@code i}, {@code parent[i]} stores its parent in the forest.
     * A root element is its own parent: {@code parent[i] == i}.
     */
    private final int[] parent;

    /**
     * For each root {@code i}, {@code rank[i]} stores an upper bound on the height
     * of the tree rooted at {@code i}. Used to keep trees shallow during unions.
     */
    private final int[] rank;

    /**
     * Constructs a Disjoint Set Union with {@code size} elements labeled
     * from {@code 0} to {@code size - 1}. Initially, each element is in its own set.
     *
     * @param size the number of elements
     * @throws IllegalArgumentException if {@code size} is negative
     */
    public DisjointSetUnion(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Size must be non-negative");
        }

        parent = new int[size];
        rank = new int[size];

        // Initially, each element is its own parent (each element is a separate set).
        for (int i = 0; i < size; i++) {
            parent[i] = i;
        }
    }

    /**
     * Returns the representative (root) of the set containing {@code x}.
     * <p>
     * Applies path compression: during the search for the root, each visited
     * node is directly attached to the root, flattening the tree and
     * improving future query performance.
     *
     * @param x the element whose set representative is to be found
     * @return the representative of the set containing {@code x}
     * @throws IndexOutOfBoundsException if {@code x} is out of range
     */
    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]); // Path compression
        }
        return parent[x];
    }

    /**
     * Merges the sets containing {@code x} and {@code y}.
     * <p>
     * Uses union by rank: the root with smaller rank is attached to the root
     * with larger rank. If both ranks are equal, one root becomes the parent
     * and its rank is incremented.
     *
     * @param x an element in the first set
     * @param y an element in the second set
     * @throws IndexOutOfBoundsException if {@code x} or {@code y} is out of range
     */
    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        // Elements are already in the same set.
        if (rootX == rootY) {
            return;
        }

        // Attach the tree with smaller rank under the tree with larger rank.
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
     * Returns the number of distinct sets currently represented.
     *
     * @return the number of disjoint sets
     */
    public int countSets() {
        boolean[] isRoot = new boolean[parent.length];
        int count = 0;

        for (int i = 0; i < parent.length; i++) {
            int root = find(i);
            if (!isRoot[root]) {
                isRoot[root] = true;
                count++;
            }
        }

        return count;
    }

    @Override
    public String toString() {
        return "parent " + Arrays.toString(parent) + " rank " + Arrays.toString(rank) + "\n";
    }
}