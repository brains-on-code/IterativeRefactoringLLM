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

        for (int request : requests) {
            if (isOutOfRange(request)) {
                continue;
            }
            if (request < headPosition) {
                lowerThanHead.add(request);
            } else {
                higherOrEqualToHead.add(request);
            }
        }

        Collections.sort(lowerThanHead);
        Collections.sort(higherOrEqualToHead);

        if (scanningUpwards) {
            serviceUpwards(orderedRequests, lowerThanHead, higherOrEqualToHead);
        } else {
            serviceDownwards(orderedRequests, lowerThanHead, higherOrEqualToHead);
        }

        return orderedRequests;
    }

    private boolean isOutOfRange(int request) {
        return request < 0 || request >= maxCylinder;
    }

    /**
     * Services requests when scanning upwards:
     * <ol>
     *   <li>Service all requests at/above the head in ascending order.</li>
     *   <li>Reverse direction and service all requests below the head in descending order.</li>
     * </ol>
     */
    private void serviceUpwards(
        List<Integer> orderedRequests,
        List<Integer> lowerThanHead,
        List<Integer> higherOrEqualToHead
    ) {
        orderedRequests.addAll(higherOrEqualToHead);
        if (!higherOrEqualToHead.isEmpty()) {
            lastServicedRequest = higherOrEqualToHead.get(higherOrEqualToHead.size() - 1);
        }

        scanningUpwards = false;

        Collections.reverse(lowerThanHead);
        orderedRequests.addAll(lowerThanHead);
        if (!lowerThanHead.isEmpty()) {
            lastServicedRequest = lowerThanHead.get(0);
        }
    }

    /**
     * Services requests when scanning downwards:
     * <ol>
     *   <li>Service all requests below the head in descending order.</li>
     *   <li>Reverse direction and service all requests at/above the head in ascending order.</li>
     * </ol>
     */
    private void serviceDownwards(
        List<Integer> orderedRequests,
        List<Integer> lowerThanHead,
        List<Integer> higherOrEqualToHead
    ) {
        Collections.reverse(lowerThanHead);
        orderedRequests.addAll(lowerThanHead);
        if (!lowerThanHead.isEmpty()) {
            lastServicedRequest = lowerThanHead.get(0);
        }

        scanningUpwards = true;

        orderedRequests.addAll(higherOrEqualToHead);
        if (!higherOrEqualToHead.isEmpty()) {
            lastServicedRequest = higherOrEqualToHead.get(higherOrEqualToHead.size() - 1);
        }
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