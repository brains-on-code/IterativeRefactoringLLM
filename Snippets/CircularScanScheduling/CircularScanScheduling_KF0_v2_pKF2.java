package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Circular Scan Scheduling (C-SCAN) is a disk scheduling algorithm that
 * moves the disk arm in one direction to service requests until it reaches
 * the end of the disk. Once it reaches the end, the arm returns to the
 * beginning without servicing any requests, then continues in the same
 * direction. This provides more uniform wait times, especially for requests
 * near the disk edges.
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

        List<Integer> result = movingUp
            ? processMovingUp(sortedRequests)
            : processMovingDown(sortedRequests);

        if (!result.isEmpty()) {
            currentPosition = result.get(result.size() - 1);
        }

        return result;
    }

    /**
     * Processes requests when the disk arm is moving towards higher cylinder numbers.
     * It first services all requests at or beyond the current position up to diskSize,
     * then wraps around and services the remaining lower-numbered requests.
     */
    private List<Integer> processMovingUp(List<Integer> sortedRequests) {
        List<Integer> result = new ArrayList<>();

        // Service requests from current position up to the end of the disk
        for (int request : sortedRequests) {
            if (request >= currentPosition && request < diskSize) {
                result.add(request);
            }
        }

        // Wrap around: service remaining requests from the beginning up to current position
        for (int request : sortedRequests) {
            if (request < currentPosition) {
                result.add(request);
            }
        }

        return result;
    }

    /**
     * Processes requests when the disk arm is moving towards lower cylinder numbers.
     * It first services all requests at or below the current position down to 0,
     * then wraps around and services the remaining higher-numbered requests.
     */
    private List<Integer> processMovingDown(List<Integer> sortedRequests) {
        List<Integer> result = new ArrayList<>();

        // Service requests from current position down to the start of the disk
        for (int i = sortedRequests.size() - 1; i >= 0; i--) {
            int request = sortedRequests.get(i);
            if (request <= currentPosition) {
                result.add(request);
            }
        }

        // Wrap around: service remaining requests from the end down to just above current position
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