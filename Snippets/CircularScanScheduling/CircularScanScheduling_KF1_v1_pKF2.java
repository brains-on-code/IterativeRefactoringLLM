package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implements a simple disk scheduling strategy similar to the SCAN (elevator) algorithm.
 *
 * <p>The scheduler maintains:
 * <ul>
 *   <li>currentHead: current position of the disk head</li>
 *   <li>moveRight: direction of head movement (true = towards larger tracks, false = towards smaller)</li>
 *   <li>maxTrack: upper bound (exclusive) for valid track numbers</li>
 * </ul>
 *
 * <p>Given a list of requested tracks, {@link #schedule(List)} returns the order in which
 * the tracks will be serviced according to the current head position and direction.
 */
public class Class1 {
    /** Current head position. */
    private int currentHead;

    /** Direction of movement: true = towards higher track numbers, false = towards lower. */
    private final boolean moveRight;

    /** Maximum valid track number (exclusive upper bound). */
    private final int maxTrack;

    public Class1(int initialHead, boolean moveRight, int maxTrack) {
        this.currentHead = initialHead;
        this.moveRight = moveRight;
        this.maxTrack = maxTrack;
    }

    /**
     * Schedule the given track requests according to a SCAN-like policy.
     *
     * <p>If moving right:
     * <ol>
     *   <li>Service all requests in [currentHead, maxTrack) in ascending order.</li>
     *   <li>Then service all remaining requests &lt; currentHead in ascending order.</li>
     * </ol>
     *
     * <p>If moving left:
     * <ol>
     *   <li>Service all requests &lt;= currentHead in descending order.</li>
     *   <li>Then service all remaining requests &gt; currentHead in descending order.</li>
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

        List<Integer> scheduledOrder = new ArrayList<>();

        if (moveRight) {
            // First: all requests at or after currentHead, but before maxTrack, in ascending order.
            for (int track : sortedRequests) {
                if (track >= currentHead && track < maxTrack) {
                    scheduledOrder.add(track);
                }
            }

            // Then: all remaining requests before currentHead, in ascending order.
            for (int track : sortedRequests) {
                if (track < currentHead) {
                    scheduledOrder.add(track);
                }
            }
        } else {
            // First: all requests at or before currentHead, in descending order.
            for (int i = sortedRequests.size() - 1; i >= 0; i--) {
                int track = sortedRequests.get(i);
                if (track <= currentHead) {
                    scheduledOrder.add(track);
                }
            }

            // Then: all remaining requests after currentHead, in descending order.
            for (int i = sortedRequests.size() - 1; i >= 0; i--) {
                int track = sortedRequests.get(i);
                if (track > currentHead) {
                    scheduledOrder.add(track);
                }
            }
        }

        // Update current head position to the last serviced track, if any.
        if (!scheduledOrder.isEmpty()) {
            currentHead = scheduledOrder.get(scheduledOrder.size() - 1);
        }

        return scheduledOrder;
    }

    /** @return current head position */
    public int getCurrentHead() {
        return currentHead;
    }

    /** @return movement direction: true if moving towards higher tracks, false otherwise */
    public boolean isMovingRight() {
        return moveRight;
    }
}