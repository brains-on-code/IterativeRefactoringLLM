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

        addRequestsInRange(sortedRequests, scheduledOrder, currentHead, maxCylinder, true);
        addRequestsInRange(sortedRequests, scheduledOrder, Integer.MIN_VALUE, currentHead, true);

        return scheduledOrder;
    }

    private List<Integer> scheduleMovingDown(List<Integer> sortedRequests) {
        List<Integer> scheduledOrder = new ArrayList<>();

        addRequestsInRange(sortedRequests, scheduledOrder, Integer.MIN_VALUE, currentHead, false);
        addRequestsInRange(sortedRequests, scheduledOrder, currentHead + 1, Integer.MAX_VALUE, false);

        return scheduledOrder;
    }

    private void addRequestsInRange(
        List<Integer> sortedRequests,
        List<Integer> scheduledOrder,
        int lowerBound,
        int upperBound,
        boolean ascending
    ) {
        if (ascending) {
            for (int request : sortedRequests) {
                if (request >= lowerBound && request < upperBound) {
                    scheduledOrder.add(request);
                }
            }
        } else {
            for (int i = sortedRequests.size() - 1; i >= 0; i--) {
                int request = sortedRequests.get(i);
                if (request >= lowerBound && request < upperBound) {
                    scheduledOrder.add(request);
                }
            }
        }
    }

    public int getCurrentHead() {
        return currentHead;
    }

    public boolean isMovingUp() {
        return movingUp;
    }
}