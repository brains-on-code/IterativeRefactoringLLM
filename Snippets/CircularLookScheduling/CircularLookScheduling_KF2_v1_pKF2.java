package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CircularLookScheduling {

    private int currentPosition;
    private boolean movingUp;
    private final int maxCylinder;

    public CircularLookScheduling(int startPosition, boolean movingUp, int maxCylinder) {
        this.currentPosition = startPosition;
        this.movingUp = movingUp;
        this.maxCylinder = maxCylinder;
    }

    public List<Integer> execute(List<Integer> requests) {
        List<Integer> scheduledRequests = new ArrayList<>();
        List<Integer> upwardRequests = new ArrayList<>();
        List<Integer> downwardRequests = new ArrayList<>();

        for (int request : requests) {
            if (isWithinBounds(request)) {
                if (request > currentPosition) {
                    upwardRequests.add(request);
                } else if (request < currentPosition) {
                    downwardRequests.add(request);
                }
            }
        }

        Collections.sort(upwardRequests);
        Collections.sort(downwardRequests);

        if (movingUp) {
            scheduledRequests.addAll(upwardRequests);
            scheduledRequests.addAll(downwardRequests);
        } else {
            Collections.reverse(downwardRequests);
            scheduledRequests.addAll(downwardRequests);

            Collections.reverse(upwardRequests);
            scheduledRequests.addAll(upwardRequests);
        }

        updateCurrentPosition(scheduledRequests);

        return scheduledRequests;
    }

    private boolean isWithinBounds(int request) {
        return request >= 0 && request < maxCylinder;
    }

    private void updateCurrentPosition(List<Integer> scheduledRequests) {
        if (!scheduledRequests.isEmpty()) {
            currentPosition = scheduledRequests.get(scheduledRequests.size() - 1);
        }
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public boolean isMovingUp() {
        return movingUp;
    }
}