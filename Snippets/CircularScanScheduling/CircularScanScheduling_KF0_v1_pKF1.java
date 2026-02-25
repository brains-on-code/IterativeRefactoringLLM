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
    private boolean isMovingUpwards;
    private final int totalCylinders;

    public CircularScanScheduling(int startHeadPosition, boolean isMovingUpwards, int totalCylinders) {
        this.currentHeadPosition = startHeadPosition;
        this.isMovingUpwards = isMovingUpwards;
        this.totalCylinders = totalCylinders;
    }

    public List<Integer> execute(List<Integer> requestQueue) {
        if (requestQueue.isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> sortedRequestQueue = new ArrayList<>(requestQueue);
        Collections.sort(sortedRequestQueue);

        List<Integer> serviceOrder = new ArrayList<>();

        if (isMovingUpwards) {
            for (int requestCylinder : sortedRequestQueue) {
                if (requestCylinder >= currentHeadPosition && requestCylinder < totalCylinders) {
                    serviceOrder.add(requestCylinder);
                }
            }

            for (int requestCylinder : sortedRequestQueue) {
                if (requestCylinder < currentHeadPosition) {
                    serviceOrder.add(requestCylinder);
                }
            }
        } else {
            for (int index = sortedRequestQueue.size() - 1; index >= 0; index--) {
                int requestCylinder = sortedRequestQueue.get(index);
                if (requestCylinder <= currentHeadPosition) {
                    serviceOrder.add(requestCylinder);
                }
            }

            for (int index = sortedRequestQueue.size() - 1; index >= 0; index--) {
                int requestCylinder = sortedRequestQueue.get(index);
                if (requestCylinder > currentHeadPosition) {
                    serviceOrder.add(requestCylinder);
                }
            }
        }

        if (!serviceOrder.isEmpty()) {
            currentHeadPosition = serviceOrder.get(serviceOrder.size() - 1);
        }
        return serviceOrder;
    }

    public int getCurrentHeadPosition() {
        return currentHeadPosition;
    }

    public boolean isMovingUpwards() {
        return isMovingUpwards;
    }
}