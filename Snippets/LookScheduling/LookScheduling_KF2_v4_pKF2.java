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

    private void processUpwardFirst(
            List<Integer> scheduledOrder,
            List<Integer> lowerRequests,
            List<Integer> upperRequests
    ) {
        scheduledOrder.addAll(upperRequests);
        updateFarthestFromUpper(upperRequests);

        movingUp = false;

        Collections.reverse(lowerRequests);
        scheduledOrder.addAll(lowerRequests);
        updateFarthestFromLower(lowerRequests);
    }

    private void processDownwardFirst(
            List<Integer> scheduledOrder,
            List<Integer> lowerRequests,
            List<Integer> upperRequests
    ) {
        Collections.reverse(lowerRequests);
        scheduledOrder.addAll(lowerRequests);
        updateFarthestFromLower(lowerRequests);

        movingUp = true;

        scheduledOrder.addAll(upperRequests);
        updateFarthestFromUpper(upperRequests);
    }

    private void updateFarthestFromUpper(List<Integer> upperRequests) {
        if (!upperRequests.isEmpty()) {
            int lastUpper = upperRequests.get(upperRequests.size() - 1);
            farthestPosition = Math.max(farthestPosition, lastUpper);
        }
    }

    private void updateFarthestFromLower(List<Integer> lowerRequests) {
        if (!lowerRequests.isEmpty()) {
            int lastLower = lowerRequests.get(0);
            farthestPosition = Math.max(farthestPosition, lastLower);
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