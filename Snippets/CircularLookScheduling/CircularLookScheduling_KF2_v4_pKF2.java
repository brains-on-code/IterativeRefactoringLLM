package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * C-LOOK (Circular LOOK) disk scheduling algorithm.
 *
 * <p>The head moves in one direction (up or down), servicing all requests in
 * that direction. After the last request in that direction, it jumps to the
 * furthest request in the opposite direction and continues in the original
 * direction, without servicing cylinders in between.
 */
public class CircularLookScheduling {

    /** Current head position on the disk. */
    private int currentPosition;

    /** Movement direction: true = up (toward higher cylinders), false = down. */
    private final boolean movingUp;

    /** Exclusive upper bound for valid cylinder indices. */
    private final int maxCylinder;

    /**
     * Constructs a C-LOOK scheduler.
     *
     * @param startPosition initial head position
     * @param movingUp      initial direction (true for up, false for down)
     * @param maxCylinder   total number of cylinders (valid range is [0, maxCylinder))
     */
    public CircularLookScheduling(int startPosition, boolean movingUp, int maxCylinder) {
        this.currentPosition = startPosition;
        this.movingUp = movingUp;
        this.maxCylinder = maxCylinder;
    }

    /**
     * Orders the given requests using the C-LOOK algorithm.
     *
     * @param requests list of requested cylinder positions
     * @return ordered list of requests in the sequence they will be serviced
     */
    public List<Integer> execute(List<Integer> requests) {
        List<Integer> scheduledRequests = new ArrayList<>();
        List<Integer> upwardRequests = new ArrayList<>();
        List<Integer> downwardRequests = new ArrayList<>();

        for (int request : requests) {
            if (!isWithinBounds(request) || request == currentPosition) {
                continue;
            }
            if (request > currentPosition) {
                upwardRequests.add(request);
            } else {
                downwardRequests.add(request);
            }
        }

        Collections.sort(upwardRequests);
        Collections.sort(downwardRequests);

        if (movingUp) {
            scheduledRequests.addAll(upwardRequests);
            scheduledRequests.addAll(downwardRequests);
        } else {
            Collections.reverse(downwardRequests);
            scheduledRequests.addAll(downwardRequests);

            Collections.reverse(upwardRequests);
            scheduledRequests.addAll(upwardRequests);
        }

        updateCurrentPosition(scheduledRequests);
        return scheduledRequests;
    }

    private boolean isWithinBounds(int request) {
        return request >= 0 && request < maxCylinder;
    }

    private void updateCurrentPosition(List<Integer> scheduledRequests) {
        if (!scheduledRequests.isEmpty()) {
            currentPosition = scheduledRequests.get(scheduledRequests.size() - 1);
        }
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public boolean isMovingUp() {
        return movingUp;
    }
}