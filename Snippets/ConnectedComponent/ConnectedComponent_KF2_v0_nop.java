package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


class Graph<E extends Comparable<E>> {

    class Node {

        E name;

        Node(E name) {
            this.name = name;
        }
    }

    class Edge {

        Node startNode;
        Node endNode;

        Edge(Node startNode, Node endNode) {
            this.startNode = startNode;
            this.endNode = endNode;
        }
    }

    ArrayList<Edge> edgeList;
    ArrayList<Node> nodeList;

    Graph() {
        edgeList = new ArrayList<Edge>();
        nodeList = new ArrayList<Node>();
    }


    public void addEdge(E startNode, E endNode) {
        Node start = null;
        Node end = null;
        for (Node node : nodeList) {
            if (startNode.compareTo(node.name) == 0) {
                start = node;
            } else if (endNode.compareTo(node.name) == 0) {
                end = node;
            }
        }
        if (start == null) {
            start = new Node(startNode);
            nodeList.add(start);
        }
        if (end == null) {
            end = new Node(endNode);
            nodeList.add(end);
        }

        edgeList.add(new Edge(start, end));
    }


    public int countGraphs() {
        int count = 0;
        Set<Node> markedNodes = new HashSet<Node>();

        for (Node n : nodeList) {
            if (markedNodes.add(n)) {
                markedNodes.addAll(depthFirstSearch(n, new ArrayList<Node>()));
                count++;
            }
        }

        return count;
    }


    public ArrayList<Node> depthFirstSearch(Node n, ArrayList<Node> visited) {
        visited.add(n);
        for (Edge e : edgeList) {
            if (e.startNode.equals(n) && !visited.contains(e.endNode)) {
                depthFirstSearch(e.endNode, visited);
            }
        }
        return visited;
    }
}

public final class ConnectedComponent {
    private ConnectedComponent() {
    }

    public static void main(String[] args) {
        Graph<Character> graphChars = new Graph<>();

        graphChars.addEdge('a', 'b');
        graphChars.addEdge('a', 'e');
        graphChars.addEdge('b', 'e');
        graphChars.addEdge('b', 'c');
        graphChars.addEdge('c', 'd');
        graphChars.addEdge('d', 'a');

        graphChars.addEdge('x', 'y');
        graphChars.addEdge('x', 'z');

        graphChars.addEdge('w', 'w');

        Graph<Integer> graphInts = new Graph<>();

        graphInts.addEdge(1, 2);
        graphInts.addEdge(2, 3);
        graphInts.addEdge(2, 4);
        graphInts.addEdge(3, 5);

        graphInts.addEdge(7, 8);
        graphInts.addEdge(8, 10);
        graphInts.addEdge(10, 8);

        System.out.println("Amount of different char-graphs: " + graphChars.countGraphs());
        System.out.println("Amount of different int-graphs: " + graphInts.countGraphs());
    }
}
