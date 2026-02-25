package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implements the C-LOOK (Circular LOOK) disk scheduling algorithm.
 *
 * <p>C-LOOK moves the disk arm in one direction (up or down), servicing all
 * requests in that direction. When it reaches the last request in that
 * direction, it jumps back to the furthest request in the opposite direction
 * without servicing cylinders in between, then continues in the original
 * direction.
 */
public class CircularLookScheduling {

    /** Current head position on the disk. */
    private int currentPosition;

    /** Direction of head movement: true = moving up (toward higher cylinders), false = moving down. */
    private final boolean movingUp;

    /** Maximum number of cylinders on the disk (exclusive upper bound). */
    private final int maxCylinder;

    /**
     * Creates a new C-LOOK scheduler.
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
     * Schedules the given list of requests using the C-LOOK algorithm.
     *
     * @param requests list of requested cylinder positions
     * @return ordered list of requests in the sequence they will be serviced
     */
    public List<Integer> execute(List<Integer> requests) {
        List<Integer> scheduledRequests = new ArrayList<>();
        List<Integer> upwardRequests = new ArrayList<>();
        List<Integer> downwardRequests = new ArrayList<>();

        // Partition requests into those above and below the current head position,
        // ignoring out-of-bounds requests.
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
            // Move up first, then jump to the lowest request and move up again.
            scheduledRequests.addAll(upwardRequests);
            scheduledRequests.addAll(downwardRequests);
        } else {
            // Move down first (from high to low), then jump to the highest request and move down again.
            Collections.reverse(downwardRequests);
            scheduledRequests.addAll(downwardRequests);

            Collections.reverse(upwardRequests);
            scheduledRequests.addAll(upwardRequests);
        }

        updateCurrentPosition(scheduledRequests);
        return scheduledRequests;
    }

    /**
     * Checks whether a request is within the valid cylinder range.
     *
     * @param request cylinder number
     * @return true if the request is valid, false otherwise
     */
    private boolean isWithinBounds(int request) {
        return request >= 0 && request < maxCylinder;
    }

    /**
     * Updates the current head position to the last serviced request, if any.
     *
     * @param scheduledRequests ordered list of serviced requests
     */
    private void updateCurrentPosition(List<Integer> scheduledRequests) {
        if (!scheduledRequests.isEmpty()) {
            currentPosition = scheduledRequests.get(scheduledRequests.size() - 1);
        }
    }

    /**
     * Returns the current head position.
     *
     * @return current cylinder position
     */
    public int getCurrentPosition() {
        return currentPosition;
    }

    /**
     * Returns the configured movement direction.
     *
     * @return true if moving up, false if moving down
     */
    public boolean isMovingUp() {
        return movingUp;
    }
}