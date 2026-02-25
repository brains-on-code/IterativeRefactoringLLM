package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A class that counts the number of different connected components in a graph
 *
 * @author Lukas Keul, Florian Mercks
 */
class Graph<E extends Comparable<E>> {

    class Node {

        E value;

        Node(E value) {
            this.value = value;
        }
    }

    class Edge {

        Node source;
        Node destination;

        Edge(Node source, Node destination) {
            this.source = source;
            this.destination = destination;
        }
    }

    List<Edge> edges;
    List<Node> nodes;

    Graph() {
        edges = new ArrayList<>();
        nodes = new ArrayList<>();
    }

    /**
     * Adds a new Edge to the graph. If the nodes aren't yet in nodes, they
     * will be added to it.
     *
     * @param sourceValue the starting Node value for the edge
     * @param destinationValue the ending Node value for the edge
     */
    public void addEdge(E sourceValue, E destinationValue) {
        Node sourceNode = null;
        Node destinationNode = null;

        for (Node node : nodes) {
            if (sourceValue.compareTo(node.value) == 0) {
                sourceNode = node;
            } else if (destinationValue.compareTo(node.value) == 0) {
                destinationNode = node;
            }
        }

        if (sourceNode == null) {
            sourceNode = new Node(sourceValue);
            nodes.add(sourceNode);
        }
        if (destinationNode == null) {
            destinationNode = new Node(destinationValue);
            nodes.add(destinationNode);
        }

        edges.add(new Edge(sourceNode, destinationNode));
    }

    /**
     * Main method used for counting the connected components. Iterates through
     * the list of nodes to do a depth first search to get all nodes of the
     * graph from the current node. These nodes are added to the set
     * visitedNodes and will be ignored if they are chosen again in the nodes list.
     *
     * @return the number of connected components
     */
    public int countConnectedComponents() {
        int componentCount = 0;
        Set<Node> visitedNodes = new HashSet<>();

        for (Node node : nodes) {
            if (visitedNodes.add(node)) {
                visitedNodes.addAll(depthFirstSearch(node, new ArrayList<>()));
                componentCount++;
            }
        }

        return componentCount;
    }

    /**
     * Implementation of depth first search.
     *
     * @param current the current visiting node
     * @param visited a list of already visited nodes in the depth first search
     * @return a list of visited nodes
     */
    public List<Node> depthFirstSearch(Node current, List<Node> visited) {
        visited.add(current);
        for (Edge edge : edges) {
            if (edge.source.equals(current) && !visited.contains(edge.destination)) {
                depthFirstSearch(edge.destination, visited);
            }
        }
        return visited;
    }
}

public final class ConnectedComponent {
    private ConnectedComponent() {
    }

    public static void main(String[] args) {
        Graph<Character> characterGraph = new Graph<>();

        // Graph 1
        characterGraph.addEdge('a', 'b');
        characterGraph.addEdge('a', 'e');
        characterGraph.addEdge('b', 'e');
        characterGraph.addEdge('b', 'c');
        characterGraph.addEdge('c', 'd');
        characterGraph.addEdge('d', 'a');

        characterGraph.addEdge('x', 'y');
        characterGraph.addEdge('x', 'z');

        characterGraph.addEdge('w', 'w');

        Graph<Integer> integerGraph = new Graph<>();

        // Graph 2
        integerGraph.addEdge(1, 2);
        integerGraph.addEdge(2, 3);
        integerGraph.addEdge(2, 4);
        integerGraph.addEdge(3, 5);

        integerGraph.addEdge(7, 8);
        integerGraph.addEdge(8, 10);
        integerGraph.addEdge(10, 8);

        System.out.println("Amount of different char-graphs: " + characterGraph.countConnectedComponents());
        System.out.println("Amount of different int-graphs: " + integerGraph.countConnectedComponents());
    }
}