package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implements the Circular SCAN (C-SCAN) disk scheduling algorithm.
 */
public class CScanScheduler {
    private int currentHeadPosition;
    private final boolean movingTowardsHigherTracks;
    private final int maxTrack;

    public CScanScheduler(int initialHeadPosition, boolean movingTowardsHigherTracks, int maxTrack) {
        this.currentHeadPosition = initialHeadPosition;
        this.movingTowardsHigherTracks = movingTowardsHigherTracks;
        this.maxTrack = maxTrack;
    }

    public List<Integer> getServiceOrder(List<Integer> requestQueue) {
        if (requestQueue.isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> sortedRequests = new ArrayList<>(requestQueue);
        Collections.sort(sortedRequests);

        List<Integer> serviceOrder = new ArrayList<>();

        if (movingTowardsHigherTracks) {
            for (int request : sortedRequests) {
                if (request >= currentHeadPosition && request < maxTrack) {
                    serviceOrder.add(request);
                }
            }

            for (int request : sortedRequests) {
                if (request < currentHeadPosition) {
                    serviceOrder.add(request);
                }
            }
        } else {
            for (int i = sortedRequests.size() - 1; i >= 0; i--) {
                int request = sortedRequests.get(i);
                if (request <= currentHeadPosition) {
                    serviceOrder.add(request);
                }
            }

            for (int i = sortedRequests.size() - 1; i >= 0; i--) {
                int request = sortedRequests.get(i);
                if (request > currentHeadPosition) {
                    serviceOrder.add(request);
                }
            }
        }

        if (!serviceOrder.isEmpty()) {
            currentHeadPosition = serviceOrder.get(serviceOrder.size() - 1);
        }
        return serviceOrder;
    }

    public int getCurrentHeadPosition() {
        return currentHeadPosition;
    }

    public boolean isMovingTowardsHigherTracks() {
        return movingTowardsHigherTracks;
    }
}