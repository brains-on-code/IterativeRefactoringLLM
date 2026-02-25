package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * https://en.wikipedia.org/wiki/Elevator_algorithm
 * SCAN Scheduling algorithm implementation.
 * The SCAN algorithm moves the disk arm towards one end of the disk, servicing all requests
 * along the way until it reaches the end. Once it reaches the end, it reverses direction
 * and services the requests on its way back.
 *
 * This algorithm ensures that all requests are serviced in a fair manner,
 * while minimizing the seek time for requests located close to the current position
 * of the disk arm.
 *
 * The SCAN algorithm is particularly useful in environments with a large number of
 * disk requests, as it reduces the overall movement of the disk arm compared to
 */
public class ScanScheduling {

    private final int headPosition;
    private final int diskSize;
    private final boolean movingUp;

    public ScanScheduling(int headPosition, boolean movingUp, int diskSize) {
        this.headPosition = headPosition;
        this.movingUp = movingUp;
        this.diskSize = diskSize;
    }

    public List<Integer> execute(List<Integer> requests) {
        if (requests == null || requests.isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> leftRequests = new ArrayList<>();
        List<Integer> rightRequests = new ArrayList<>();

        partitionRequests(requests, leftRequests, rightRequests);

        Collections.sort(leftRequests);
        Collections.sort(rightRequests);

        return movingUp
            ? processMovingUp(leftRequests, rightRequests)
            : processMovingDown(leftRequests, rightRequests);
    }

    private void partitionRequests(List<Integer> requests, List<Integer> left, List<Integer> right) {
        for (int request : requests) {
            if (request < headPosition) {
                left.add(request);
            } else {
                right.add(request);
            }
        }
    }

    private List<Integer> processMovingUp(List<Integer> left, List<Integer> right) {
        List<Integer> result = new ArrayList<>();

        // Move up: service right side first
        result.addAll(right);

        // Simulate reaching the end of the disk
        result.add(diskSize - 1);

        // Reverse direction: service left side in descending order
        Collections.reverse(left);
        result.addAll(left);

        return result;
    }

    private List<Integer> processMovingDown(List<Integer> left, List<Integer> right) {
        List<Integer> result = new ArrayList<>();

        // Move down: service left side in descending order
        Collections.reverse(left);
        result.addAll(left);

        // Simulate reaching the start of the disk
        result.add(0);

        // Reverse direction: service right side
        result.addAll(right);

        return result;
    }

    public int getHeadPosition() {
        return headPosition;
    }

    public boolean isMovingUp() {
        return movingUp;
    }
}