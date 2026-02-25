package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CircularScanScheduling {
    private int currentHeadPosition;
    private boolean isMovingUpwards;
    private final int totalCylinders;

    public CircularScanScheduling(int initialHeadPosition, boolean isMovingUpwards, int totalCylinders) {
        this.currentHeadPosition = initialHeadPosition;
        this.isMovingUpwards = isMovingUpwards;
        this.totalCylinders = totalCylinders;
    }

    public List<Integer> execute(List<Integer> requestQueue) {
        if (requestQueue.isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> sortedRequests = new ArrayList<>(requestQueue);
        Collections.sort(sortedRequests);

        List<Integer> scheduledRequests = new ArrayList<>();

        if (isMovingUpwards) {
            for (int requestCylinder : sortedRequests) {
                if (requestCylinder >= currentHeadPosition && requestCylinder < totalCylinders) {
                    scheduledRequests.add(requestCylinder);
                }
            }

            for (int requestCylinder : sortedRequests) {
                if (requestCylinder < currentHeadPosition) {
                    scheduledRequests.add(requestCylinder);
                }
            }
        } else {
            for (int i = sortedRequests.size() - 1; i >= 0; i--) {
                int requestCylinder = sortedRequests.get(i);
                if (requestCylinder <= currentHeadPosition) {
                    scheduledRequests.add(requestCylinder);
                }
            }

            for (int i = sortedRequests.size() - 1; i >= 0; i--) {
                int requestCylinder = sortedRequests.get(i);
                if (requestCylinder > currentHeadPosition) {
                    scheduledRequests.add(requestCylinder);
                }
            }
        }

        if (!scheduledRequests.isEmpty()) {
            currentHeadPosition = scheduledRequests.get(scheduledRequests.size() - 1);
        }

        return scheduledRequests;
    }

    public int getHeadPosition() {
        return currentHeadPosition;
    }

    public boolean isMovingUpwards() {
        return isMovingUpwards;
    }
}