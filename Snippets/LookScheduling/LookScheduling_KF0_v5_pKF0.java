package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * https://en.wikipedia.org/wiki/LOOK_algorithm
 * Look Scheduling algorithm implementation.
 * The Look algorithm moves the disk arm to the closest request in the current direction,
 * and once it processes all requests in that direction, it reverses the direction.
 */
public class LookScheduling {

    private final int maxTrack;
    private final int currentPosition;
    private boolean movingUp;
    private int farthestPosition;

    public LookScheduling(int startPosition, boolean initialDirection, int maxTrack) {
        this.currentPosition = startPosition;
        this.movingUp = initialDirection;
        this.maxTrack = maxTrack;
    }

    /**
     * Executes the Look Scheduling algorithm on the given list of requests.
     *
     * @param requests List of disk requests.
     * @return Order in which requests are processed.
     */
    public List<Integer> execute(List<Integer> requests) {
        List<Integer> result = new ArrayList<>();
        List<Integer> lowerRequests = new ArrayList<>();
        List<Integer> upperRequests = new ArrayList<>();

        partitionRequests(requests, lowerRequests, upperRequests);
        sortRequests(lowerRequests, upperRequests);

        if (movingUp) {
            processUpwardFirst(result, lowerRequests, upperRequests);
        } else {
            processDownwardFirst(result, lowerRequests, upperRequests);
        }

        return result;
    }

    private void partitionRequests(List<Integer> requests, List<Integer> lower, List<Integer> upper) {
        for (int request : requests) {
            if (isOutOfBounds(request)) {
                continue;
            }
            if (request < currentPosition) {
                lower.add(request);
            } else {
                upper.add(request);
            }
        }
    }

    private boolean isOutOfBounds(int request) {
        return request < 0 || request >= maxTrack;
    }

    private void sortRequests(List<Integer> lower, List<Integer> upper) {
        Collections.sort(lower);
        Collections.sort(upper);
    }

    private void processUpwardFirst(List<Integer> result, List<Integer> lower, List<Integer> upper) {
        result.addAll(upper);
        updateFarthestFromList(upper, true);

        movingUp = false;
        Collections.reverse(lower);
        result.addAll(lower);
        updateFarthestFromList(lower, false);
    }

    private void processDownwardFirst(List<Integer> result, List<Integer> lower, List<Integer> upper) {
        Collections.reverse(lower);
        result.addAll(lower);
        updateFarthestFromList(lower, false);

        movingUp = true;
        result.addAll(upper);
        updateFarthestFromList(upper, true);
    }

    private void updateFarthestFromList(List<Integer> requests, boolean fromEnd) {
        if (requests.isEmpty()) {
            return;
        }
        int index = fromEnd ? requests.size() - 1 : 0;
        farthestPosition = Math.max(farthestPosition, requests.get(index));
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