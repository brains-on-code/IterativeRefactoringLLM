package com.thealgorithms.searches;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Disjoint Set Union (Union-Find) data structure with path compression and union by rank.
 */
public class Class1 {

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
    public Class1(int size) {
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
    public int method1(int x) {
        int root = parent[x];

        if (x == root) {
            return x;
        }

        int representative = method1(root);
        parent[x] = representative;
        return representative;
    }

    /**
     * Unites the sets containing {@code x} and {@code y} using union by rank.
     *
     * @param x first element
     * @param y second element
     */
    public void method2(int x, int y) {
        int rootX = method1(x);
        int rootY = method1(y);

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
    public int method3() {
        List<Integer> representatives = new ArrayList<>();
        for (int i = 0; i < parent.length; i++) {
            int root = method1(i);
            if (!representatives.contains(root)) {
                representatives.add(root);
            }
        }
        return representatives.size();
    }

    @Override
    public String method4() {
        return "p " + Arrays.toString(parent) + " r " + Arrays.toString(rank) + "\n";
    }
}