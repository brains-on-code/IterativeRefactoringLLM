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
        List<Integer> result = new ArrayList<>();
        List<Integer> lower = new ArrayList<>();
        List<Integer> upper = new ArrayList<>();

        for (int request : requests) {
            if (request >= 0 && request < maxTrack) {
                if (request < currentPosition) {
                    lower.add(request);
                } else {
                    upper.add(request);
                }
            }
        }

        Collections.sort(lower);
        Collections.sort(upper);

        if (movingUp) {
            result.addAll(upper);
            if (!upper.isEmpty()) {
                farthestPosition = upper.get(upper.size() - 1);
            }

            movingUp = false;
            Collections.reverse(lower);
            result.addAll(lower);
            if (!lower.isEmpty()) {
                farthestPosition = Math.max(farthestPosition, lower.get(0));
            }
        } else {
            Collections.reverse(lower);
            result.addAll(lower);
            if (!lower.isEmpty()) {
                farthestPosition = lower.get(0);
            }

            movingUp = true;
            result.addAll(upper);
            if (!upper.isEmpty()) {
                farthestPosition = Math.max(farthestPosition, upper.get(upper.size() - 1));
            }
        }

        return result;
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
