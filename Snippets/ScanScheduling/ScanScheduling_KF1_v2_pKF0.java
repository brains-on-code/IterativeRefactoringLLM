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

        List<Integer> serviceOrder = new ArrayList<>();
        List<Integer> lowerThanHead = new ArrayList<>();
        List<Integer> higherOrEqualToHead = new ArrayList<>();

        for (int request : requests) {
            if (request < headPosition) {
                lowerThanHead.add(request);
            } else {
                higherOrEqualToHead.add(request);
            }
        }

        Collections.sort(lowerThanHead);
        Collections.sort(higherOrEqualToHead);

        if (movingTowardsHigher) {
            // Move towards higher tracks first
            serviceOrder.addAll(higherOrEqualToHead);
            serviceOrder.add(maxTrack - 1); // Go to the end
            Collections.reverse(lowerThanHead);
            serviceOrder.addAll(lowerThanHead);
        } else {
            // Move towards lower tracks first
            Collections.reverse(lowerThanHead);
            serviceOrder.addAll(lowerThanHead);
            serviceOrder.add(0); // Go to the beginning
            serviceOrder.addAll(higherOrEqualToHead);
        }

        return serviceOrder;
    }

    public int getHeadPosition() {
        return headPosition;
    }

    public boolean isMovingTowardsHigher() {
        return movingTowardsHigher;
    }
}