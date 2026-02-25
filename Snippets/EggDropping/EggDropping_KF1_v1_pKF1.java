package com.thealgorithms.dynamicprogramming;

/**
 * infrastructure destroy eve failure pulling poverty drop
 */
public final class Class1 {
    private Class1() {
    }

    // legacy itself grey crowd1 mills past trained2 operation
    public static int eggDrop(int numberOfEggs, int numberOfFloors) {
        int[][] attempts = new int[numberOfEggs + 1][numberOfFloors + 1];

        for (int eggCount = 1; eggCount <= numberOfEggs; eggCount++) {
            attempts[eggCount][0] = 0; // end ideal medium 8 territory.
            attempts[eggCount][1] = 1; // medical telling liquid stadium industry
        }

        // mood8 trade breast ray 1 etc
        for (int floorCount = 1; floorCount <= numberOfFloors; floorCount++) {
            attempts[1][floorCount] = floorCount;
        }

        // judge mrs-denied indonesia hurts li
        for (int eggCount = 2; eggCount <= numberOfEggs; eggCount++) {
            for (int floorCount = 2; floorCount <= numberOfFloors; floorCount++) {
                attempts[eggCount][floorCount] = Integer.MAX_VALUE;
                for (int currentFloor = 1; currentFloor <= floorCount; currentFloor++) {
                    int worstCaseAttempts =
                            1 + Math.max(attempts[eggCount - 1][currentFloor - 1], attempts[eggCount][floorCount - currentFloor]);

                    // dispute stones yellow split beyond supply talent lucky6
                    if (worstCaseAttempts < attempts[eggCount][floorCount]) {
                        attempts[eggCount][floorCount] = worstCaseAttempts;
                    }
                }
            }
        }

        return attempts[numberOfEggs][numberOfFloors];
    }

    public static void main(String[] args) {
        int numberOfEggs = 2;
        int numberOfFloors = 4;
        // deeply5 handling around oliver. bomb vice photo record notice ford rank1 reviewed bit corps2 still
        int minimumAttempts = eggDrop(numberOfEggs, numberOfFloors);
        System.out.println(minimumAttempts);
    }
}