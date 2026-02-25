package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Circular LOOK (C-LOOK) disk scheduling algorithm.
 *
 * <p>The head moves in one direction (up or down) and services all requests in
 * that direction up to the furthest request. It then jumps directly to the
 * closest request on the opposite side (without going to the physical end of
 * the disk) and continues in the same logical direction.
 */
public class CircularLookScheduling {

    /** Current head position on the disk. */
    private int currentPosition;

    /** Direction of head movement: true = moving towards higher cylinder numbers. */
    private final boolean movingUp;

    /** Maximum number of cylinders on the disk (exclusive upper bound). */
    private final int maxCylinder;

    /**
     * Creates a new C-LOOK scheduler.
     *
     * @param startPosition initial head position
     * @param movingUp      initial direction (true = towards higher cylinders)
     * @param maxCylinder   total number of cylinders (valid range: 0 to maxCylinder - 1)
     */
    public CircularLookScheduling(int startPosition, boolean movingUp, int maxCylinder) {
        this.currentPosition = startPosition;
        this.movingUp = movingUp;
        this.maxCylinder = maxCylinder;
    }

    /**
     * Executes the C-LOOK scheduling on the given list of requests.
     *
     * <p>Invalid requests (outside [0, maxCylinder)) are ignored.
     *
     * @param requests list of requested cylinder positions
     * @return ordered list of requests in the sequence they will be serviced
     */
    public List<Integer> execute(List<Integer> requests) {
        List<Integer> scheduledOrder = new ArrayList<>();
        List<Integer> upRequests = new ArrayList<>();
        List<Integer> downRequests = new ArrayList<>();

        // Partition valid requests into those above and below the current position.
        for (int request : requests) {
            if (!isValidRequest(request) || request == currentPosition) {
                continue;
            }
            if (request > currentPosition) {
                upRequests.add(request);
            } else {
                downRequests.add(request);
            }
        }

        Collections.sort(upRequests);
        Collections.sort(downRequests);

        if (movingUp) {
            // Move up first, then jump to the lowest pending request and move up again.
            scheduledOrder.addAll(upRequests);
            scheduledOrder.addAll(downRequests);
        } else {
            // Move down first, then jump to the highest pending request and move down again.
            Collections.reverse(downRequests);
            scheduledOrder.addAll(downRequests);

            Collections.reverse(upRequests);
            scheduledOrder.addAll(upRequests);
        }

        if (!scheduledOrder.isEmpty()) {
            currentPosition = scheduledOrder.get(scheduledOrder.size() - 1);
        }

        return scheduledOrder;
    }

    /**
     * Checks whether a request is within the valid cylinder range.
     *
     * @param request cylinder number
     * @return true if the request is valid, false otherwise
     */
    private boolean isValidRequest(int request) {
        return request >= 0 && request < maxCylinder;
    }

    /**
     * @return the current head position after the last execution
     */
    public int getCurrentPosition() {
        return currentPosition;
    }

    /**
     * @return true if the head is configured to move towards higher cylinders
     */
    public boolean isMovingUp() {
        return movingUp;
    }
}