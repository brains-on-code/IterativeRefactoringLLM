package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CircularLookScheduling {
    private int currentHeadPosition;
    private boolean isMovingUpwards;
    private final int maxCylinderIndex;

    public CircularLookScheduling(int initialHeadPosition, boolean initialDirectionUpwards, int maxCylinderIndex) {
        this.currentHeadPosition = initialHeadPosition;
        this.isMovingUpwards = initialDirectionUpwards;
        this.maxCylinderIndex = maxCylinderIndex;
    }

    public List<Integer> execute(List<Integer> cylinderRequests) {
        List<Integer> scheduledRequestOrder = new ArrayList<>();

        List<Integer> upwardRequests = new ArrayList<>();
        List<Integer> downwardRequests = new ArrayList<>();

        for (int requestedCylinder : cylinderRequests) {
            if (requestedCylinder >= 0 && requestedCylinder < maxCylinderIndex) {
                if (requestedCylinder > currentHeadPosition) {
                    upwardRequests.add(requestedCylinder);
                } else if (requestedCylinder < currentHeadPosition) {
                    downwardRequests.add(requestedCylinder);
                }
            }
        }

        Collections.sort(upwardRequests);
        Collections.sort(downwardRequests);

        if (isMovingUpwards) {
            scheduledRequestOrder.addAll(upwardRequests);
            scheduledRequestOrder.addAll(downwardRequests);
        } else {
            Collections.reverse(downwardRequests);
            scheduledRequestOrder.addAll(downwardRequests);

            Collections.reverse(upwardRequests);
            scheduledRequestOrder.addAll(upwardRequests);
        }

        if (!scheduledRequestOrder.isEmpty()) {
            currentHeadPosition = scheduledRequestOrder.get(scheduledRequestOrder.size() - 1);
        }

        return scheduledRequestOrder;
    }

    public int getHeadPosition() {
        return currentHeadPosition;
    }

    public boolean isMovingUpwards() {
        return isMovingUpwards;
    }
}