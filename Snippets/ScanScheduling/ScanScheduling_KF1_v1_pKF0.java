package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Disk scheduling algorithm implementation.
 *
 * This class simulates a disk head moving across tracks and generating
 * a service order for a given list of track requests.
 */
public class Class1 {

    /** Current head position. */
    private final int headPosition;

    /** Total number of tracks on the disk (or upper bound). */
    private final int maxTrack;

    /** Direction flag: true for moving towards higher tracks, false for lower. */
    private final boolean moveTowardsHigher;

    public Class1(int headPosition, boolean moveTowardsHigher, int maxTrack) {
        this.headPosition = headPosition;
        this.moveTowardsHigher = moveTowardsHigher;
        this.maxTrack = maxTrack;
    }

    /**
     * Generates the order in which disk tracks will be serviced.
     *
     * @param requests list of requested track numbers
     * @return ordered list of tracks representing the service sequence
     */
    public List<Integer> method1(List<Integer> requests) {
        if (requests.isEmpty()) {
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

        if (moveTowardsHigher) {
            serviceOrder.addAll(higherOrEqualToHead);
            serviceOrder.add(maxTrack - 1);
            Collections.reverse(lowerThanHead);
            serviceOrder.addAll(lowerThanHead);
        } else {
            Collections.reverse(lowerThanHead);
            serviceOrder.addAll(lowerThanHead);
            serviceOrder.add(0);
            serviceOrder.addAll(higherOrEqualToHead);
        }

        return serviceOrder;
    }

    public int method2() {
        return headPosition;
    }

    public boolean method3() {
        return moveTowardsHigher;
    }
}