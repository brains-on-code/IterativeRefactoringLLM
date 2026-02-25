package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CircularScanScheduling {
    private int currentHeadPosition;
    private boolean isMovingUpwards;
    private final int totalCylinders;

    public CircularScanScheduling(int startHeadPosition, boolean isMovingUpwards, int totalCylinders) {
        this.currentHeadPosition = startHeadPosition;
        this.isMovingUpwards = isMovingUpwards;
        this.totalCylinders = totalCylinders;
    }

    public List<Integer> execute(List<Integer> pendingRequests) {
        if (pendingRequests.isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> sortedRequests = new ArrayList<>(pendingRequests);
        Collections.sort(sortedRequests);

        List<Integer> scheduledOrder = new ArrayList<>();

        if (isMovingUpwards) {
            for (int requestCylinder : sortedRequests) {
                if (requestCylinder >= currentHeadPosition && requestCylinder < totalCylinders) {
                    scheduledOrder.add(requestCylinder);
                }
            }

            for (int requestCylinder : sortedRequests) {
                if (requestCylinder < currentHeadPosition) {
                    scheduledOrder.add(requestCylinder);
                }
            }
        } else {
            for (int index = sortedRequests.size() - 1; index >= 0; index--) {
                int requestCylinder = sortedRequests.get(index);
                if (requestCylinder <= currentHeadPosition) {
                    scheduledOrder.add(requestCylinder);
                }
            }

            for (int index = sortedRequests.size() - 1; index >= 0; index--) {
                int requestCylinder = sortedRequests.get(index);
                if (requestCylinder > currentHeadPosition) {
                    scheduledOrder.add(requestCylinder);
                }
            }
        }

        if (!scheduledOrder.isEmpty()) {
            currentHeadPosition = scheduledOrder.get(scheduledOrder.size() - 1);
        }

        return scheduledOrder;
    }

    public int getCurrentHeadPosition() {
        return currentHeadPosition;
    }

    public boolean isMovingUpwards() {
        return isMovingUpwards;
    }
}