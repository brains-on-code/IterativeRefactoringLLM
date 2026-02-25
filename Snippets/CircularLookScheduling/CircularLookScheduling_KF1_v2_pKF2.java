package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Helper for reordering disk I/O requests based on:
 * <ul>
 *   <li>current head position</li>
 *   <li>scan direction</li>
 *   <li>disk size (maximum cylinder index, exclusive)</li>
 * </ul>
 */
public class Class1 {

    /** Current head position. */
    private int currentHead;

    /** Scan direction: {@code true} = forward (toward larger cylinders), {@code false} = backward. */
    private boolean forwardDirection;

    /** Exclusive upper bound for valid cylinder indices. */
    private final int maxCylinder;

    public Class1(int initialHead, boolean forwardDirection, int maxCylinder) {
        this.currentHead = initialHead;
        this.forwardDirection = forwardDirection;
        this.maxCylinder = maxCylinder;
    }

    /**
     * Reorders the given cylinder requests according to the current head position and direction.
     * Only requests in the range {@code [0, maxCylinder)} are considered.
     *
     * <p>Algorithm:
     * <ol>
     *   <li>Partition valid requests into:
     *     <ul>
     *       <li>{@code right}: requests &gt; current head</li>
     *       <li>{@code left}: requests &lt; current head</li>
     *     </ul>
     *   </li>
     *   <li>Sort both partitions in ascending order.</li>
     *   <li>Build the final order:
     *     <ul>
     *       <li>If scanning forward:
     *         <ul>
     *           <li>service {@code right} in ascending order, then {@code left} in ascending order</li>
     *         </ul>
     *       </li>
     *       <li>If scanning backward:
     *         <ul>
     *           <li>service {@code left} in descending order, then {@code right} in descending order</li>
     *         </ul>
     *       </li>
     *     </ul>
     *   </li>
     *   <li>If any request is serviced, update {@code currentHead} to the last serviced cylinder.</li>
     * </ol>
     *
     * @param requests list of requested cylinders
     * @return reordered list of valid requests
     */
    public List<Integer> method1(List<Integer> requests) {
        List<Integer> orderedRequests = new ArrayList<>();
        List<Integer> rightRequests = new ArrayList<>();
        List<Integer> leftRequests = new ArrayList<>();

        for (int request : requests) {
            if (request < 0 || request >= maxCylinder) {
                continue;
            }
            if (request > currentHead) {
                rightRequests.add(request);
            } else if (request < currentHead) {
                leftRequests.add(request);
            }
        }

        Collections.sort(rightRequests);
        Collections.sort(leftRequests);

        if (forwardDirection) {
            orderedRequests.addAll(rightRequests);
            orderedRequests.addAll(leftRequests);
        } else {
            Collections.reverse(leftRequests);
            orderedRequests.addAll(leftRequests);

            Collections.reverse(rightRequests);
            orderedRequests.addAll(rightRequests);
        }

        if (!orderedRequests.isEmpty()) {
            currentHead = orderedRequests.get(orderedRequests.size() - 1);
        }

        return orderedRequests;
    }

    /** Returns the current head position. */
    public int method2() {
        return currentHead;
    }

    /** Returns the current scan direction. */
    public boolean method3() {
        return forwardDirection;
    }
}