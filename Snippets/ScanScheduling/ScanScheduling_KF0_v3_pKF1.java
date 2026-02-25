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
        List<Integer> lowerTracks = new ArrayList<>();
        List<Integer> higherTracks = new ArrayList<>();

        for (int track : requestQueue) {
            if (track < initialHeadPosition) {
                lowerTracks.add(track);
            } else {
                higherTracks.add(track);
            }
        }

        Collections.sort(lowerTracks);
        Collections.sort(higherTracks);

        if (isMovingUpInitially) {
            serviceOrder.addAll(higherTracks);
            serviceOrder.add(diskSize - 1); // Simulate reaching the end of the disk
            Collections.reverse(lowerTracks);
            serviceOrder.addAll(lowerTracks);
        } else {
            Collections.reverse(lowerTracks);
            serviceOrder.addAll(lowerTracks);
            serviceOrder.add(0); // Simulate reaching the start of the disk
            serviceOrder.addAll(higherTracks);
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