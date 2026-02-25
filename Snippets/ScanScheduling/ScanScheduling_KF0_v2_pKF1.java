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

    private final int startHeadPosition;
    private final int diskSize;
    private final boolean movingUpInitially;

    public ScanScheduling(int startHeadPosition, boolean movingUpInitially, int diskSize) {
        this.startHeadPosition = startHeadPosition;
        this.movingUpInitially = movingUpInitially;
        this.diskSize = diskSize;
    }

    public List<Integer> execute(List<Integer> requests) {
        if (requests.isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> serviceSequence = new ArrayList<>();
        List<Integer> lowerTrackRequests = new ArrayList<>();
        List<Integer> higherTrackRequests = new ArrayList<>();

        for (int track : requests) {
            if (track < startHeadPosition) {
                lowerTrackRequests.add(track);
            } else {
                higherTrackRequests.add(track);
            }
        }

        Collections.sort(lowerTrackRequests);
        Collections.sort(higherTrackRequests);

        if (movingUpInitially) {
            serviceSequence.addAll(higherTrackRequests);
            serviceSequence.add(diskSize - 1); // Simulate reaching the end of the disk
            Collections.reverse(lowerTrackRequests);
            serviceSequence.addAll(lowerTrackRequests);
        } else {
            Collections.reverse(lowerTrackRequests);
            serviceSequence.addAll(lowerTrackRequests);
            serviceSequence.add(0); // Simulate reaching the start of the disk
            serviceSequence.addAll(higherTrackRequests);
        }

        return serviceSequence;
    }

    public int getStartHeadPosition() {
        return startHeadPosition;
    }

    public boolean isMovingUpInitially() {
        return movingUpInitially;
    }
}