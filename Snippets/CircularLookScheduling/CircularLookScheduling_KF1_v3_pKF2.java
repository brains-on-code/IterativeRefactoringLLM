package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Reorders disk I/O requests based on:
 * <ul>
 *   <li>current head position</li>
 *   <li>scan direction</li>
 *   <li>disk size (maximum cylinder index, exclusive)</li>
 * </ul>
 */
public class Class1 {

    /** Current head position. */
    private int currentHead;

    /**
     * Scan direction:
     * <ul>
     *   <li>{@code true}  = forward (toward larger cylinder indices)</li>
     *   <li>{@code false} = backward (toward smaller cylinder indices)</li>
     * </ul>
     */
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
     * <p>Algorithm overview:
     * <ol>
     *   <li>Filter out invalid requests (outside {@code [0, maxCylinder)}).</li>
     *   <li>Partition remaining requests into:
     *     <ul>
     *       <li>{@code rightRequests}: requests &gt; current head</li>
     *       <li>{@code leftRequests}:  requests &lt; current head</li>
     *     </ul>
     *   </li>
     *   <li>Sort both partitions in ascending order.</li>
     *   <li>Build the final service order:
     *     <ul>
     *       <li>If scanning forward:
     *         <ul>
     *           <li>service {@code rightRequests} in ascending order, then {@code leftRequests} in ascending order</li>
     *         </ul>
     *       </li>
     *       <li>If scanning backward:
     *         <ul>
     *           <li>service {@code leftRequests} in descending order, then {@code rightRequests} in descending order</li>
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