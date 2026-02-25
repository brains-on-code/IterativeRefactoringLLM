package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * C-SCAN (Circular SCAN) disk scheduling algorithm.
 *
 * <p>The head moves in one direction only. When it reaches one end of the disk,
 * it jumps to the opposite end and continues in the same direction.
 */
public class CircularScanScheduling {

    /** Current head position on the disk. */
    private int currentPosition;

    /** Direction of head movement: true = towards higher cylinder numbers. */
    private final boolean movingUp;

    /** Total number of cylinders on the disk (upper bound, exclusive). */
    private final int diskSize;

    /**
     * Creates a C-SCAN scheduler.
     *
     * @param startPosition initial head position
     * @param movingUp      initial direction (true = towards higher cylinders)
     * @param diskSize      total number of cylinders on the disk
     */
    public CircularScanScheduling(int startPosition, boolean movingUp, int diskSize) {
        this.currentPosition = startPosition;
        this.movingUp = movingUp;
        this.diskSize = diskSize;
    }

    /**
     * Runs C-SCAN scheduling on the given list of requests.
     *
     * @param requests list of requested cylinder positions
     * @return ordered list of serviced requests
     */
    public List<Integer> execute(List<Integer> requests) {
        if (requests == null || requests.isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> sortedRequests = new ArrayList<>(requests);
        Collections.sort(sortedRequests);

        List<Integer> serviceOrder = movingUp
            ? getRequestsMovingUp(sortedRequests)
            : getRequestsMovingDown(sortedRequests);

        if (!serviceOrder.isEmpty()) {
            currentPosition = serviceOrder.get(serviceOrder.size() - 1);
        }

        return serviceOrder;
    }

    /**
     * Service order when the head moves towards higher cylinders.
     *
     * <p>Order:
     * <ol>
     *   <li>Requests in [currentPosition, diskSize)</li>
     *   <li>Then requests in [0, currentPosition)</li>
     * </ol>
     */
    private List<Integer> getRequestsMovingUp(List<Integer> sortedRequests) {
        List<Integer> result = new ArrayList<>();

        for (int request : sortedRequests) {
            if (request >= currentPosition && request < diskSize) {
                result.add(request);
            }
        }

        for (int request : sortedRequests) {
            if (request < currentPosition) {
                result.add(request);
            }
        }

        return result;
    }

    /**
     * Service order when the head moves towards lower cylinders.
     *
     * <p>Order:
     * <ol>
     *   <li>Requests in (−∞, currentPosition]</li>
     *   <li>Then remaining requests above currentPosition</li>
     * </ol>
     */
    private List<Integer> getRequestsMovingDown(List<Integer> sortedRequests) {
        List<Integer> result = new ArrayList<>();

        for (int i = sortedRequests.size() - 1; i >= 0; i--) {
            int request = sortedRequests.get(i);
            if (request <= currentPosition) {
                result.add(request);
            }
        }

        for (int i = sortedRequests.size() - 1; i >= 0; i--) {
            int request = sortedRequests.get(i);
            if (request > currentPosition) {
                result.add(request);
            }
        }

        return result;
    }

    /** Returns the current head position. */
    public int getCurrentPosition() {
        return currentPosition;
    }

    /** Returns true if moving towards higher cylinders. */
    public boolean isMovingUp() {
        return movingUp;
    }
}