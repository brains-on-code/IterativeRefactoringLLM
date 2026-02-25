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
        List<Integer> validRequests = filterValidRequests(requests);
        List<Integer> upRequests = new ArrayList<>();
        List<Integer> downRequests = new ArrayList<>();

        splitRequestsByDirection(validRequests, upRequests, downRequests);

        Collections.sort(upRequests);
        Collections.sort(downRequests);

        List<Integer> scheduledOrder = scheduleRequests(upRequests, downRequests);

        updateCurrentPosition(scheduledOrder);

        return scheduledOrder;
    }

    private List<Integer> filterValidRequests(List<Integer> requests) {
        List<Integer> validRequests = new ArrayList<>();
        for (int request : requests) {
            if (isWithinBounds(request)) {
                validRequests.add(request);
            }
        }
        return validRequests;
    }

    private boolean isWithinBounds(int request) {
        return request >= 0 && request < maxCylinder;
    }

    private void splitRequestsByDirection(List<Integer> requests, List<Integer> upRequests, List<Integer> downRequests) {
        for (int request : requests) {
            if (request > currentPosition) {
                upRequests.add(request);
            } else if (request < currentPosition) {
                downRequests.add(request);
            }
        }
    }

    private List<Integer> scheduleRequests(List<Integer> upRequests, List<Integer> downRequests) {
        List<Integer> result = new ArrayList<>();

        if (movingUp) {
            result.addAll(upRequests);
            result.addAll(downRequests);
        } else {
            Collections.reverse(downRequests);
            Collections.reverse(upRequests);
            result.addAll(downRequests);
            result.addAll(upRequests);
        }

        return result;
    }

    private void updateCurrentPosition(List<Integer> scheduledOrder) {
        if (!scheduledOrder.isEmpty()) {
            currentPosition = scheduledOrder.get(scheduledOrder.size() - 1);
        }
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public boolean isMovingUp() {
        return movingUp;
    }
}