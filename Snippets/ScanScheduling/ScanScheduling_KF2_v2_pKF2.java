package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implements the SCAN (elevator) disk scheduling algorithm.
 *
 * <p>The disk head moves in one direction (up or down) servicing all requests
 * until it reaches the end of the disk, then reverses direction.</p>
 */
public class ScanScheduling {

    private final int headPosition;
    private final int diskSize;
    private final boolean movingUp;

    /**
     * @param headPosition initial position of the disk head
     * @param movingUp     true if the head initially moves towards higher tracks
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
     * @return ordered list of tracks in the sequence they will be serviced
     */
    public List<Integer> execute(List<Integer> requests) {
        if (requests == null || requests.isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> serviceOrder = new ArrayList<>();
        List<Integer> leftOfHead = new ArrayList<>();
        List<Integer> rightOfHead = new ArrayList<>();

        // Partition requests into those below and above the current head position
        for (int request : requests) {
            if (request < headPosition) {
                leftOfHead.add(request);
            } else {
                rightOfHead.add(request);
            }
        }

        Collections.sort(leftOfHead);
        Collections.sort(rightOfHead);

        if (movingUp) {
            // Move towards higher tracks first, then reverse
            serviceOrder.addAll(rightOfHead);
            serviceOrder.add(diskSize - 1); // reach the last track before reversing
            Collections.reverse(leftOfHead);
            serviceOrder.addAll(leftOfHead);
        } else {
            // Move towards lower tracks first, then reverse
            Collections.reverse(leftOfHead);
            serviceOrder.addAll(leftOfHead);
            serviceOrder.add(0); // reach the first track before reversing
            serviceOrder.addAll(rightOfHead);
        }

        return serviceOrder;
    }

    public int getHeadPosition() {
        return headPosition;
    }

    public boolean isMovingUp() {
        return movingUp;
    }
}