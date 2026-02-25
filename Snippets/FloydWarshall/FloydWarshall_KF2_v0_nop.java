package com.thealgorithms.datastructures.graphs;


public class FloydWarshall {

    private int[][] distanceMatrix;
    private int numberofvertices;
    public static final int INFINITY = 999;


    public FloydWarshall(int numberofvertices) {
        distanceMatrix = new int[numberofvertices + 1][numberofvertices + 1];
        this.numberofvertices = numberofvertices;
    }


    public void floydwarshall(int[][] adjacencyMatrix) {
        for (int source = 1; source <= numberofvertices; source++) {
            System.arraycopy(adjacencyMatrix[source], 1, distanceMatrix[source], 1, numberofvertices);
        }
        for (int intermediate = 1; intermediate <= numberofvertices; intermediate++) {
            for (int source = 1; source <= numberofvertices; source++) {
                for (int destination = 1; destination <= numberofvertices; destination++) {
                    if (distanceMatrix[source][intermediate] + distanceMatrix[intermediate][destination] < distanceMatrix[source][destination]) {
                        distanceMatrix[source][destination] = distanceMatrix[source][intermediate] + distanceMatrix[intermediate][destination];
                    }
                }
            }
        }

        printDistanceMatrix();
    }


    private void printDistanceMatrix() {
        for (int source = 1; source <= numberofvertices; source++) {
            System.out.print("\t" + source);
        }
        System.out.println();

        for (int source = 1; source <= numberofvertices; source++) {
            System.out.print(source + "\t");
            for (int destination = 1; destination <= numberofvertices; destination++) {
                System.out.print(distanceMatrix[source][destination] + "\t");
            }
            System.out.println();
        }
    }

    public Object[] getDistanceMatrix() {
        return distanceMatrix;
    }
}
