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

        splitRequestsByHeadPosition(requests, lowerCylinders, higherOrEqualCylinders);

        Collections.sort(lowerCylinders);
        Collections.sort(higherOrEqualCylinders);

        if (moveTowardsHigher) {
            scheduleWhenMovingTowardsHigher(schedule, lowerCylinders, higherOrEqualCylinders);
        } else {
            scheduleWhenMovingTowardsLower(schedule, lowerCylinders, higherOrEqualCylinders);
        }

        return schedule;
    }

    /**
     * Splits the incoming requests into those below the head position and those
     * at or above it.
     */
    private void splitRequestsByHeadPosition(
            List<Integer> requests,
            List<Integer> lowerCylinders,
            List<Integer> higherOrEqualCylinders
    ) {
        for (int request : requests) {
            if (request < headPosition) {
                lowerCylinders.add(request);
            } else {
                higherOrEqualCylinders.add(request);
            }
        }
    }

    /**
     * Builds the schedule when the head initially moves toward higher cylinders.
     *
     * Order:
     * 1. Service all requests at/above the head, in ascending order.
     * 2. Move to the last cylinder (maxCylinder - 1).
     * 3. Reverse direction and service remaining lower requests in descending order.
     */
    private void scheduleWhenMovingTowardsHigher(
            List<Integer> schedule,
            List<Integer> lowerCylinders,
            List<Integer> higherOrEqualCylinders
    ) {
        schedule.addAll(higherOrEqualCylinders);
        schedule.add(maxCylinder - 1);
        Collections.reverse(lowerCylinders);
        schedule.addAll(lowerCylinders);
    }

    /**
     * Builds the schedule when the head initially moves toward lower cylinders.
     *
     * Order:
     * 1. Service all lower requests in descending order.
     * 2. Move to cylinder 0.
     * 3. Reverse direction and service remaining higher requests in ascending order.
     */
    private void scheduleWhenMovingTowardsLower(
            List<Integer> schedule,
            List<Integer> lowerCylinders,
            List<Integer> higherOrEqualCylinders
    ) {
        Collections.reverse(lowerCylinders);
        schedule.addAll(lowerCylinders);
        schedule.add(0);
        schedule.addAll(higherOrEqualCylinders);
    }

    public int getHeadPosition() {
        return headPosition;
    }

    public boolean isMovingTowardsHigher() {
        return moveTowardsHigher;
    }
}