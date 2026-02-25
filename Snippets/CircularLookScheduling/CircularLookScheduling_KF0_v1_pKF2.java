package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Circular Look Scheduling (C-LOOK) is a disk scheduling algorithm similar to
 * C-SCAN. The disk arm moves in one direction to service requests but only up
 * to the furthest request in that direction. It then jumps directly to the
 * closest request on the opposite side, without traveling to the physical
 * ends of the disk.
 */
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
        List<Integer> result = new ArrayList<>();

        List<Integer> upRequests = new ArrayList<>();
        List<Integer> downRequests = new ArrayList<>();

        for (int request : requests) {
            if (isValidRequest(request)) {
                if (request > currentPosition) {
                    upRequests.add(request);
                } else if (request < currentPosition) {
                    downRequests.add(request);
                }
            }
        }

        Collections.sort(upRequests);
        Collections.sort(downRequests);

        if (movingUp) {
            result.addAll(upRequests);
            result.addAll(downRequests);
        } else {
            Collections.reverse(downRequests);
            result.addAll(downRequests);

            Collections.reverse(upRequests);
            result.addAll(upRequests);
        }

        if (!result.isEmpty()) {
            currentPosition = result.get(result.size() - 1);
        }

        return result;
    }

    private boolean isValidRequest(int request) {
        return request >= 0 && request < maxCylinder;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public boolean isMovingUp() {
        return movingUp;
    }
}