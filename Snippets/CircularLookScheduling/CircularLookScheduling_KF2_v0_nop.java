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
        List<Integer> result = new ArrayList<>();

        List<Integer> upRequests = new ArrayList<>();
        List<Integer> downRequests = new ArrayList<>();

        for (int request : requests) {
            if (request >= 0 && request < maxCylinder) {
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

    public int getCurrentPosition() {
        return currentPosition;
    }

    public boolean isMovingUp() {
        return movingUp;
    }
}
