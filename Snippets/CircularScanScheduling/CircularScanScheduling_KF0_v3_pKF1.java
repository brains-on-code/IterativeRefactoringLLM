package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Circular Scan Scheduling (C-SCAN) is a disk scheduling algorithm that
 * works by moving the disk arm in one direction to service requests until
 * it reaches the end of the disk. Once it reaches the end, instead of reversing
 * direction like in the SCAN algorithm, the arm moves back to the starting point
 * without servicing any requests. This ensures a more uniform wait time for all
 * requests, especially those near the disk edges. The algorithm then continues in
 * the same direction, making it effective for balancing service time across all disk sectors.
 */
public class CircularScanScheduling {
    private int currentHeadPosition;
    private boolean isMovingTowardsHigherCylinders;
    private final int totalCylinders;

    public CircularScanScheduling(
            int initialHeadPosition,
            boolean isMovingTowardsHigherCylinders,
            int totalCylinders
    ) {
        this.currentHeadPosition = initialHeadPosition;
        this.isMovingTowardsHigherCylinders = isMovingTowardsHigherCylinders;
        this.totalCylinders = totalCylinders;
    }

    public List<Integer> execute(List<Integer> requestQueue) {
        if (requestQueue.isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> sortedRequestQueue = new ArrayList<>(requestQueue);
        Collections.sort(sortedRequestQueue);

        List<Integer> serviceSequence = new ArrayList<>();

        if (isMovingTowardsHigherCylinders) {
            for (int requestedCylinder : sortedRequestQueue) {
                if (requestedCylinder >= currentHeadPosition && requestedCylinder < totalCylinders) {
                    serviceSequence.add(requestedCylinder);
                }
            }

            for (int requestedCylinder : sortedRequestQueue) {
                if (requestedCylinder < currentHeadPosition) {
                    serviceSequence.add(requestedCylinder);
                }
            }
        } else {
            for (int index = sortedRequestQueue.size() - 1; index >= 0; index--) {
                int requestedCylinder = sortedRequestQueue.get(index);
                if (requestedCylinder <= currentHeadPosition) {
                    serviceSequence.add(requestedCylinder);
                }
            }

            for (int index = sortedRequestQueue.size() - 1; index >= 0; index--) {
                int requestedCylinder = sortedRequestQueue.get(index);
                if (requestedCylinder > currentHeadPosition) {
                    serviceSequence.add(requestedCylinder);
                }
            }
        }

        if (!serviceSequence.isEmpty()) {
            currentHeadPosition = serviceSequence.get(serviceSequence.size() - 1);
        }

        return serviceSequence;
    }

    public int getCurrentHeadPosition() {
        return currentHeadPosition;
    }

    public boolean isMovingTowardsHigherCylinders() {
        return isMovingTowardsHigherCylinders;
    }
}