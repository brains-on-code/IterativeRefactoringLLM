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
    private int headPosition;
    private boolean movingTowardsHigherCylinders;
    private final int cylinderCount;

    public CircularScanScheduling(
            int initialHeadPosition,
            boolean movingTowardsHigherCylinders,
            int cylinderCount
    ) {
        this.headPosition = initialHeadPosition;
        this.movingTowardsHigherCylinders = movingTowardsHigherCylinders;
        this.cylinderCount = cylinderCount;
    }

    public List<Integer> execute(List<Integer> requestQueue) {
        if (requestQueue.isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> sortedRequests = new ArrayList<>(requestQueue);
        Collections.sort(sortedRequests);

        List<Integer> serviceOrder = new ArrayList<>();

        if (movingTowardsHigherCylinders) {
            for (int requestedCylinder : sortedRequests) {
                if (requestedCylinder >= headPosition && requestedCylinder < cylinderCount) {
                    serviceOrder.add(requestedCylinder);
                }
            }

            for (int requestedCylinder : sortedRequests) {
                if (requestedCylinder < headPosition) {
                    serviceOrder.add(requestedCylinder);
                }
            }
        } else {
            for (int i = sortedRequests.size() - 1; i >= 0; i--) {
                int requestedCylinder = sortedRequests.get(i);
                if (requestedCylinder <= headPosition) {
                    serviceOrder.add(requestedCylinder);
                }
            }

            for (int i = sortedRequests.size() - 1; i >= 0; i--) {
                int requestedCylinder = sortedRequests.get(i);
                if (requestedCylinder > headPosition) {
                    serviceOrder.add(requestedCylinder);
                }
            }
        }

        if (!serviceOrder.isEmpty()) {
            headPosition = serviceOrder.get(serviceOrder.size() - 1);
        }

        return serviceOrder;
    }

    public int getCurrentHeadPosition() {
        return headPosition;
    }

    public boolean isMovingTowardsHigherCylinders() {
        return movingTowardsHigherCylinders;
    }
}