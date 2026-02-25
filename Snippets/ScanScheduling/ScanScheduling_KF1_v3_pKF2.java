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
public class ScanScheduler {

    /** Initial head position. */
    private final int headPosition;

    /** Maximum valid cylinder index (exclusive upper bound). */
    private final int maxCylinder;

    /** Scan direction: true = towards higher cylinders, false = towards lower cylinders. */
    private final boolean moveTowardsHigher;

    public ScanScheduler(int headPosition, boolean moveTowardsHigher, int maxCylinder) {
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
    public List<Integer> getSchedule(List<Integer> requests) {
        if (requests == null || requests.isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> schedule = new ArrayList<>();
        List<Integer> lowerCylinders = new ArrayList<>();
        List<Integer> higherOrEqualCylinders = new ArrayList<>();

        // Split requests into those below the head and those at/above the head
        for (int request : requests) {
            if (request < headPosition) {
                lowerCylinders.add(request);
            } else {
                higherOrEqualCylinders.add(request);
            }
        }

        Collections.sort(lowerCylinders);
        Collections.sort(higherOrEqualCylinders);

        if (moveTowardsHigher) {
            // Move towards higher cylinders first, then to the last cylinder, then reverse
            schedule.addAll(higherOrEqualCylinders);
            schedule.add(maxCylinder - 1);
            Collections.reverse(lowerCylinders);
            schedule.addAll(lowerCylinders);
        } else {
            // Move towards lower cylinders first, then to cylinder 0, then reverse
            Collections.reverse(lowerCylinders);
            schedule.addAll(lowerCylinders);
            schedule.add(0);
            schedule.addAll(higherOrEqualCylinders);
        }

        return schedule;
    }

    public int getHeadPosition() {
        return headPosition;
    }

    public boolean isMovingTowardsHigher() {
        return moveTowardsHigher;
    }
}