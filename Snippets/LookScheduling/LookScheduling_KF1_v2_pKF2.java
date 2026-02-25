package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the state and behavior of a simple SCAN-like disk scheduler.
 *
 * <p>The scheduler:
 * <ul>
 *   <li>Partitions requests into those below and at/above the current head position.</li>
 *   <li>Services them in an order determined by the current scan direction.</li>
 *   <li>Tracks the last serviced request and the current scan direction.</li>
 * </ul>
 */
public class Class1 {

    /** Exclusive upper bound for valid cylinder numbers (valid range is [0, maxCylinder)). */
    private final int maxCylinder;

    /** Current head position. */
    private final int headPosition;

    /** Current scan direction: {@code true} = towards higher cylinders, {@code false} = towards lower. */
    private boolean scanningUpwards;

    /** Last serviced request. */
    private int lastServicedRequest;

    /**
     * Creates a new scheduler state.
     *
     * @param headPosition    current head position
     * @param scanningUpwards initial scan direction
     * @param maxCylinder     exclusive upper bound for valid cylinder numbers
     */
    public Class1(int headPosition, boolean scanningUpwards, int maxCylinder) {
        this.headPosition = headPosition;
        this.scanningUpwards = scanningUpwards;
        this.maxCylinder = maxCylinder;
    }

    /**
     * Orders the given list of cylinder requests based on the current head
     * position and scan direction, using a SCAN-like strategy.
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

        // Filter out-of-range requests and partition valid ones by head position.
        for (int request : requests) {
            if (request < 0 || request >= maxCylinder) {
                continue;
            }
            if (request < headPosition) {
                lowerThanHead.add(request);
            } else {
                higherOrEqualToHead.add(request);
            }
        }

        // Sort each partition in ascending order.
        Collections.sort(lowerThanHead);
        Collections.sort(higherOrEqualToHead);

        if (scanningUpwards) {
            // Service requests at/above the head first (ascending), then reverse direction.
            orderedRequests.addAll(higherOrEqualToHead);
            if (!higherOrEqualToHead.isEmpty()) {
                lastServicedRequest = higherOrEqualToHead.get(higherOrEqualToHead.size() - 1);
            }

            scanningUpwards = false;

            // Now service requests below the head in descending order.
            Collections.reverse(lowerThanHead);
            orderedRequests.addAll(lowerThanHead);
            if (!lowerThanHead.isEmpty()) {
                lastServicedRequest = Math.max(lastServicedRequest, lowerThanHead.get(0));
            }
        } else {
            // Service requests below the head first in descending order.
            Collections.reverse(lowerThanHead);
            orderedRequests.addAll(lowerThanHead);
            if (!lowerThanHead.isEmpty()) {
                lastServicedRequest = lowerThanHead.get(0);
            }

            scanningUpwards = true;

            // Then service requests at/above the head in ascending order.
            orderedRequests.addAll(higherOrEqualToHead);
            if (!higherOrEqualToHead.isEmpty()) {
                lastServicedRequest =
                    Math.max(lastServicedRequest, higherOrEqualToHead.get(higherOrEqualToHead.size() - 1));
            }
        }

        return orderedRequests;
    }

    /** Returns the current head position. */
    public int method2() {
        return headPosition;
    }

    /** Returns the current scan direction: {@code true} if scanning upwards. */
    public boolean method3() {
        return scanningUpwards;
    }

    /** Returns the last serviced request. */
    public int method4() {
        return lastServicedRequest;
    }
}