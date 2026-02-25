package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.Scanner;

public final class LCA {
    private LCA() {
    }

    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();

        int v = SCANNER.nextInt();
        int e = v - 1;

        for (int i = 0; i < v; i++) {
            adj.add(new ArrayList<Integer>());
        }

        int to;
        int from;
        for (int i = 0; i < e; i++) {
            to = SCANNER.nextInt();
            from = SCANNER.nextInt();

            adj.get(to).add(from);
            adj.get(from).add(to);
        }

        int[] parent = new int[v];

        int[] depth = new int[v];

        dfs(adj, 0, -1, parent, depth);

        int v1 = SCANNER.nextInt();
        int v2 = SCANNER.nextInt();

        System.out.println(getLCA(v1, v2, depth, parent));
    }


    private static void dfs(ArrayList<ArrayList<Integer>> adj, int s, int p, int[] parent, int[] depth) {
        for (int adjacent : adj.get(s)) {
            if (adjacent != p) {
                parent[adjacent] = s;
                depth[adjacent] = 1 + depth[s];
                dfs(adj, adjacent, s, parent, depth);
            }
        }
    }


    private static int getLCA(int v1, int v2, int[] depth, int[] parent) {
        if (depth[v1] < depth[v2]) {
            int temp = v1;
            v1 = v2;
            v2 = temp;
        }
        while (depth[v1] != depth[v2]) {
            v1 = parent[v1];
        }
        if (v1 == v2) {
            return v1;
        }
        while (v1 != v2) {
            v1 = parent[v1];
            v2 = parent[v2];
        }
        return v1;
    }
}

