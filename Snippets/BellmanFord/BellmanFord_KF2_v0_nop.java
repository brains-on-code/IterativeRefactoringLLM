package com.thealgorithms.datastructures.graphs;

import java.util.Scanner;

class BellmanFord
{

    int vertex;
    int edge;
    private Edge[] edges;
    private int index = 0;

    BellmanFord(int v, int e) {
        vertex = v;
        edge = e;
        edges = new Edge[e];
    }

    class Edge {

        int u;
        int v;
        int w;


        Edge(int a, int b, int c) {
            u = a;
            v = b;
            w = c;
        }
    }


    void printPath(int[] p, int i) {
        if (p[i] == -1) {
            return;
        }
        printPath(p, p[i]);
        System.out.print(i + " ");
    }

    public static void main(String[] args) {
        BellmanFord obj = new BellmanFord(0, 0);
        obj.go();
    }

    public void go() {
        try (Scanner sc = new Scanner(System.in)) {
            int i;
            int v;
            int e;
            int u;
            int ve;
            int w;
            int j;
            int neg = 0;
            System.out.println("Enter no. of vertices and edges please");
            v = sc.nextInt();
            e = sc.nextInt();
            Edge[] arr = new Edge[e];
            System.out.println("Input edges");
            for (i = 0; i < e; i++) {
                u = sc.nextInt();
                ve = sc.nextInt();
                w = sc.nextInt();
                arr[i] = new Edge(u, ve, w);
            }
            int[] dist = new int[v];
            int[] p = new int[v];
            for (i = 0; i < v; i++) {
                dist[i] = Integer.MAX_VALUE;
            }
            dist[0] = 0;
            p[0] = -1;
            for (i = 0; i < v - 1; i++) {
                for (j = 0; j < e; j++) {
                    if (dist[arr[j].u] != Integer.MAX_VALUE && dist[arr[j].v] > dist[arr[j].u] + arr[j].w) {
                        dist[arr[j].v] = dist[arr[j].u] + arr[j].w;
                        p[arr[j].v] = arr[j].u;
                    }
                }
            }
            for (j = 0; j < e; j++) {
                if (dist[arr[j].u] != Integer.MAX_VALUE && dist[arr[j].v] > dist[arr[j].u] + arr[j].w) {
                    neg = 1;
                    System.out.println("Negative cycle");
                    break;
                }
            }
            if (neg == 0) {
                System.out.println("Distances are: ");
                for (i = 0; i < v; i++) {
                    System.out.println(i + " " + dist[i]);
                }
                System.out.println("Path followed:");
                for (i = 0; i < v; i++) {
                    System.out.print("0 ");
                    printPath(p, i);
                    System.out.println();
                }
            }
        }
    }


    public void show(int source, int end,
        Edge[] arr) {
        int i;
        int j;
        int v = vertex;
        int e = edge;
        int neg = 0;
        double[] dist = new double[v];
        int[] p = new int[v];
        for (i = 0; i < v; i++) {
            dist[i] = Integer.MAX_VALUE;
        }
        dist[source] = 0;
        p[source] = -1;
        for (i = 0; i < v - 1; i++) {
            for (j = 0; j < e; j++) {
                if ((int) dist[arr[j].u] != Integer.MAX_VALUE && dist[arr[j].v] > dist[arr[j].u] + arr[j].w) {
                    dist[arr[j].v] = dist[arr[j].u] + arr[j].w;
                    p[arr[j].v] = arr[j].u;
                }
            }
        }
        for (j = 0; j < e; j++) {
            if ((int) dist[arr[j].u] != Integer.MAX_VALUE && dist[arr[j].v] > dist[arr[j].u] + arr[j].w) {
                neg = 1;
                System.out.println("Negative cycle");
                break;
            }
        }
        if (neg == 0) {
            System.out.println("Distance is: " + dist[end]);
            System.out.println("Path followed:");
            System.out.print(source + " ");
            printPath(p, end);
            System.out.println();
        }
    }


    public void addEdge(int x, int y, int z) {
        edges[index++] = new Edge(x, y, z);
    }

    public Edge[] getEdgeArray() {
        return edges;
    }
}
