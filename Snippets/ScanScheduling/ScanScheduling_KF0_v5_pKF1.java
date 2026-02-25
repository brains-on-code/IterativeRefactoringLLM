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
 * other algorithms.
 */
public class ScanScheduling {

    private final int initialHeadPosition;
    private final int diskSize;
    private final boolean isMovingUpInitially;

    public ScanScheduling(int initialHeadPosition, boolean isMovingUpInitially, int diskSize) {
        this.initialHeadPosition = initialHeadPosition;
        this.isMovingUpInitially = isMovingUpInitially;
        this.diskSize = diskSize;
    }

    public List<Integer> execute(List<Integer> requestQueue) {
        if (requestQueue.isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> serviceOrder = new ArrayList<>();
        List<Integer> lowerTrackRequests = new ArrayList<>();
        List<Integer> higherTrackRequests = new ArrayList<>();

        for (int request : requestQueue) {
            if (request < initialHeadPosition) {
                lowerTrackRequests.add(request);
            } else {
                higherTrackRequests.add(request);
            }
        }

        Collections.sort(lowerTrackRequests);
        Collections.sort(higherTrackRequests);

        if (isMovingUpInitially) {
            serviceOrder.addAll(higherTrackRequests);
            serviceOrder.add(diskSize - 1); // Simulate reaching the end of the disk
            Collections.reverse(lowerTrackRequests);
            serviceOrder.addAll(lowerTrackRequests);
        } else {
            Collections.reverse(lowerTrackRequests);
            serviceOrder.addAll(lowerTrackRequests);
            serviceOrder.add(0); // Simulate reaching the start of the disk
            serviceOrder.addAll(higherTrackRequests);
        }

        return serviceOrder;
    }

    public int getInitialHeadPosition() {
        return initialHeadPosition;
    }

    public boolean isMovingUpInitially() {
        return isMovingUpInitially;
    }
}