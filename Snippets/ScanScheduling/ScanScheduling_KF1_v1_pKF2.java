package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * SCAN (Elevator) disk scheduling algorithm.
 *
 * Given:
 * - an initial head position,
 * - a scan direction (toward higher or lower cylinder numbers),
 * - and the maximum cylinder index,
 *
 * this class computes the order in which disk I/O requests are serviced
 * according to the SCAN algorithm.
 */
public class Class1 {

    /** Current head position. */
    private int headPosition;

    /** Maximum cylinder index (disk size upper bound). */
    private int maxCylinder;

    /** Direction of head movement: true = towards higher cylinders, false = towards lower. */
    private boolean moveTowardsHigher;

    public Class1(int headPosition, boolean moveTowardsHigher, int maxCylinder) {
        this.headPosition = headPosition;
        this.moveTowardsHigher = moveTowardsHigher;
        this.maxCylinder = maxCylinder;
    }

    /**
     * Computes the SCAN scheduling order for the given list of requests.
     *
     * @param requests list of requested cylinder positions
     * @return ordered list of cylinder positions in the sequence they will be serviced
     */
    public List<Integer> method1(List<Integer> requests) {
        if (requests.isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> result = new ArrayList<>();
        List<Integer> leftOfHead = new ArrayList<>();
        List<Integer> rightOfHead = new ArrayList<>();

        // Split requests into those to the left and right of the current head position
        for (int request : requests) {
            if (request < headPosition) {
                leftOfHead.add(request);
            } else {
                rightOfHead.add(request);
            }
        }

        Collections.sort(leftOfHead);
        Collections.sort(rightOfHead);

        if (moveTowardsHigher) {
            // Move towards higher cylinders first, then reverse direction
            result.addAll(rightOfHead);
            // Go to the last cylinder before reversing direction
            result.add(maxCylinder - 1);
            Collections.reverse(leftOfHead);
            result.addAll(leftOfHead);
        } else {
            // Move towards lower cylinders first, then reverse direction
            Collections.reverse(leftOfHead);
            result.addAll(leftOfHead);
            // Go to cylinder 0 before reversing direction
            result.add(0);
            result.addAll(rightOfHead);
        }

        return result;
    }

    public int method2() {
        return headPosition;
    }

    public boolean method3() {
        return moveTowardsHigher;
    }
}