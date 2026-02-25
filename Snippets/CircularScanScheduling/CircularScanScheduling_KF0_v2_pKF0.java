package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Circular Scan Scheduling (C-SCAN) is a disk scheduling algorithm that
 * moves the disk arm in one direction to service requests until it reaches
 * the end of the disk. Once it reaches the end, instead of reversing
 * direction like in the SCAN algorithm, the arm moves back to the starting
 * point without servicing any requests. This ensures a more uniform wait
 * time for all requests, especially those near the disk edges.
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
        List<Integer> scheduled = new ArrayList<>();

        // Service requests from currentPosition upwards
        for (int request : sortedRequests) {
            if (isWithinUpperRange(request)) {
                scheduled.add(request);
            }
        }

        // Wrap around and service remaining lower requests
        for (int request : sortedRequests) {
            if (request < currentPosition) {
                scheduled.add(request);
            }
        }

        return scheduled;
    }

    private boolean isWithinUpperRange(int request) {
        return request >= currentPosition && request < diskSize;
    }

    private List<Integer> scheduleMovingDown(List<Integer> sortedRequests) {
        List<Integer> scheduled = new ArrayList<>();

        // Service requests from currentPosition downwards
        for (int i = sortedRequests.size() - 1; i >= 0; i--) {
            int request = sortedRequests.get(i);
            if (request <= currentPosition) {
                scheduled.add(request);
            }
        }

        // Wrap around and service remaining higher requests
        for (int i = sortedRequests.size() - 1; i >= 0; i--) {
            int request = sortedRequests.get(i);
            if (request > currentPosition) {
                scheduled.add(request);
            }
        }

        return scheduled;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public boolean isMovingUp() {
        return movingUp;
    }
}