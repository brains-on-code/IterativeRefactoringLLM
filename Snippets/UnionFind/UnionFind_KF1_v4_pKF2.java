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
     * parent[i] is the parent of element i.
     * If parent[i] == i, then i is a root.
     */
    private final int[] parent;

    /**
     * rank[i] is an upper bound on the height of the tree rooted at i.
     * Used to keep trees shallow during unions.
     */
    private final int[] rank;

    /**
     * Creates a Disjoint Set Union with elements 0..size-1,
     * each initially in its own set.
     *
     * @param size number of elements
     * @throws IllegalArgumentException if size is negative
     */
    public DisjointSetUnion(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Size must be non-negative");
        }

        parent = new int[size];
        rank = new int[size];

        for (int i = 0; i < size; i++) {
            parent[i] = i; // each element is its own parent
        }
    }

    /**
     * Returns the representative (root) of the set containing x.
     * Uses path compression to flatten the tree.
     *
     * @param x element whose set representative is to be found
     * @return representative of the set containing x
     * @throws IndexOutOfBoundsException if x is out of range
     */
    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]); // path compression
        }
        return parent[x];
    }

    /**
     * Merges the sets containing x and y using union by rank.
     *
     * @param x element in the first set
     * @param y element in the second set
     * @throws IndexOutOfBoundsException if x or y is out of range
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