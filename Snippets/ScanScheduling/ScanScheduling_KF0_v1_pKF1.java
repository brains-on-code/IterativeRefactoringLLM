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
        List<Integer> lowerRequests = new ArrayList<>();
        List<Integer> higherRequests = new ArrayList<>();

        for (int request : requestQueue) {
            if (request < initialHeadPosition) {
                lowerRequests.add(request);
            } else {
                higherRequests.add(request);
            }
        }

        Collections.sort(lowerRequests);
        Collections.sort(higherRequests);

        if (isMovingUpInitially) {
            serviceOrder.addAll(higherRequests);
            serviceOrder.add(diskSize - 1); // Simulate reaching the end of the disk
            Collections.reverse(lowerRequests);
            serviceOrder.addAll(lowerRequests);
        } else {
            Collections.reverse(lowerRequests);
            serviceOrder.addAll(lowerRequests);
            serviceOrder.add(0); // Simulate reaching the start of the disk
            serviceOrder.addAll(higherRequests);
        }

        return serviceOrder;
    }

    public int getHeadPosition() {
        return initialHeadPosition;
    }

    public boolean isMovingUp() {
        return isMovingUpInitially;
    }
}