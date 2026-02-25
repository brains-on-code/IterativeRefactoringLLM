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

        // Partition requests into those below and above the current head position
        for (int request : requests) {
            if (request < headPosition) {
                left.add(request);
            } else {
                right.add(request);
            }
        }

        Collections.sort(left);
        Collections.sort(right);

        if (movingUp) {
            // Move towards higher-numbered tracks first
            result.addAll(right);

            // Simulate reaching the highest track, then reverse direction
            result.add(diskSize - 1);

            // Now service remaining requests while moving downwards
            Collections.reverse(left);
            result.addAll(left);
        } else {
            // Move towards lower-numbered tracks first
            Collections.reverse(left);
            result.addAll(left);

            // Simulate reaching the lowest track, then reverse direction
            result.add(0);

            // Now service remaining requests while moving upwards
            result.addAll(right);
        }

        return result;
    }

    public int getHeadPosition() {
        return headPosition;
    }

    public boolean isMovingUp() {
        return movingUp;
    }
}