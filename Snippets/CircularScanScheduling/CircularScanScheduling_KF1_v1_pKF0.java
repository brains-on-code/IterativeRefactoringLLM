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
        if (requests.isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> sortedRequests = new ArrayList<>(requests);
        Collections.sort(sortedRequests);

        List<Integer> scheduledOrder = new ArrayList<>();

        if (movingUp) {
            // Service requests from currentHead upwards (but below maxCylinder)
            for (int request : sortedRequests) {
                if (request >= currentHead && request < maxCylinder) {
                    scheduledOrder.add(request);
                }
            }

            // Then wrap and service remaining requests below currentHead
            for (int request : sortedRequests) {
                if (request < currentHead) {
                    scheduledOrder.add(request);
                }
            }
        } else {
            // Service requests from currentHead downwards
            for (int i = sortedRequests.size() - 1; i >= 0; i--) {
                int request = sortedRequests.get(i);
                if (request <= currentHead) {
                    scheduledOrder.add(request);
                }
            }

            // Then wrap and service remaining requests above currentHead
            for (int i = sortedRequests.size() - 1; i >= 0; i--) {
                int request = sortedRequests.get(i);
                if (request > currentHead) {
                    scheduledOrder.add(request);
                }
            }
        }

        if (!scheduledOrder.isEmpty()) {
            currentHead = scheduledOrder.get(scheduledOrder.size() - 1);
        }

        return scheduledOrder;
    }

    public int getCurrentHead() {
        return currentHead;
    }

    public boolean isMovingUp() {
        return movingUp;
    }
}