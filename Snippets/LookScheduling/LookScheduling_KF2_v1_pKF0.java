package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public List<Integer> execute(List<Integer> requests) {
        List<Integer> validRequests = filterValidRequests(requests);
        List<Integer> lower = new ArrayList<>();
        List<Integer> upper = new ArrayList<>();

        splitRequests(validRequests, lower, upper);
        sortRequests(lower, upper);

        return movingUp ? processMovingUp(lower, upper) : processMovingDown(lower, upper);
    }

    private List<Integer> filterValidRequests(List<Integer> requests) {
        List<Integer> validRequests = new ArrayList<>();
        for (int request : requests) {
            if (request >= 0 && request < maxTrack) {
                validRequests.add(request);
            }
        }
        return validRequests;
    }

    private void splitRequests(List<Integer> requests, List<Integer> lower, List<Integer> upper) {
        for (int request : requests) {
            if (request < currentPosition) {
                lower.add(request);
            } else {
                upper.add(request);
            }
        }
    }

    private void sortRequests(List<Integer> lower, List<Integer> upper) {
        Collections.sort(lower);
        Collections.sort(upper);
    }

    private List<Integer> processMovingUp(List<Integer> lower, List<Integer> upper) {
        List<Integer> result = new ArrayList<>();

        result.addAll(upper);
        updateFarthestFromUpper(upper);

        movingUp = false;
        Collections.reverse(lower);
        result.addAll(lower);
        updateFarthestFromLower(lower);

        return result;
    }

    private List<Integer> processMovingDown(List<Integer> lower, List<Integer> upper) {
        List<Integer> result = new ArrayList<>();

        Collections.reverse(lower);
        result.addAll(lower);
        updateFarthestFromLower(lower);

        movingUp = true;
        result.addAll(upper);
        updateFarthestFromUpper(upper);

        return result;
    }

    private void updateFarthestFromUpper(List<Integer> upper) {
        if (!upper.isEmpty()) {
            int lastUpper = upper.get(upper.size() - 1);
            farthestPosition = Math.max(farthestPosition, lastUpper);
        }
    }

    private void updateFarthestFromLower(List<Integer> lower) {
        if (!lower.isEmpty()) {
            int firstLower = lower.get(0);
            farthestPosition = Math.max(farthestPosition, firstLower);
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