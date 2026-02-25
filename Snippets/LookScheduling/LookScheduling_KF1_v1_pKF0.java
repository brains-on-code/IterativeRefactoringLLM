package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a simple disk scheduling mechanism that processes requests
 * based on a current head position, direction, and disk size.
 */
public class Class1 {

    /** Total number of tracks on the disk (disk size). */
    private final int diskSize;

    /** Current head position. */
    private int currentHeadPosition;

    /** Current direction of head movement: true = upward, false = downward. */
    private boolean movingUpward;

    /**
     * Creates a new disk scheduler.
     *
     * @param initialHeadPosition initial head position
     * @param initialDirection    initial direction (true = upward, false = downward)
     * @param diskSize            total number of tracks on the disk
     */
    public Class1(int initialHeadPosition, boolean initialDirection, int diskSize) {
        this.currentHeadPosition = initialHeadPosition;
        this.movingUpward = initialDirection;
        this.diskSize = diskSize;
    }

    /**
     * Schedules the given list of disk requests based on the current head
     * position and direction, returning the order in which they will be served.
     *
     * @param requests list of requested track positions
     * @return ordered list of track positions to be served
     */
    public List<Integer> method1(List<Integer> requests) {
        List<Integer> scheduledOrder = new ArrayList<>();
        List<Integer> lowerRequests = new ArrayList<>();
        List<Integer> higherRequests = new ArrayList<>();

        for (int request : requests) {
            if (request >= 0 && request < diskSize) {
                if (request < currentHeadPosition) {
                    lowerRequests.add(request);
                } else {
                    higherRequests.add(request);
                }
            }
        }

        Collections.sort(lowerRequests);
        Collections.sort(higherRequests);

        if (movingUpward) {
            scheduledOrder.addAll(higherRequests);
            if (!higherRequests.isEmpty()) {
                currentHeadPosition = higherRequests.get(higherRequests.size() - 1);
            }

            movingUpward = false;
            Collections.reverse(lowerRequests);
            scheduledOrder.addAll(lowerRequests);
            if (!lowerRequests.isEmpty()) {
                currentHeadPosition = Math.max(currentHeadPosition, lowerRequests.get(0));
            }
        } else {
            Collections.reverse(lowerRequests);
            scheduledOrder.addAll(lowerRequests);
            if (!lowerRequests.isEmpty()) {
                currentHeadPosition = lowerRequests.get(0);
            }

            movingUpward = true;
            scheduledOrder.addAll(higherRequests);
            if (!higherRequests.isEmpty()) {
                currentHeadPosition = Math.max(currentHeadPosition, higherRequests.get(higherRequests.size() - 1));
            }
        }

        return scheduledOrder;
    }

    public int method2() {
        return currentHeadPosition;
    }

    public boolean method3() {
        return movingUpward;
    }

    public int method4() {
        return diskSize;
    }
}