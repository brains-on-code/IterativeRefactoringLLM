package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Disk scheduler implementing the SCAN (elevator) algorithm.
 *
 * <p>State:
 * <ul>
 *   <li>{@code currentHead}: current position of the disk head</li>
 *   <li>{@code moveRight}: movement direction
 *       (<b>true</b> = towards larger track numbers, <b>false</b> = towards smaller)</li>
 *   <li>{@code maxTrack}: exclusive upper bound for valid track numbers</li>
 * </ul>
 *
 * <p>Given a list of requested tracks, {@link #schedule(List)} returns the order in which
 * the tracks will be serviced based on the current head position and direction.
 */
public class Class1 {

    /** Current head position. */
    private int currentHead;

    /**
     * Movement direction:
     * <ul>
     *   <li>{@code true}: towards higher track numbers</li>
     *   <li>{@code false}: towards lower track numbers</li>
     * </ul>
     */
    private final boolean moveRight;

    /** Exclusive upper bound for valid track numbers. */
    private final int maxTrack;

    public Class1(int initialHead, boolean moveRight, int maxTrack) {
        this.currentHead = initialHead;
        this.moveRight = moveRight;
        this.maxTrack = maxTrack;
    }

    /**
     * Schedule the given track requests using a SCAN-like policy.
     *
     * <p>If moving right:
     * <ol>
     *   <li>Service all requests in [{@code currentHead}, {@code maxTrack}) in ascending order.</li>
     *   <li>Then service all remaining requests &lt; {@code currentHead} in ascending order.</li>
     * </ol>
     *
     * <p>If moving left:
     * <ol>
     *   <li>Service all requests &lt;= {@code currentHead} in descending order.</li>
     *   <li>Then service all remaining requests &gt; {@code currentHead} in descending order.</li>
     * </ol>
     *
     * @param requests list of requested track numbers
     * @return ordered list of tracks in the sequence they will be serviced
     */
    public List<Integer> schedule(List<Integer> requests) {
        if (requests.isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> sortedRequests = new ArrayList<>(requests);
        Collections.sort(sortedRequests);

        List<Integer> scheduledOrder = moveRight
            ? scheduleMovingRight(sortedRequests)
            : scheduleMovingLeft(sortedRequests);

        if (!scheduledOrder.isEmpty()) {
            currentHead = scheduledOrder.get(scheduledOrder.size() - 1);
        }

        return scheduledOrder;
    }

    /**
     * Schedule requests when the head is moving towards higher track numbers.
     *
     * <p>Order:
     * <ol>
     *   <li>All requests in [{@code currentHead}, {@code maxTrack}) in ascending order.</li>
     *   <li>All remaining requests &lt; {@code currentHead} in ascending order.</li>
     * </ol>
     */
    private List<Integer> scheduleMovingRight(List<Integer> sortedRequests) {
        List<Integer> scheduledOrder = new ArrayList<>();

        for (int track : sortedRequests) {
            if (isWithinRightScanRange(track)) {
                scheduledOrder.add(track);
            }
        }

        for (int track : sortedRequests) {
            if (track < currentHead) {
                scheduledOrder.add(track);
            }
        }

        return scheduledOrder;
    }

    /**
     * Schedule requests when the head is moving towards lower track numbers.
     *
     * <p>Order:
     * <ol>
     *   <li>All requests &lt;= {@code currentHead} in descending order.</li>
     *   <li>All remaining requests &gt; {@code currentHead} in descending order.</li>
     * </ol>
     */
    private List<Integer> scheduleMovingLeft(List<Integer> sortedRequests) {
        List<Integer> scheduledOrder = new ArrayList<>();

        for (int i = sortedRequests.size() - 1; i >= 0; i--) {
            int track = sortedRequests.get(i);
            if (track <= currentHead) {
                scheduledOrder.add(track);
            }
        }

        for (int i = sortedRequests.size() - 1; i >= 0; i--) {
            int track = sortedRequests.get(i);
            if (track > currentHead) {
                scheduledOrder.add(track);
            }
        }

        return scheduledOrder;
    }

    private boolean isWithinRightScanRange(int track) {
        return track >= currentHead && track < maxTrack;
    }

    /** Returns the current head position. */
    public int getCurrentHead() {
        return currentHead;
    }

    /**
     * Returns the current movement direction.
     *
     * @return {@code true} if the head is moving towards higher tracks,
     *         {@code false} if it is moving towards lower tracks
     */
    public boolean isMovingRight() {
        return moveRight;
    }
}