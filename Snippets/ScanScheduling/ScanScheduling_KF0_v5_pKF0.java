package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * https://en.wikipedia.org/wiki/Elevator_algorithm
 * SCAN Scheduling algorithm implementation.
 *
 * The SCAN algorithm moves the disk arm towards one end of the disk, servicing all
 * requests along the way until it reaches the end. Once it reaches the end, it
 * reverses direction and services the requests on its way back.
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

        List<Integer> lowerThanHead = new ArrayList<>();
        List<Integer> greaterOrEqualHead = new ArrayList<>();
        partitionRequestsByHeadPosition(requests, lowerThanHead, greaterOrEqualHead);

        Collections.sort(lowerThanHead);
        Collections.sort(greaterOrEqualHead);

        return movingUp
            ? buildServiceOrderMovingUp(lowerThanHead, greaterOrEqualHead)
            : buildServiceOrderMovingDown(lowerThanHead, greaterOrEqualHead);
    }

    private void partitionRequestsByHeadPosition(
        List<Integer> requests,
        List<Integer> lowerThanHead,
        List<Integer> greaterOrEqualHead
    ) {
        for (int request : requests) {
            if (request < headPosition) {
                lowerThanHead.add(request);
            } else {
                greaterOrEqualHead.add(request);
            }
        }
    }

    private List<Integer> buildServiceOrderMovingUp(
        List<Integer> lowerThanHead,
        List<Integer> greaterOrEqualHead
    ) {
        List<Integer> serviceOrder = new ArrayList<>();

        // Move up: service requests on the right side of the head first
        serviceOrder.addAll(greaterOrEqualHead);

        // Simulate reaching the end of the disk
        serviceOrder.add(diskSize - 1);

        // Reverse direction: service left side in descending order
        Collections.reverse(lowerThanHead);
        serviceOrder.addAll(lowerThanHead);

        return serviceOrder;
    }

    private List<Integer> buildServiceOrderMovingDown(
        List<Integer> lowerThanHead,
        List<Integer> greaterOrEqualHead
    ) {
        List<Integer> serviceOrder = new ArrayList<>();

        // Move down: service left side in descending order
        Collections.reverse(lowerThanHead);
        serviceOrder.addAll(lowerThanHead);

        // Simulate reaching the start of the disk
        serviceOrder.add(0);

        // Reverse direction: service right side
        serviceOrder.addAll(greaterOrEqualHead);

        return serviceOrder;
    }

    public int getHeadPosition() {
        return headPosition;
    }

    public boolean isMovingUp() {
        return movingUp;
    }

    public int getDiskSize() {
        return diskSize;
    }
}