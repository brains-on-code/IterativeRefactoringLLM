package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CircularScanScheduling {
    private int headPosition;
    private boolean movingUpwards;
    private final int cylinderCount;

    public CircularScanScheduling(int initialHeadPosition, boolean movingUpwards, int cylinderCount) {
        this.headPosition = initialHeadPosition;
        this.movingUpwards = movingUpwards;
        this.cylinderCount = cylinderCount;
    }

    public List<Integer> execute(List<Integer> requestQueue) {
        if (requestQueue.isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> sortedRequestQueue = new ArrayList<>(requestQueue);
        Collections.sort(sortedRequestQueue);

        List<Integer> scheduledRequests = new ArrayList<>();

        if (movingUpwards) {
            for (int requestCylinder : sortedRequestQueue) {
                if (requestCylinder >= headPosition && requestCylinder < cylinderCount) {
                    scheduledRequests.add(requestCylinder);
                }
            }

            for (int requestCylinder : sortedRequestQueue) {
                if (requestCylinder < headPosition) {
                    scheduledRequests.add(requestCylinder);
                }
            }
        } else {
            for (int index = sortedRequestQueue.size() - 1; index >= 0; index--) {
                int requestCylinder = sortedRequestQueue.get(index);
                if (requestCylinder <= headPosition) {
                    scheduledRequests.add(requestCylinder);
                }
            }

            for (int index = sortedRequestQueue.size() - 1; index >= 0; index--) {
                int requestCylinder = sortedRequestQueue.get(index);
                if (requestCylinder > headPosition) {
                    scheduledRequests.add(requestCylinder);
                }
            }
        }

        if (!scheduledRequests.isEmpty()) {
            headPosition = scheduledRequests.get(scheduledRequests.size() - 1);
        }

        return scheduledRequests;
    }

    public int getHeadPosition() {
        return headPosition;
    }

    public boolean isMovingUpwards() {
        return movingUpwards;
    }
}