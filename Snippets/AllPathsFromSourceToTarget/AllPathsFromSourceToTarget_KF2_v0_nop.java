package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;


public class AllPathsFromSourceToTarget {

    private final int v;

    static List<List<Integer>> nm = new ArrayList<>();
    private ArrayList<Integer>[] adjList;

    public AllPathsFromSourceToTarget(int vertices) {

        this.v = vertices;

        initAdjList();
    }

    private void initAdjList() {
        adjList = new ArrayList[v];

        for (int i = 0; i < v; i++) {
            adjList[i] = new ArrayList<>();
        }
    }

    public void addEdge(int u, int v) {
        adjList[u].add(v);
    }

    public void storeAllPaths(int s, int d) {
        boolean[] isVisited = new boolean[v];
        ArrayList<Integer> pathList = new ArrayList<>();

        pathList.add(s);
        storeAllPathsUtil(s, d, isVisited, pathList);
    }

    private void storeAllPathsUtil(Integer u, Integer d, boolean[] isVisited, List<Integer> localPathList) {

        if (u.equals(d)) {
            nm.add(new ArrayList<>(localPathList));
            return;
        }

        isVisited[u] = true;


        for (Integer i : adjList[u]) {
            if (!isVisited[i]) {
                localPathList.add(i);
                storeAllPathsUtil(i, d, isVisited, localPathList);

                localPathList.remove(i);
            }
        }

        isVisited[u] = false;
    }

    public static List<List<Integer>> allPathsFromSourceToTarget(int vertices, int[][] a, int source, int destination) {
        AllPathsFromSourceToTarget g = new AllPathsFromSourceToTarget(vertices);
        for (int[] i : a) {
            g.addEdge(i[0], i[1]);
        }
        g.storeAllPaths(source, destination);
        return nm;
    }
}
