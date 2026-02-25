package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * LOOK disk scheduling algorithm.
 *
 * <p>Moves the disk arm in one direction, servicing all requests up to the last
 * request in that direction, then reverses direction without going to the
 * physical end of the disk.</p>
 */
public class LookScheduling {

    private final int maxTrack;
    private final int currentPosition;
    private boolean movingUp;
    private int farthestPosition;

    /**
     * @param startPosition    initial head position
     * @param initialDirection true if initial direction is towards higher tracks
     * @param maxTrack         exclusive upper bound for valid track numbers
     */
    public LookScheduling(int startPosition, boolean initialDirection, int maxTrack) {
        this.currentPosition = startPosition;
        this.movingUp = initialDirection;
        this.maxTrack = maxTrack;
    }

    /**
     * Run LOOK scheduling on the given requests.
     *
     * @param requests list of requested track numbers
     * @return tracks in the order they are serviced
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