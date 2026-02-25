package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Circular Look Scheduling (C-LOOK) is a disk scheduling algorithm similar to
 * the C-SCAN algorithm but with a key difference. In C-LOOK, the disk arm also
 * moves in one direction to service requests, but instead of going all the way
 * to the end of the disk, it only goes as far as the furthest request in the
 * current direction. After servicing the last request in the current direction,
 * the arm immediately jumps back to the closest request on the other side without
 * moving to the disk's extreme ends. This reduces the unnecessary movement of the
 * disk arm, resulting in better performance compared to C-SCAN, while still
 * maintaining fair wait times for requests.
 */
public class CircularLookScheduling {

    private int currentPosition;
    private boolean movingUp;
    private final int maxCylinder;

    public CircularLookScheduling(int startPosition, boolean movingUp, int maxCylinder) {
        this.currentPosition = startPosition;
        this.movingUp = movingUp;
        this.maxCylinder = maxCylinder;
    }

    public List<Integer> execute(List<Integer> requests) {
        List<Integer> upRequests = new ArrayList<>();
        List<Integer> downRequests = new ArrayList<>();

        partitionRequests(requests, upRequests, downRequests);

        Collections.sort(upRequests);
        Collections.sort(downRequests);

        List<Integer> result = movingUp
            ? processMovingUp(upRequests, downRequests)
            : processMovingDown(upRequests, downRequests);

        updateCurrentPosition(result);

        return result;
    }

    private void partitionRequests(List<Integer> requests,
                                   List<Integer> upRequests,
                                   List<Integer> downRequests) {
        for (int request : requests) {
            if (!isValidRequest(request)) {
                continue;
            }
            if (request > currentPosition) {
                upRequests.add(request);
            } else if (request < currentPosition) {
                downRequests.add(request);
            }
        }
    }

    private boolean isValidRequest(int request) {
        return request >= 0 && request < maxCylinder;
    }

    private List<Integer> processMovingUp(List<Integer> upRequests,
                                          List<Integer> downRequests) {
        List<Integer> result = new ArrayList<>(upRequests.size() + downRequests.size());
        result.addAll(upRequests);
        result.addAll(downRequests);
        return result;
    }

    private List<Integer> processMovingDown(List<Integer> upRequests,
                                            List<Integer> downRequests) {
        List<Integer> result = new ArrayList<>(upRequests.size() + downRequests.size());

        Collections.reverse(downRequests);
        result.addAll(downRequests);

        Collections.reverse(upRequests);
        result.addAll(upRequests);

        return result;
    }

    private void updateCurrentPosition(List<Integer> result) {
        if (!result.isEmpty()) {
            currentPosition = result.get(result.size() - 1);
        }
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public boolean isMovingUp() {
        return movingUp;
    }
}