package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * SCAN (elevator) disk scheduling algorithm.
 *
 * <p>The disk head moves in one direction (up or down), servicing all requests
 * until it reaches the end of the disk, then reverses direction.</p>
 */
public class ScanScheduling {

    private final int headPosition;
    private final int diskSize;
    private final boolean movingUp;

    /**
     * Creates a SCAN scheduler.
     *
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
     * Runs the SCAN scheduling algorithm on the given list of requests.
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

        partitionRequests(requests, leftOfHead, rightOfHead);

        Collections.sort(leftOfHead);
        Collections.sort(rightOfHead);

        if (movingUp) {
            executeMovingUp(serviceOrder, leftOfHead, rightOfHead);
        } else {
            executeMovingDown(serviceOrder, leftOfHead, rightOfHead);
        }

        return serviceOrder;
    }

    private void partitionRequests(List<Integer> requests,
                                   List<Integer> leftOfHead,
                                   List<Integer> rightOfHead) {
        for (int request : requests) {
            if (request < headPosition) {
                leftOfHead.add(request);
            } else {
                rightOfHead.add(request);
            }
        }
    }

    private void executeMovingUp(List<Integer> serviceOrder,
                                 List<Integer> leftOfHead,
                                 List<Integer> rightOfHead) {
        // Service requests at/above the head, then go to the last track and reverse
        serviceOrder.addAll(rightOfHead);
        serviceOrder.add(diskSize - 1);
        Collections.reverse(leftOfHead);
        serviceOrder.addAll(leftOfHead);
    }

    private void executeMovingDown(List<Integer> serviceOrder,
                                   List<Integer> leftOfHead,
                                   List<Integer> rightOfHead) {
        // Service requests below the head, then go to the first track and reverse
        Collections.reverse(leftOfHead);
        serviceOrder.addAll(leftOfHead);
        serviceOrder.add(0);
        serviceOrder.addAll(rightOfHead);
    }

    public int getHeadPosition() {
        return headPosition;
    }

    public boolean isMovingUp() {
        return movingUp;
    }
}