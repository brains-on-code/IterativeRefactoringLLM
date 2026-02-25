package com.thealgorithms.searches;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The Union-Find data structure, also known as Disjoint Set Union (DSU),
 * is a data structure that tracks a set of elements partitioned into
 * disjoint (non-overlapping) subsets. It supports two main operations:
 *
 * 1. **Find**: Determine which subset a particular element is in.
 * 2. **Union**: Join two subsets into a single subset.
 *
 * This implementation uses path compression in the `find` operation
 * and union by rank in the `union` operation for efficiency.
 */
public class UnionFind {

    private final int[] parent; // Parent array
    private final int[] rank;   // Rank array

    /**
     * Initializes a Union-Find data structure with n elements.
     * Each element is its own parent initially.
     *
     * @param size the number of elements
     */
    public UnionFind(int size) {
        parent = new int[size];
        rank = new int[size];

        for (int index = 0; index < size; index++) {
            parent[index] = index;
        }
    }

    /**
     * Finds the root of the set containing the given element.
     * Uses path compression to flatten the structure.
     *
     * @param element the element to find
     * @return the root of the set
     */
    public int find(int element) {
        int parentOfElement = parent[element];

        if (element == parentOfElement) {
            return element;
        }

        // Path compression
        final int root = find(parentOfElement);
        parent[element] = root;
        return root;
    }

    /**
     * Unites the sets containing the two given elements.
     * Uses union by rank to attach the smaller tree under the larger tree.
     *
     * @param firstElement  the first element
     * @param secondElement the second element
     */
    public void union(int firstElement, int secondElement) {
        int firstRoot = find(firstElement);
        int secondRoot = find(secondElement);

        if (firstRoot == secondRoot) {
            return;
        }

        // Union by rank
        if (rank[firstRoot] > rank[secondRoot]) {
            parent[secondRoot] = firstRoot;
        } else if (rank[secondRoot] > rank[firstRoot]) {
            parent[firstRoot] = secondRoot;
        } else {
            parent[secondRoot] = firstRoot;
            rank[firstRoot]++;
        }
    }

    /**
     * Counts the number of disjoint sets.
     *
     * @return the number of disjoint sets
     */
    public int count() {
        List<Integer> uniqueRoots = new ArrayList<>();
        for (int index = 0; index < parent.length; index++) {
            int root = find(index);
            if (!uniqueRoots.contains(root)) {
                uniqueRoots.add(root);
            }
        }
        return uniqueRoots.size();
    }

    @Override
    public String toString() {
        return "parent " + Arrays.toString(parent) + " rank " + Arrays.toString(rank) + "\n";
    }
}