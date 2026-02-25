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

        partitionRequests(requests, lowerCylinders, higherOrEqualCylinders);

        Collections.sort(lowerCylinders);
        Collections.sort(higherOrEqualCylinders);

        if (moveTowardsHigher) {
            scheduleMovingTowardsHigher(schedule, lowerCylinders, higherOrEqualCylinders);
        } else {
            scheduleMovingTowardsLower(schedule, lowerCylinders, higherOrEqualCylinders);
        }

        return schedule;
    }

    private void partitionRequests(
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

    private void scheduleMovingTowardsHigher(
            List<Integer> schedule,
            List<Integer> lowerCylinders,
            List<Integer> higherOrEqualCylinders
    ) {
        schedule.addAll(higherOrEqualCylinders);
        schedule.add(maxCylinder - 1);
        Collections.reverse(lowerCylinders);
        schedule.addAll(lowerCylinders);
    }

    private void scheduleMovingTowardsLower(
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