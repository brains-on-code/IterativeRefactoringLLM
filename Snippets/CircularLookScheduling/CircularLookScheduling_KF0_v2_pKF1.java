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
    private int headPosition;
    private boolean movingUpward;
    private final int maxCylinder;

    public CircularLookScheduling(int initialHeadPosition, boolean initialDirectionUpward, int maxCylinder) {
        this.headPosition = initialHeadPosition;
        this.movingUpward = initialDirectionUpward;
        this.maxCylinder = maxCylinder;
    }

    public List<Integer> execute(List<Integer> requests) {
        List<Integer> scheduledRequests = new ArrayList<>();

        List<Integer> upwardRequests = new ArrayList<>();
        List<Integer> downwardRequests = new ArrayList<>();

        for (int request : requests) {
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

        if (movingUpward) {
            scheduledRequests.addAll(upwardRequests);
            scheduledRequests.addAll(downwardRequests);
        } else {
            Collections.reverse(downwardRequests);
            scheduledRequests.addAll(downwardRequests);

            Collections.reverse(upwardRequests);
            scheduledRequests.addAll(upwardRequests);
        }

        if (!scheduledRequests.isEmpty()) {
            headPosition = scheduledRequests.get(scheduledRequests.size() - 1);
        }

        return scheduledRequests;
    }

    public int getHeadPosition() {
        return headPosition;
    }

    public boolean isMovingUpward() {
        return movingUpward;
    }
}