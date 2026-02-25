package com.thealgorithms.searches;

import java.util.Arrays;

/**
 * Disjoint Set Union (Union-Find) data structure.
 *
 * <p>Optimizations:
 * <ul>
 *     <li><b>Path compression</b> in {@link #find(int)} to flatten trees.</li>
 *     <li><b>Union by rank</b> in {@link #union(int, int)} to keep trees shallow.</li>
 * </ul>
 */
public class DisjointSetUnion {

    /**
     * parent[i] stores the parent of element i.
     * If parent[i] == i, then i is a root.
     */
    private final int[] parent;

    /**
     * rank[i] is an upper bound on the height of the tree rooted at i.
     * Used to decide which root becomes the parent during union.
     */
    private final int[] rank;

    /**
     * Creates a Disjoint Set Union with elements {@code 0..size-1},
     * each initially in its own singleton set.
     *
     * @param size number of elements
     * @throws IllegalArgumentException if {@code size} is negative
     */
    public DisjointSetUnion(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Size must be non-negative");
        }

        parent = new int[size];
        rank = new int[size];

        for (int i = 0; i < size; i++) {
            parent[i] = i; // each element starts as its own root
        }
    }

    /**
     * Returns the representative (root) of the set containing {@code x}.
     * Applies path compression so that subsequent queries are faster.
     *
     * @param x element whose set representative is to be found
     * @return representative of the set containing {@code x}
     * @throws IndexOutOfBoundsException if {@code x} is out of range
     */
    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]); // compress path to root
        }
        return parent[x];
    }

    /**
     * Merges the sets containing {@code x} and {@code y}.
     * Uses union by rank to attach the shallower tree under the deeper one.
     *
     * @param x element in the first set
     * @param y element in the second set
     * @throws IndexOutOfBoundsException if {@code x} or {@code y} is out of range
     */
    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX == rootY) {
            return; // elements are already in the same set
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
     * Returns the number of distinct sets currently represented.
     *
     * @return number of disjoint sets
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