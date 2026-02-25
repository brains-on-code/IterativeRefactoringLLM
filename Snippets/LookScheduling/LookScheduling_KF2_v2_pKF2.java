package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LookScheduling {

    private final int maxTrack;
    private final int currentPosition;
    private boolean movingUp;
    private int farthestPosition;

    public LookScheduling(int startPosition, boolean initialDirection, int maxTrack) {
        this.currentPosition = startPosition;
        this.movingUp = initialDirection;
        this.maxTrack = maxTrack;
    }

    public List<Integer> execute(List<Integer> requests) {
        List<Integer> scheduledOrder = new ArrayList<>();
        List<Integer> lowerRequests = new ArrayList<>();
        List<Integer> upperRequests = new ArrayList<>();

        for (int request : requests) {
            if (!isWithinBounds(request)) {
                continue;
            }
            if (request < currentPosition) {
                lowerRequests.add(request);
            } else {
                upperRequests.add(request);
            }
        }

        Collections.sort(lowerRequests);
        Collections.sort(upperRequests);

        if (movingUp) {
            processUpwardFirst(scheduledOrder, lowerRequests, upperRequests);
        } else {
            processDownwardFirst(scheduledOrder, lowerRequests, upperRequests);
        }

        return scheduledOrder;
    }

    private boolean isWithinBounds(int request) {
        return request >= 0 && request < maxTrack;
    }

    private void processUpwardFirst(
            List<Integer> scheduledOrder,
            List<Integer> lowerRequests,
            List<Integer> upperRequests
    ) {
        scheduledOrder.addAll(upperRequests);
        if (!upperRequests.isEmpty()) {
            farthestPosition = upperRequests.get(upperRequests.size() - 1);
        }

        movingUp = false;

        Collections.reverse(lowerRequests);
        scheduledOrder.addAll(lowerRequests);
        if (!lowerRequests.isEmpty()) {
            farthestPosition = Math.max(farthestPosition, lowerRequests.get(0));
        }
    }

    private void processDownwardFirst(
            List<Integer> scheduledOrder,
            List<Integer> lowerRequests,
            List<Integer> upperRequests
    ) {
        Collections.reverse(lowerRequests);
        scheduledOrder.addAll(lowerRequests);
        if (!lowerRequests.isEmpty()) {
            farthestPosition = lowerRequests.get(0);
        }

        movingUp = true;

        scheduledOrder.addAll(upperRequests);
        if (!upperRequests.isEmpty()) {
            farthestPosition = Math.max(farthestPosition, upperRequests.get(upperRequests.size() - 1));
        }
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public boolean isMovingUp() {
        return movingUp;
    }

    public int getFarthestPosition() {
        return farthestPosition;
    }
}