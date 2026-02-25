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
        List<Integer> scheduledCylinders = new ArrayList<>();

        List<Integer> upwardRequests = new ArrayList<>();
        List<Integer> downwardRequests = new ArrayList<>();

        for (int requestCylinder : cylinderRequests) {
            if (requestCylinder >= 0 && requestCylinder < maxCylinderIndex) {
                if (requestCylinder > currentHeadPosition) {
                    upwardRequests.add(requestCylinder);
                } else if (requestCylinder < currentHeadPosition) {
                    downwardRequests.add(requestCylinder);
                }
            }
        }

        Collections.sort(upwardRequests);
        Collections.sort(downwardRequests);

        if (isMovingUpwards) {
            scheduledCylinders.addAll(upwardRequests);
            scheduledCylinders.addAll(downwardRequests);
        } else {
            Collections.reverse(downwardRequests);
            scheduledCylinders.addAll(downwardRequests);

            Collections.reverse(upwardRequests);
            scheduledCylinders.addAll(upwardRequests);
        }

        if (!scheduledCylinders.isEmpty()) {
            currentHeadPosition = scheduledCylinders.get(scheduledCylinders.size() - 1);
        }

        return scheduledCylinders;
    }

    public int getCurrentHeadPosition() {
        return currentHeadPosition;
    }

    public boolean isMovingUpwards() {
        return isMovingUpwards;
    }
}