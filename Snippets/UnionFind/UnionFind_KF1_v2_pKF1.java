package com.thealgorithms.searches;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Disjoint Set Union (Union-Find) data structure implementation.
 */
public class DisjointSetUnion {

    private final int[] parent;
    private final int[] rank;

    /**
     * Initializes a Disjoint Set Union with the given number of elements.
     *
     * @param size the number of elements
     */
    public DisjointSetUnion(int size) {
        parent = new int[size];
        rank = new int[size];

        for (int element = 0; element < size; element++) {
            parent[element] = element;
        }
    }

    /**
     * Finds the representative (root) of the set that the given element belongs to.
     * Uses path compression for efficiency.
     *
     * @param element the element whose set representative is to be found
     * @return the representative of the set
     */
    public int find(int element) {
        int root = parent[element];

        if (element == root) {
            return element;
        }

        int representative = find(root);
        parent[element] = representative;
        return representative;
    }

    /**
     * Unites the sets that contain elementA and elementB.
     * Uses union by rank for efficiency.
     *
     * @param elementA first element
     * @param elementB second element
     */
    public void union(int elementA, int elementB) {
        int rootA = find(elementA);
        int rootB = find(elementB);

        if (rootA == rootB) {
            return;
        }

        if (rank[rootA] > rank[rootB]) {
            parent[rootB] = rootA;
        } else if (rank[rootB] > rank[rootA]) {
            parent[rootA] = rootB;
        } else {
            parent[rootB] = rootA;
            rank[rootA]++;
        }
    }

    /**
     * Counts the number of distinct sets currently represented in the structure.
     *
     * @return the number of distinct sets
     */
    public int countDistinctSets() {
        List<Integer> uniqueRoots = new ArrayList<>();
        for (int element = 0; element < parent.length; element++) {
            int root = find(element);
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