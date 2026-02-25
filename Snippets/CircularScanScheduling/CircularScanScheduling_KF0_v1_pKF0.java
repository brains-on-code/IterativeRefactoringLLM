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

    private int currentPosition;
    private final boolean movingUp;
    private final int diskSize;

    public CircularScanScheduling(int startPosition, boolean movingUp, int diskSize) {
        this.currentPosition = startPosition;
        this.movingUp = movingUp;
        this.diskSize = diskSize;
    }

    public List<Integer> execute(List<Integer> requests) {
        if (requests == null || requests.isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> sortedRequests = new ArrayList<>(requests);
        Collections.sort(sortedRequests);

        List<Integer> scheduledOrder = movingUp
            ? scheduleMovingUp(sortedRequests)
            : scheduleMovingDown(sortedRequests);

        if (!scheduledOrder.isEmpty()) {
            currentPosition = scheduledOrder.get(scheduledOrder.size() - 1);
        }

        return scheduledOrder;
    }

    private List<Integer> scheduleMovingUp(List<Integer> sortedRequests) {
        List<Integer> result = new ArrayList<>();

        // First, service requests from currentPosition upwards
        for (int request : sortedRequests) {
            if (request >= currentPosition && request < diskSize) {
                result.add(request);
            }
        }

        // Then, wrap around and service remaining lower requests
        for (int request : sortedRequests) {
            if (request < currentPosition) {
                result.add(request);
            }
        }

        return result;
    }

    private List<Integer> scheduleMovingDown(List<Integer> sortedRequests) {
        List<Integer> result = new ArrayList<>();

        // First, service requests from currentPosition downwards
        for (int i = sortedRequests.size() - 1; i >= 0; i--) {
            int request = sortedRequests.get(i);
            if (request <= currentPosition) {
                result.add(request);
            }
        }

        // Then, wrap around and service remaining higher requests
        for (int i = sortedRequests.size() - 1; i >= 0; i--) {
            int request = sortedRequests.get(i);
            if (request > currentPosition) {
                result.add(request);
            }
        }

        return result;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public boolean isMovingUp() {
        return movingUp;
    }
}