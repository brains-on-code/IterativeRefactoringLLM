package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Disk scheduling algorithm implementation (SCAN-like).
 *
 * This class simulates a disk head moving across tracks and generating
 * a service order for a given list of track requests.
 */
public class DiskScheduler {

    /** Current head position. */
    private final int headPosition;

    /** Total number of tracks on the disk (or upper bound). */
    private final int maxTrack;

    /** Direction flag: true for moving towards higher tracks, false for lower. */
    private final boolean movingTowardsHigher;

    public DiskScheduler(int headPosition, boolean movingTowardsHigher, int maxTrack) {
        this.headPosition = headPosition;
        this.movingTowardsHigher = movingTowardsHigher;
        this.maxTrack = maxTrack;
    }

    /**
     * Generates the order in which disk tracks will be serviced.
     *
     * @param requests list of requested track numbers
     * @return ordered list of tracks representing the service sequence
     */
    public List<Integer> generateServiceOrder(List<Integer> requests) {
        if (requests == null || requests.isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> lowerThanHead = new ArrayList<>();
        List<Integer> higherOrEqualToHead = new ArrayList<>();

        partitionRequests(requests, lowerThanHead, higherOrEqualToHead);
        sortPartitions(lowerThanHead, higherOrEqualToHead);

        return movingTowardsHigher
            ? buildOrderMovingHigher(lowerThanHead, higherOrEqualToHead)
            : buildOrderMovingLower(lowerThanHead, higherOrEqualToHead);
    }

    private void partitionRequests(List<Integer> requests,
                                   List<Integer> lowerThanHead,
                                   List<Integer> higherOrEqualToHead) {
        for (int request : requests) {
            if (request < headPosition) {
                lowerThanHead.add(request);
            } else {
                higherOrEqualToHead.add(request);
            }
        }
    }

    private void sortPartitions(List<Integer> lowerThanHead,
                                List<Integer> higherOrEqualToHead) {
        Collections.sort(lowerThanHead);
        Collections.sort(higherOrEqualToHead);
    }

    private List<Integer> buildOrderMovingHigher(List<Integer> lowerThanHead,
                                                 List<Integer> higherOrEqualToHead) {
        List<Integer> serviceOrder = new ArrayList<>();

        // Move towards higher tracks first
        serviceOrder.addAll(higherOrEqualToHead);
        serviceOrder.add(maxTrack - 1); // Go to the end

        Collections.reverse(lowerThanHead);
        serviceOrder.addAll(lowerThanHead);

        return serviceOrder;
    }

    private List<Integer> buildOrderMovingLower(List<Integer> lowerThanHead,
                                                List<Integer> higherOrEqualToHead) {
        List<Integer> serviceOrder = new ArrayList<>();

        // Move towards lower tracks first
        Collections.reverse(lowerThanHead);
        serviceOrder.addAll(lowerThanHead);
        serviceOrder.add(0); // Go to the beginning
        serviceOrder.addAll(higherOrEqualToHead);

        return serviceOrder;
    }

    public int getHeadPosition() {
        return headPosition;
    }

    public boolean isMovingTowardsHigher() {
        return movingTowardsHigher;
    }
}