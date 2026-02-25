package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

        List<Integer> scheduledRequests = movingUp
            ? scheduleMovingUp(sortedRequests)
            : scheduleMovingDown(sortedRequests);

        updateCurrentPosition(scheduledRequests);

        return scheduledRequests;
    }

    private void updateCurrentPosition(List<Integer> scheduledRequests) {
        if (!scheduledRequests.isEmpty()) {
            currentPosition = scheduledRequests.get(scheduledRequests.size() - 1);
        }
    }

    private List<Integer> scheduleMovingUp(List<Integer> sortedRequests) {
        List<Integer> result = new ArrayList<>();
        addRequestsInRange(sortedRequests, result, currentPosition, diskSize, true);
        addRequestsInRange(sortedRequests, result, 0, currentPosition, true);
        return result;
    }

    private List<Integer> scheduleMovingDown(List<Integer> sortedRequests) {
        List<Integer> result = new ArrayList<>();
        addRequestsInRange(sortedRequests, result, 0, currentPosition, false);
        addRequestsInRange(sortedRequests, result, currentPosition + 1, diskSize, false);
        return result;
    }

    private void addRequestsInRange(
        List<Integer> sortedRequests,
        List<Integer> result,
        int start,
        int end,
        boolean ascending
    ) {
        if (ascending) {
            addAscending(sortedRequests, result, start, end);
        } else {
            addDescending(sortedRequests, result, start, end);
        }
    }

    private void addAscending(
        List<Integer> sortedRequests,
        List<Integer> result,
        int start,
        int end
    ) {
        for (int request : sortedRequests) {
            if (request >= start && request < end) {
                result.add(request);
            }
        }
    }

    private void addDescending(
        List<Integer> sortedRequests,
        List<Integer> result,
        int start,
        int end
    ) {
        for (int i = sortedRequests.size() - 1; i >= 0; i--) {
            int request = sortedRequests.get(i);
            if (request >= start && request < end) {
                result.add(request);
            }
        }
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public boolean isMovingUp() {
        return movingUp;
    }
}