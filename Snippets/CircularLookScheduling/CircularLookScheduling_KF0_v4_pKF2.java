package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Circular LOOK (C-LOOK) disk scheduling algorithm.
 *
 * <p>The head moves in one direction and services all requests in that
 * direction up to the furthest request. It then jumps directly to the closest
 * request on the opposite side (without going to the physical end of the disk)
 * and continues in the same logical direction.
 */
public class CircularLookScheduling {

    /** Current head position on the disk. */
    private int currentPosition;

    /** Direction of head movement: true = towards higher cylinder numbers. */
    private final boolean movingUp;

    /** Total number of cylinders (valid range: 0 to maxCylinder - 1). */
    private final int maxCylinder;

    /**
     * Constructs a new C-LOOK scheduler.
     *
     * @param startPosition initial head position
     * @param movingUp      initial direction (true = towards higher cylinders)
     * @param maxCylinder   total number of cylinders
     */
    public CircularLookScheduling(int startPosition, boolean movingUp, int maxCylinder) {
        this.currentPosition = startPosition;
        this.movingUp = movingUp;
        this.maxCylinder = maxCylinder;
    }

    /**
     * Executes the C-LOOK scheduling on the given list of requests.
     *
     * <p>Requests outside the valid range [0, maxCylinder) or equal to the
     * current head position are ignored.
     *
     * @param requests list of requested cylinder positions
     * @return ordered list of requests in the sequence they will be serviced
     */
    public List<Integer> execute(List<Integer> requests) {
        List<Integer> scheduledOrder = new ArrayList<>();
        List<Integer> upRequests = new ArrayList<>();
        List<Integer> downRequests = new ArrayList<>();

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
            scheduleMovingUp(scheduledOrder, upRequests, downRequests);
        } else {
            scheduleMovingDown(scheduledOrder, upRequests, downRequests);
        }

        updateCurrentPosition(scheduledOrder);

        return scheduledOrder;
    }

    private void scheduleMovingUp(List<Integer> scheduledOrder,
                                  List<Integer> upRequests,
                                  List<Integer> downRequests) {
        scheduledOrder.addAll(upRequests);
        scheduledOrder.addAll(downRequests);
    }

    private void scheduleMovingDown(List<Integer> scheduledOrder,
                                    List<Integer> upRequests,
                                    List<Integer> downRequests) {
        Collections.reverse(downRequests);
        scheduledOrder.addAll(downRequests);

        Collections.reverse(upRequests);
        scheduledOrder.addAll(upRequests);
    }

    private void updateCurrentPosition(List<Integer> scheduledOrder) {
        if (!scheduledOrder.isEmpty()) {
            currentPosition = scheduledOrder.get(scheduledOrder.size() - 1);
        }
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
     * Returns the current head position after the last execution.
     *
     * @return current head position
     */
    public int getCurrentPosition() {
        return currentPosition;
    }

    /**
     * Returns whether the head is configured to move towards higher cylinders.
     *
     * @return true if moving towards higher cylinders, false otherwise
     */
    public boolean isMovingUp() {
        return movingUp;
    }
}