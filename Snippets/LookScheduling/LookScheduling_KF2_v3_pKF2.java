package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implements the LOOK disk scheduling algorithm.
 *
 * <p>LOOK moves the disk arm in one direction, servicing all requests until the
 * last request in that direction, then reverses direction without going all the
 * way to the end of the disk (unlike SCAN).</p>
 */
public class LookScheduling {

    private final int maxTrack;
    private final int currentPosition;
    private boolean movingUp;
    private int farthestPosition;

    /**
     * @param startPosition    initial head position
     * @param initialDirection true if initial direction is towards higher tracks
     * @param maxTrack         total number of tracks (exclusive upper bound)
     */
    public LookScheduling(int startPosition, boolean initialDirection, int maxTrack) {
        this.currentPosition = startPosition;
        this.movingUp = initialDirection;
        this.maxTrack = maxTrack;
    }

    /**
     * Executes the LOOK scheduling on the given list of requests.
     *
     * @param requests list of requested track numbers
     * @return ordered list of tracks in the sequence they will be serviced
     */
    public List<Integer> execute(List<Integer> requests) {
        List<Integer> scheduledOrder = new ArrayList<>();
        List<Integer> lowerRequests = new ArrayList<>();
        List<Integer> upperRequests = new ArrayList<>();

        // Partition requests into those below and at/above the current position
        for (int request : requests) {
            if (!isWithinBounds(request)) {
                continue;
            }
            if (request < currentPosition) {
                lowerRequests.add(request);
            } else {
                upperRequests.add(request);
            }
        }

        Collections.sort(lowerRequests);
        Collections.sort(upperRequests);

        if (movingUp) {
            processUpwardFirst(scheduledOrder, lowerRequests, upperRequests);
        } else {
            processDownwardFirst(scheduledOrder, lowerRequests, upperRequests);
        }

        return scheduledOrder;
    }

    private boolean isWithinBounds(int request) {
        return request >= 0 && request < maxTrack;
    }

    /**
     * Process requests when the initial direction is upwards (towards higher tracks).
     */
    private void processUpwardFirst(
            List<Integer> scheduledOrder,
            List<Integer> lowerRequests,
            List<Integer> upperRequests
    ) {
        // Service all requests above or equal to current position in ascending order
        scheduledOrder.addAll(upperRequests);
        if (!upperRequests.isEmpty()) {
            farthestPosition = upperRequests.get(upperRequests.size() - 1);
        }

        // Reverse direction after reaching the last upper request
        movingUp = false;

        // Service remaining lower requests in descending order
        Collections.reverse(lowerRequests);
        scheduledOrder.addAll(lowerRequests);
        if (!lowerRequests.isEmpty()) {
            farthestPosition = Math.max(farthestPosition, lowerRequests.get(0));
        }
    }

    /**
     * Process requests when the initial direction is downwards (towards lower tracks).
     */
    private void processDownwardFirst(
            List<Integer> scheduledOrder,
            List<Integer> lowerRequests,
            List<Integer> upperRequests
    ) {
        // Service all requests below current position in descending order
        Collections.reverse(lowerRequests);
        scheduledOrder.addAll(lowerRequests);
        if (!lowerRequests.isEmpty()) {
            farthestPosition = lowerRequests.get(0);
        }

        // Reverse direction after reaching the last lower request
        movingUp = true;

        // Service remaining upper requests in ascending order
        scheduledOrder.addAll(upperRequests);
        if (!upperRequests.isEmpty()) {
            farthestPosition = Math.max(
                    farthestPosition,
                    upperRequests.get(upperRequests.size() - 1)
            );
        }
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public boolean isMovingUp() {
        return movingUp;
    }

    public int getFarthestPosition() {
        return farthestPosition;
    }
}