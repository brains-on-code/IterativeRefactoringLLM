package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Circular Look Scheduling (C-LOOK) is a disk scheduling algorithm similar to
 * the C-SCAN algorithm but with a key difference. In C-LOOK, the disk arm also
 * moves in one direction to service requests, but instead of going all the way
 * to the end of the disk, it only goes as far as the furthest request in the
 * current direction. After servicing the last request in the current direction,
 * the arm immediately jumps back to the closest request on the other side without
 * moving to the disk's extreme ends. This reduces the unnecessary movement of the
 * disk arm, resulting in better performance compared to C-SCAN, while still
 * maintaining fair wait times for requests.
 */
public class CircularLookScheduling {
    private int currentHeadPosition;
    private boolean isMovingUpward;
    private final int maxCylinderIndex;

    public CircularLookScheduling(int initialHeadPosition, boolean initialDirectionUpward, int maxCylinderIndex) {
        this.currentHeadPosition = initialHeadPosition;
        this.isMovingUpward = initialDirectionUpward;
        this.maxCylinderIndex = maxCylinderIndex;
    }

    public List<Integer> execute(List<Integer> requestQueue) {
        List<Integer> scheduledOrder = new ArrayList<>();

        List<Integer> upwardRequests = new ArrayList<>();
        List<Integer> downwardRequests = new ArrayList<>();

        for (int request : requestQueue) {
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

        if (isMovingUpward) {
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

    public int getHeadPosition() {
        return currentHeadPosition;
    }

    public boolean isMovingUpward() {
        return isMovingUpward;
    }
}