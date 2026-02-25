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
        List<Integer> lower = new ArrayList<>();
        List<Integer> upper = new ArrayList<>();

        partitionRequests(requests, lower, upper);

        Collections.sort(lower);
        Collections.sort(upper);

        if (movingUp) {
            serviceOrder.addAll(handleUpwardMovement(lower, upper));
        } else {
            serviceOrder.addAll(handleDownwardMovement(lower, upper));
        }

        return serviceOrder;
    }

    private void partitionRequests(List<Integer> requests, List<Integer> lower, List<Integer> upper) {
        for (int request : requests) {
            if (request < 0 || request >= maxTrack) {
                continue;
            }
            if (request < currentPosition) {
                lower.add(request);
            } else {
                upper.add(request);
            }
        }
    }

    private List<Integer> handleUpwardMovement(List<Integer> lower, List<Integer> upper) {
        List<Integer> serviceOrder = new ArrayList<>();

        serviceOrder.addAll(upper);
        if (!upper.isEmpty()) {
            farthestPosition = upper.get(upper.size() - 1);
        }

        movingUp = false;
        Collections.reverse(lower);
        serviceOrder.addAll(lower);
        if (!lower.isEmpty()) {
            farthestPosition = Math.max(farthestPosition, lower.get(0));
        }

        return serviceOrder;
    }

    private List<Integer> handleDownwardMovement(List<Integer> lower, List<Integer> upper) {
        List<Integer> serviceOrder = new ArrayList<>();

        Collections.reverse(lower);
        serviceOrder.addAll(lower);
        if (!lower.isEmpty()) {
            farthestPosition = lower.get(0);
        }

        movingUp = true;
        serviceOrder.addAll(upper);
        if (!upper.isEmpty()) {
            farthestPosition = Math.max(farthestPosition, upper.get(upper.size() - 1));
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