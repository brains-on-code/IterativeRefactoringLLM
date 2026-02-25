package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CircularLookScheduling {

    private int currentPosition;
    private final boolean movingUp;
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

        List<Integer> scheduledOrder = buildSchedule(upRequests, downRequests);
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

    private void splitRequestsByDirection(
        List<Integer> requests,
        List<Integer> upRequests,
        List<Integer> downRequests
    ) {
        for (int request : requests) {
            if (request > currentPosition) {
                upRequests.add(request);
            } else if (request < currentPosition) {
                downRequests.add(request);
            }
        }
    }

    private List<Integer> buildSchedule(List<Integer> upRequests, List<Integer> downRequests) {
        List<Integer> schedule = new ArrayList<>();

        if (movingUp) {
            schedule.addAll(upRequests);
            schedule.addAll(downRequests);
        } else {
            Collections.reverse(downRequests);
            Collections.reverse(upRequests);
            schedule.addAll(downRequests);
            schedule.addAll(upRequests);
        }

        return schedule;
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