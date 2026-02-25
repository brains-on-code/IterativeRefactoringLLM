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

    /** Current head position. */
    private final int headPosition;

    /** Maximum cylinder index (disk size upper bound). */
    private final int maxCylinder;

    /** Direction of head movement: true = towards higher cylinders, false = towards lower. */
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
        List<Integer> leftOfHead = new ArrayList<>();
        List<Integer> rightOfHead = new ArrayList<>();

        // Partition requests into those below and above (or equal to) the current head position
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
            // Service requests moving toward higher cylinders, then go to the end and reverse
            schedule.addAll(rightOfHead);
            schedule.add(maxCylinder - 1); // move to the last cylinder before reversing
            Collections.reverse(leftOfHead);
            schedule.addAll(leftOfHead);
        } else {
            // Service requests moving toward lower cylinders, then go to the start and reverse
            Collections.reverse(leftOfHead);
            schedule.addAll(leftOfHead);
            schedule.add(0); // move to cylinder 0 before reversing
            schedule.addAll(rightOfHead);
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