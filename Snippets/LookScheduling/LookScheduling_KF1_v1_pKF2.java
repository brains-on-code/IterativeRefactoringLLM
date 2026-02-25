package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implements a simple disk scheduling helper that partitions and orders
 * requests around a current head position, tracking the last serviced
 * request and current scan direction.
 */
public class Class1 {

    /** Maximum valid cylinder (exclusive upper bound). */
    private final int maxCylinder;

    /** Current head position. */
    private final int headPosition;

    /** Current scan direction: true = towards higher cylinders, false = towards lower. */
    private boolean scanningUpwards;

    /** Last serviced request. */
    private int lastServicedRequest;

    /**
     * Creates a new scheduler state.
     *
     * @param headPosition   current head position
     * @param scanningUpwards initial scan direction
     * @param maxCylinder    exclusive upper bound for valid cylinder numbers
     */
    public Class1(int headPosition, boolean scanningUpwards, int maxCylinder) {
        this.headPosition = headPosition;
        this.scanningUpwards = scanningUpwards;
        this.maxCylinder = maxCylinder;
    }

    /**
     * Orders the given list of cylinder requests based on the current head
     * position and scan direction, similar to a SCAN-like strategy.
     *
     * <p>Requests outside the range [0, maxCylinder) are ignored.</p>
     *
     * @param requests list of requested cylinder positions
     * @return ordered list of requests to service
     */
    public List<Integer> method1(List<Integer> requests) {
        List<Integer> orderedRequests = new ArrayList<>();
        List<Integer> lowerThanHead = new ArrayList<>();
        List<Integer> higherOrEqualToHead = new ArrayList<>();

        // Partition valid requests into those below and those at/above the head position.
        for (int request : requests) {
            if (request >= 0 && request < maxCylinder) {
                if (request < headPosition) {
                    lowerThanHead.add(request);
                } else {
                    higherOrEqualToHead.add(request);
                }
            }
        }

        // Sort each partition.
        Collections.sort(lowerThanHead);
        Collections.sort(higherOrEqualToHead);

        if (scanningUpwards) {
            // First service requests at/above the head, then reverse direction.
            orderedRequests.addAll(higherOrEqualToHead);
            if (!higherOrEqualToHead.isEmpty()) {
                lastServicedRequest = higherOrEqualToHead.get(higherOrEqualToHead.size() - 1);
            }

            scanningUpwards = false;
            Collections.reverse(lowerThanHead);
            orderedRequests.addAll(lowerThanHead);
            if (!lowerThanHead.isEmpty()) {
                lastServicedRequest = Math.max(lastServicedRequest, lowerThanHead.get(0));
            }
        } else {
            // First service requests below the head (from closest downwards), then reverse direction.
            Collections.reverse(lowerThanHead);
            orderedRequests.addAll(lowerThanHead);
            if (!lowerThanHead.isEmpty()) {
                lastServicedRequest = lowerThanHead.get(0);
            }

            scanningUpwards = true;
            orderedRequests.addAll(higherOrEqualToHead);
            if (!higherOrEqualToHead.isEmpty()) {
                lastServicedRequest =
                    Math.max(lastServicedRequest, higherOrEqualToHead.get(higherOrEqualToHead.size() - 1));
            }
        }

        return orderedRequests;
    }

    /** @return the current head position. */
    public int method2() {
        return headPosition;
    }

    /** @return the current scan direction: true if scanning upwards. */
    public boolean method3() {
        return scanningUpwards;
    }

    /** @return the last serviced request. */
    public int method4() {
        return lastServicedRequest;
    }
}