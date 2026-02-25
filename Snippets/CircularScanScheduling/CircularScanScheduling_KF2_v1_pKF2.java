package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CircularScanScheduling {

    private int currentPosition;
    private boolean movingUp;
    private final int diskSize;

    public CircularScanScheduling(int startPosition, boolean movingUp, int diskSize) {
        this.currentPosition = startPosition;
        this.movingUp = movingUp;
        this.diskSize = diskSize;
    }

    public List<Integer> execute(List<Integer> requests) {
        if (requests.isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> sortedRequests = new ArrayList<>(requests);
        Collections.sort(sortedRequests);

        List<Integer> result = new ArrayList<>();

        if (movingUp) {
            result.addAll(getRequestsMovingUp(sortedRequests));
        } else {
            result.addAll(getRequestsMovingDown(sortedRequests));
        }

        if (!result.isEmpty()) {
            currentPosition = result.get(result.size() - 1);
        }

        return result;
    }

    private List<Integer> getRequestsMovingUp(List<Integer> sortedRequests) {
        List<Integer> result = new ArrayList<>();

        for (int request : sortedRequests) {
            if (request >= currentPosition && request < diskSize) {
                result.add(request);
            }
        }

        for (int request : sortedRequests) {
            if (request < currentPosition) {
                result.add(request);
            }
        }

        return result;
    }

    private List<Integer> getRequestsMovingDown(List<Integer> sortedRequests) {
        List<Integer> result = new ArrayList<>();

        for (int i = sortedRequests.size() - 1; i >= 0; i--) {
            int request = sortedRequests.get(i);
            if (request <= currentPosition) {
                result.add(request);
            }
        }

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