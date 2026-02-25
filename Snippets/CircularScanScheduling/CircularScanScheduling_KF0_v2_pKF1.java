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
    private boolean movingUpwards;
    private final int cylinderCount;

    public CircularScanScheduling(int initialHeadPosition, boolean movingUpwards, int cylinderCount) {
        this.headPosition = initialHeadPosition;
        this.movingUpwards = movingUpwards;
        this.cylinderCount = cylinderCount;
    }

    public List<Integer> execute(List<Integer> requestQueue) {
        if (requestQueue.isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> sortedRequests = new ArrayList<>(requestQueue);
        Collections.sort(sortedRequests);

        List<Integer> serviceOrder = new ArrayList<>();

        if (movingUpwards) {
            for (int requestCylinder : sortedRequests) {
                if (requestCylinder >= headPosition && requestCylinder < cylinderCount) {
                    serviceOrder.add(requestCylinder);
                }
            }

            for (int requestCylinder : sortedRequests) {
                if (requestCylinder < headPosition) {
                    serviceOrder.add(requestCylinder);
                }
            }
        } else {
            for (int index = sortedRequests.size() - 1; index >= 0; index--) {
                int requestCylinder = sortedRequests.get(index);
                if (requestCylinder <= headPosition) {
                    serviceOrder.add(requestCylinder);
                }
            }

            for (int index = sortedRequests.size() - 1; index >= 0; index--) {
                int requestCylinder = sortedRequests.get(index);
                if (requestCylinder > headPosition) {
                    serviceOrder.add(requestCylinder);
                }
            }
        }

        if (!serviceOrder.isEmpty()) {
            headPosition = serviceOrder.get(serviceOrder.size() - 1);
        }
        return serviceOrder;
    }

    public int getHeadPosition() {
        return headPosition;
    }

    public boolean isMovingUpwards() {
        return movingUpwards;
    }
}