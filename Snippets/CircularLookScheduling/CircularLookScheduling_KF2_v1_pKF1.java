package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CircularLookScheduling {
    private int currentHeadPosition;
    private boolean isMovingUpwards;
    private final int maxCylinderIndex;

    public CircularLookScheduling(int startHeadPosition, boolean startMovingUpwards, int maxCylinderIndex) {
        this.currentHeadPosition = startHeadPosition;
        this.isMovingUpwards = startMovingUpwards;
        this.maxCylinderIndex = maxCylinderIndex;
    }

    public List<Integer> execute(List<Integer> cylinderRequests) {
        List<Integer> scheduledOrder = new ArrayList<>();

        List<Integer> upwardRequests = new ArrayList<>();
        List<Integer> downwardRequests = new ArrayList<>();

        for (int request : cylinderRequests) {
            if (request >= 0 && request < maxCylinderIndex) {
                if (request > currentHeadPosition) {
                    upwardRequests.add(request);
                } else if (request < currentHeadPosition) {
                    downwardRequests.add(request);
                }
            }
        }

        Collections.sort(upwardRequests);
        Collections.sort(downwardRequests);

        if (isMovingUpwards) {
            scheduledOrder.addAll(upwardRequests);
            scheduledOrder.addAll(downwardRequests);
        } else {
            Collections.reverse(downwardRequests);
            scheduledOrder.addAll(downwardRequests);

            Collections.reverse(upwardRequests);
            scheduledOrder.addAll(upwardRequests);
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