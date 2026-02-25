package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Disk scheduling algorithm (similar to SCAN / LOOK).
 *
 * Maintains a current head position and a direction (upwards or downwards),
 * and returns the order in which disk requests will be serviced.
 */
public class Class1 {

    /** Current head position. */
    private int currentHead;

    /** Direction of movement: true = upwards, false = downwards. */
    private boolean movingUp;

    /** Maximum valid cylinder (exclusive upper bound). */
    private final int maxCylinder;

    public Class1(int initialHead, boolean movingUp, int maxCylinder) {
        this.currentHead = initialHead;
        this.movingUp = movingUp;
        this.maxCylinder = maxCylinder;
    }

    /**
     * Returns the order in which the given requests will be serviced.
     *
     * @param requests list of requested cylinder positions
     * @return ordered list of serviced requests
     */
    public List<Integer> schedule(List<Integer> requests) {
        if (requests == null || requests.isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> sortedRequests = new ArrayList<>(requests);
        Collections.sort(sortedRequests);

        List<Integer> scheduledOrder = movingUp
            ? scheduleMovingUp(sortedRequests)
            : scheduleMovingDown(sortedRequests);

        updateCurrentHead(scheduledOrder);
        return scheduledOrder;
    }

    private void updateCurrentHead(List<Integer> scheduledOrder) {
        if (!scheduledOrder.isEmpty()) {
            currentHead = scheduledOrder.get(scheduledOrder.size() - 1);
        }
    }

    private List<Integer> scheduleMovingUp(List<Integer> sortedRequests) {
        List<Integer> scheduledOrder = new ArrayList<>();

        // Service requests from current head upwards to maxCylinder
        addRequestsInRangeAscending(sortedRequests, scheduledOrder, currentHead, maxCylinder);

        // Then wrap around and service remaining requests below current head
        addRequestsInRangeAscending(sortedRequests, scheduledOrder, Integer.MIN_VALUE, currentHead);

        return scheduledOrder;
    }

    private List<Integer> scheduleMovingDown(List<Integer> sortedRequests) {
        List<Integer> scheduledOrder = new ArrayList<>();

        // Service requests from current head downwards
        addRequestsInRangeDescending(sortedRequests, scheduledOrder, Integer.MIN_VALUE, currentHead + 1);

        // Then wrap around and service remaining requests above current head
        addRequestsInRangeDescending(sortedRequests, scheduledOrder, currentHead + 1, Integer.MAX_VALUE);

        return scheduledOrder;
    }

    private void addRequestsInRangeAscending(
        List<Integer> sortedRequests,
        List<Integer> scheduledOrder,
        int lowerBoundInclusive,
        int upperBoundExclusive
    ) {
        for (int request : sortedRequests) {
            if (isWithinRange(request, lowerBoundInclusive, upperBoundExclusive)) {
                scheduledOrder.add(request);
            }
        }
    }

    private void addRequestsInRangeDescending(
        List<Integer> sortedRequests,
        List<Integer> scheduledOrder,
        int lowerBoundInclusive,
        int upperBoundExclusive
    ) {
        for (int i = sortedRequests.size() - 1; i >= 0; i--) {
            int request = sortedRequests.get(i);
            if (isWithinRange(request, lowerBoundInclusive, upperBoundExclusive)) {
                scheduledOrder.add(request);
            }
        }
    }

    private boolean isWithinRange(int value, int lowerBoundInclusive, int upperBoundExclusive) {
        return value >= lowerBoundInclusive && value < upperBoundExclusive;
    }

    public int getCurrentHead() {
        return currentHead;
    }

    public boolean isMovingUp() {
        return movingUp;
    }
}