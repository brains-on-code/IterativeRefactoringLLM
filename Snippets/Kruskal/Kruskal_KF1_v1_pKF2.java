package com.thealgorithms.datastructures.graphs;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * Utility class for graph-related operations.
 */
public class Class1 {

    /**
     * Represents an edge in a graph.
     * var1: source vertex
     * var2: destination vertex
     * var3: edge weight
     */
    static class Class2 {

        int var1;
        int var2;
        int var3;

        Class2(int var1, int var2, int var3) {
            this.var1 = var1;
            this.var2 = var2;
            this.var3 = var3;
        }
    }

    /**
     * Adds an edge to the adjacency list.
     *
     * @param var4 adjacency list of edges
     * @param var1 source vertex
     * @param var2 destination vertex
     * @param var3 edge weight
     */
    static void method1(HashSet<Class2>[] var4, int var1, int var2, int var3) {
        var4[var1].add(new Class2(var1, var2, var3));
    }

    /**
     * Constructs a minimum spanning forest using a variant of Kruskal's algorithm.
     *
     * @param var4 adjacency list of the original graph
     * @return adjacency list representing the minimum spanning forest
     */
    public HashSet<Class2>[] method2(HashSet<Class2>[] var4) {
        int var5 = var4.length;

        // Disjoint-set (union-find) parent array
        int[] var6 = new int[var5];

        // Disjoint-set groups: each set contains the vertices in that component
        HashSet<Integer>[] var7 = new HashSet[var5];

        // Resulting adjacency list for the minimum spanning forest
        HashSet<Class2>[] var8 = new HashSet[var5];

        // Priority queue of all edges, ordered by weight (var3)
        PriorityQueue<Class2> var9 =
            new PriorityQueue<>(Comparator.comparingInt(edge -> edge.var3));

        // Initialize disjoint sets and result adjacency list; collect all edges
        for (int var10 = 0; var10 < var5; var10++) {
            var8[var10] = new HashSet<>();
            var7[var10] = new HashSet<>();
            var7[var10].add(var10);
            var6[var10] = var10;
            var9.addAll(var4[var10]);
        }

        int var11 = 0; // number of vertices in the current largest component

        // Process edges in non-decreasing order of weight
        while (var11 != var5 && !var9.isEmpty()) {
            Class2 var12 = var9.poll();

            int root1 = var6[var12.var1];
            int root2 = var6[var12.var2];

            // If vertices are in different components, add the edge and union the sets
            if (!var7[root1].contains(var12.var2) && !var7[root2].contains(var12.var1)) {

                // Merge component of var2 into component of var1
                var7[root1].addAll(var7[root2]);

                // Update parent references for all vertices in the merged component
                var7[root1].forEach(vertex -> var6[vertex] = root1);

                // Add edge to the resulting minimum spanning forest
                method1(var8, var12.var1, var12.var2, var12.var3);

                // Update size of the current largest component
                var11 = var7[root1].size();
            }
        }
        return var8;
    }
}