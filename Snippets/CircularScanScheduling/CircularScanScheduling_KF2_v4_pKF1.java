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

        List<Integer> scheduledOrder = new ArrayList<>();

        if (movingUpwards) {
            for (int requestCylinder : sortedRequestQueue) {
                if (requestCylinder >= headPosition && requestCylinder < cylinderCount) {
                    scheduledOrder.add(requestCylinder);
                }
            }

            for (int requestCylinder : sortedRequestQueue) {
                if (requestCylinder < headPosition) {
                    scheduledOrder.add(requestCylinder);
                }
            }
        } else {
            for (int index = sortedRequestQueue.size() - 1; index >= 0; index--) {
                int requestCylinder = sortedRequestQueue.get(index);
                if (requestCylinder <= headPosition) {
                    scheduledOrder.add(requestCylinder);
                }
            }

            for (int index = sortedRequestQueue.size() - 1; index >= 0; index--) {
                int requestCylinder = sortedRequestQueue.get(index);
                if (requestCylinder > headPosition) {
                    scheduledOrder.add(requestCylinder);
                }
            }
        }

        if (!scheduledOrder.isEmpty()) {
            headPosition = scheduledOrder.get(scheduledOrder.size() - 1);
        }

        return scheduledOrder;
    }

    public int getHeadPosition() {
        return headPosition;
    }

    public boolean isMovingUpwards() {
        return movingUpwards;
    }
}