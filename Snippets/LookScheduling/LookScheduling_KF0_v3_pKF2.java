package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of the LOOK disk scheduling algorithm.
 *
 * <p>The LOOK algorithm moves the disk arm in the current direction to service
 * the nearest pending request. It continues in that direction until there are
 * no more requests, then reverses direction and repeats.</p>
 *
 * @see <a href="https://en.wikipedia.org/wiki/LOOK_algorithm">LOOK algorithm</a>
 */
public class LookScheduling {

    private final int maxTrack;
    private final int currentPosition;
    private boolean movingUp;
    private int farthestPosition;

    /**
     * Creates a new LOOK scheduler.
     *
     * @param startPosition    initial head position
     * @param initialDirection {@code true} if the head initially moves toward higher tracks,
     *                         {@code false} if it moves toward lower tracks
     * @param maxTrack         exclusive upper bound for valid track numbers (valid: 0..maxTrack-1)
     */
    public LookScheduling(int startPosition, boolean initialDirection, int maxTrack) {
        this.currentPosition = startPosition;
        this.movingUp = initialDirection;
        this.maxTrack = maxTrack;
    }

    /**
     * Executes the LOOK scheduling algorithm on the given list of requests.
     *
     * @param requests list of requested track positions
     * @return list of track positions in the order they are serviced
     */
    public List<Integer> execute(List<Integer> requests) {
        List<Integer> serviceOrder = new ArrayList<>();
        List<Integer> lowerRequests = new ArrayList<>();
        List<Integer> upperRequests = new ArrayList<>();

        splitRequestsByCurrentPosition(requests, lowerRequests, upperRequests);

        Collections.sort(lowerRequests);
        Collections.sort(upperRequests);

        if (movingUp) {
            serviceOrder.addAll(serviceWhileMovingUp(lowerRequests, upperRequests));
        } else {
            serviceOrder.addAll(serviceWhileMovingDown(lowerRequests, upperRequests));
        }

        return serviceOrder;
    }

    /**
     * Splits requests into those below and at/above the current head position,
     * ignoring out-of-range requests.
     */
    private void splitRequestsByCurrentPosition(
        List<Integer> requests,
        List<Integer> lowerRequests,
        List<Integer> upperRequests
    ) {
        for (int request : requests) {
            if (request < 0 || request >= maxTrack) {
                continue;
            }
            if (request < currentPosition) {
                lowerRequests.add(request);
            } else {
                upperRequests.add(request);
            }
        }
    }

    /**
     * Services requests when the initial direction is upward:
     * first all higher tracks, then all lower tracks in reverse order.
     */
    private List<Integer> serviceWhileMovingUp(List<Integer> lowerRequests, List<Integer> upperRequests) {
        List<Integer> serviceOrder = new ArrayList<>();

        serviceOrder.addAll(upperRequests);
        if (!upperRequests.isEmpty()) {
            farthestPosition = upperRequests.get(upperRequests.size() - 1);
        }

        movingUp = false;

        Collections.reverse(lowerRequests);
        serviceOrder.addAll(lowerRequests);
        if (!lowerRequests.isEmpty()) {
            farthestPosition = Math.max(farthestPosition, lowerRequests.get(0));
        }

        return serviceOrder;
    }

    /**
     * Services requests when the initial direction is downward:
     * first all lower tracks in reverse order, then all higher tracks.
     */
    private List<Integer> serviceWhileMovingDown(List<Integer> lowerRequests, List<Integer> upperRequests) {
        List<Integer> serviceOrder = new ArrayList<>();

        Collections.reverse(lowerRequests);
        serviceOrder.addAll(lowerRequests);
        if (!lowerRequests.isEmpty()) {
            farthestPosition = lowerRequests.get(0);
        }

        movingUp = true;

        serviceOrder.addAll(upperRequests);
        if (!upperRequests.isEmpty()) {
            farthestPosition = Math.max(farthestPosition, upperRequests.get(upperRequests.size() - 1));
        }

        return serviceOrder;
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