package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implements a simple disk scheduling helper that reorders requests
 * based on a current head position, direction, and disk size.
 */
public class Class1 {
    /** Current head position. */
    private int currentHead;

    /** Direction flag: true = forward, false = backward. */
    private boolean forwardDirection;

    /** Maximum valid cylinder (exclusive upper bound). */
    private final int maxCylinder;

    public Class1(int initialHead, boolean forwardDirection, int maxCylinder) {
        this.currentHead = initialHead;
        this.forwardDirection = forwardDirection;
        this.maxCylinder = maxCylinder;
    }

    /**
     * Reorders the given list of cylinder requests according to the current
     * head position and direction. Only requests in the range [0, maxCylinder)
     * are considered.
     *
     * <p>Behavior:
     * <ul>
     *   <li>Requests greater than the current head go into the "right" list.</li>
     *   <li>Requests less than the current head go into the "left" list.</li>
     *   <li>Both lists are sorted in ascending order.</li>
     *   <li>If direction is forward:
     *       <ul>
     *         <li>Result = right (ascending) + left (ascending).</li>
     *       </ul>
     *       If direction is backward:
     *       <ul>
     *         <li>Result = left (descending) + right (descending).</li>
     *       </ul>
     *   </li>
     *   <li>The head position is updated to the last serviced request
     *       if the result is not empty.</li>
     * </ul>
     *
     * @param requests list of requested cylinders
     * @return reordered list of valid requests
     */
    public List<Integer> method1(List<Integer> requests) {
        List<Integer> orderedRequests = new ArrayList<>();

        // Requests to the right (greater than current head)
        List<Integer> rightRequests = new ArrayList<>();
        // Requests to the left (less than current head)
        List<Integer> leftRequests = new ArrayList<>();

        for (int request : requests) {
            if (request >= 0 && request < maxCylinder) {
                if (request > currentHead) {
                    rightRequests.add(request);
                } else if (request < currentHead) {
                    leftRequests.add(request);
                }
            }
        }

        Collections.sort(rightRequests);
        Collections.sort(leftRequests);

        if (forwardDirection) {
            // Move forward first, then wrap and service the left side
            orderedRequests.addAll(rightRequests);
            orderedRequests.addAll(leftRequests);
        } else {
            // Move backward first, then wrap and service the right side
            Collections.reverse(leftRequests);
            orderedRequests.addAll(leftRequests);

            Collections.reverse(rightRequests);
            orderedRequests.addAll(rightRequests);
        }

        // Update head position to the last serviced request
        if (!orderedRequests.isEmpty()) {
            currentHead = orderedRequests.get(orderedRequests.size() - 1);
        }

        return orderedRequests;
    }

    public int method2() {
        return currentHead;
    }

    public boolean method3() {
        return forwardDirection;
    }
}