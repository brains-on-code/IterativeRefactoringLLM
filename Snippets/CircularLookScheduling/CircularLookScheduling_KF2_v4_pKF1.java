package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CircularLookScheduling {
    private int headPosition;
    private boolean movingUpwards;
    private final int maxCylinder;

    public CircularLookScheduling(int initialHeadPosition, boolean initialDirectionUpwards, int maxCylinder) {
        this.headPosition = initialHeadPosition;
        this.movingUpwards = initialDirectionUpwards;
        this.maxCylinder = maxCylinder;
    }

    public List<Integer> execute(List<Integer> cylinderRequests) {
        List<Integer> scheduledOrder = new ArrayList<>();

        List<Integer> upwardRequests = new ArrayList<>();
        List<Integer> downwardRequests = new ArrayList<>();

        for (int request : cylinderRequests) {
            if (request >= 0 && request < maxCylinder) {
                if (request > headPosition) {
                    upwardRequests.add(request);
                } else if (request < headPosition) {
                    downwardRequests.add(request);
                }
            }
        }

        Collections.sort(upwardRequests);
        Collections.sort(downwardRequests);

        if (movingUpwards) {
            scheduledOrder.addAll(upwardRequests);
            scheduledOrder.addAll(downwardRequests);
        } else {
            Collections.reverse(downwardRequests);
            scheduledOrder.addAll(downwardRequests);

            Collections.reverse(upwardRequests);
            scheduledOrder.addAll(upwardRequests);
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