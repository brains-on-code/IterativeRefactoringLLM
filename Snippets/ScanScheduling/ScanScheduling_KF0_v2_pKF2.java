package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of the SCAN (Elevator) disk scheduling algorithm.
 *
 * <p>The SCAN algorithm moves the disk head in one direction (up or down),
 * servicing all pending requests in that direction until it reaches the end
 * of the disk. It then reverses direction and services requests on the way
 * back.</p>
 *
 * <p>This approach provides a more uniform wait time compared to simple
 * algorithms like FCFS, while reducing overall head movement.</p>
 *
 * @see <a href="https://en.wikipedia.org/wiki/Elevator_algorithm">Elevator algorithm</a>
 */
public class ScanScheduling {

    private final int headPosition;
    private final int diskSize;
    private final boolean movingUp;

    /**
     * Creates a new SCAN scheduler.
     *
     * @param headPosition initial position of the disk head
     * @param movingUp     initial direction of head movement; {@code true} for
     *                     towards higher-numbered tracks, {@code false} for
     *                     towards lower-numbered tracks
     * @param diskSize     total number of tracks on the disk
     */
    public ScanScheduling(int headPosition, boolean movingUp, int diskSize) {
        this.headPosition = headPosition;
        this.movingUp = movingUp;
        this.diskSize = diskSize;
    }

    /**
     * Executes the SCAN scheduling algorithm on the given list of requests.
     *
     * @param requests list of requested track numbers
     * @return ordered list of track numbers representing the service sequence
     */
    public List<Integer> execute(List<Integer> requests) {
        if (requests.isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> result = new ArrayList<>();
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();

        partitionRequests(requests, left, right);

        Collections.sort(left);
        Collections.sort(right);

        if (movingUp) {
            handleUpwardMovement(result, left, right);
        } else {
            handleDownwardMovement(result, left, right);
        }

        return result;
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

    private void handleUpwardMovement(List<Integer> result, List<Integer> left, List<Integer> right) {
        result.addAll(right);
        result.add(diskSize - 1);
        Collections.reverse(left);
        result.addAll(left);
    }

    private void handleDownwardMovement(List<Integer> result, List<Integer> left, List<Integer> right) {
        Collections.reverse(left);
        result.addAll(left);
        result.add(0);
        result.addAll(right);
    }

    public int getHeadPosition() {
        return headPosition;
    }

    public boolean isMovingUp() {
        return movingUp;
    }
}