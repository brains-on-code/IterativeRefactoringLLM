package com.thealgorithms.searches;

import java.util.Arrays;

/**
 * Union-Find (Disjoint Set Union, DSU) data structure.
 *
 * <p>Maintains a collection of disjoint sets over {@code n} elements labeled
 * {@code 0..n-1}. Supports:
 *
 * <ul>
 *   <li>{@link #find(int)} – find the representative (root) of an element's set</li>
 *   <li>{@link #union(int, int)} – merge the sets containing two elements</li>
 *   <li>{@link #count()} – count how many disjoint sets currently exist</li>
 * </ul>
 *
 * <p>This implementation uses:
 * <ul>
 *   <li><b>Path compression</b> in {@link #find(int)} to flatten trees</li>
 *   <li><b>Union by rank</b> in {@link #union(int, int)} to keep trees shallow</li>
 * </ul>
 */
public class UnionFind {

    /** parent[i] is the parent of i; roots are their own parent. */
    private final int[] parent;

    /** rank[i] is an upper bound on the height of the tree rooted at i. */
    private final int[] rank;

    /**
     * Creates a Union-Find structure with {@code n} elements {@code 0..n-1},
     * each initially in its own singleton set.
     *
     * @param n number of elements
     * @throws IllegalArgumentException if {@code n <= 0}
     */
    public UnionFind(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Number of elements must be positive");
        }

        parent = new int[n];
        rank = new int[n];

        for (int i = 0; i < n; i++) {
            parent[i] = i; // each element starts as its own root
        }
    }

    /**
     * Returns the representative (root) of the set containing {@code i}.
     *
     * <p>Applies path compression: during the search for the root, all visited
     * nodes are directly attached to the root, flattening the tree and
     * improving future query performance.
     *
     * @param i element whose set representative is requested
     * @return index of the root of the set containing {@code i}
     * @throws IndexOutOfBoundsException if {@code i} is out of range
     */
    public int find(int i) {
        int parentOfI = parent[i];

        if (i == parentOfI) {
            return i; // i is the root of its set
        }

        int root = find(parentOfI);
        parent[i] = root; // path compression
        return root;
    }

    /**
     * Merges the sets containing {@code x} and {@code y}.
     *
     * <p>If both elements are already in the same set, this method does nothing.
     * Otherwise, it attaches the root with lower rank under the root with
     * higher rank. If both ranks are equal, one root becomes the parent and
     * its rank is incremented.
     *
     * @param x first element
     * @param y second element
     * @throws IndexOutOfBoundsException if {@code x} or {@code y} is out of range
     */
    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX == rootY) {
            return; // elements are already in the same set
        }

        // union by rank: attach the shallower tree under the deeper tree
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
     * Returns the number of disjoint sets currently represented in this
     * Union-Find structure.
     *
     * <p>Runs in {@code O(n α(n))} time due to the {@link #find(int)} calls.
     *
     * @return number of distinct sets
     */
    public int count() {
        int setCount = 0;
        for (int i = 0; i < parent.length; i++) {
            if (find(i) == i) {
                setCount++;
            }
        }
        return setCount;
    }

    @Override
    public String toString() {
        return "parent=" + Arrays.toString(parent) + ", rank=" + Arrays.toString(rank);
    }
}