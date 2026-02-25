package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implements the C-SCAN (Circular SCAN) disk scheduling algorithm.
 *
 * <p>C-SCAN moves the disk head in one direction (up or down) servicing
 * requests along the way. When it reaches the end, it jumps to the
 * opposite end and continues in the same direction, providing a more
 * uniform wait time than SCAN.
 */
public class CircularScanScheduling {

    /** Current head position on the disk. */
    private int currentPosition;

    /** Direction of head movement: true = towards higher cylinder numbers. */
    private final boolean movingUp;

    /** Total number of cylinders on the disk (upper bound, exclusive). */
    private final int diskSize;

    /**
     * Creates a new C-SCAN scheduler.
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
     * Executes the C-SCAN scheduling on the given list of requests.
     *
     * @param requests list of requested cylinder positions
     * @return ordered list of serviced requests according to C-SCAN
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
     * Returns the service order when the head is moving towards higher cylinders.
     *
     * <p>Steps:
     * <ol>
     *   <li>Service all requests from currentPosition up to (but below) diskSize.</li>
     *   <li>Wrap around and service remaining requests below currentPosition.</li>
     * </ol>
     */
    private List<Integer> getRequestsMovingUp(List<Integer> sortedRequests) {
        List<Integer> result = new ArrayList<>();

        // Service requests at or above current position up to diskSize
        for (int request : sortedRequests) {
            if (request >= currentPosition && request < diskSize) {
                result.add(request);
            }
        }

        // Wrap around: service remaining requests below current position
        for (int request : sortedRequests) {
            if (request < currentPosition) {
                result.add(request);
            }
        }

        return result;
    }

    /**
     * Returns the service order when the head is moving towards lower cylinders.
     *
     * <p>Steps:
     * <ol>
     *   <li>Service all requests from currentPosition down to 0.</li>
     *   <li>Wrap around and service remaining requests above currentPosition.</li>
     * </ol>
     */
    private List<Integer> getRequestsMovingDown(List<Integer> sortedRequests) {
        List<Integer> result = new ArrayList<>();

        // Service requests at or below current position down to 0
        for (int i = sortedRequests.size() - 1; i >= 0; i--) {
            int request = sortedRequests.get(i);
            if (request <= currentPosition) {
                result.add(request);
            }
        }

        // Wrap around: service remaining requests above current position
        for (int i = sortedRequests.size() - 1; i >= 0; i--) {
            int request = sortedRequests.get(i);
            if (request > currentPosition) {
                result.add(request);
            }
        }

        return result;
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
     * Returns the configured direction of movement.
     *
     * @return true if moving towards higher cylinders, false otherwise
     */
    public boolean isMovingUp() {
        return movingUp;
    }
}