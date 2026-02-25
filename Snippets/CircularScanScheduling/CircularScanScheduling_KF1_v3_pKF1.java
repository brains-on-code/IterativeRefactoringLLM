package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implements the Circular SCAN (C-SCAN) disk scheduling algorithm.
 */
public class CScanScheduler {
    private int headPosition;
    private final boolean movingTowardsHigherTracks;
    private final int maxTrackNumber;

    public CScanScheduler(int initialHeadPosition, boolean movingTowardsHigherTracks, int maxTrackNumber) {
        this.headPosition = initialHeadPosition;
        this.movingTowardsHigherTracks = movingTowardsHigherTracks;
        this.maxTrackNumber = maxTrackNumber;
    }

    public List<Integer> getServiceOrder(List<Integer> trackRequests) {
        if (trackRequests.isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> sortedTrackRequests = new ArrayList<>(trackRequests);
        Collections.sort(sortedTrackRequests);

        List<Integer> serviceOrder = new ArrayList<>();

        if (movingTowardsHigherTracks) {
            for (int track : sortedTrackRequests) {
                if (track >= headPosition && track < maxTrackNumber) {
                    serviceOrder.add(track);
                }
            }

            for (int track : sortedTrackRequests) {
                if (track < headPosition) {
                    serviceOrder.add(track);
                }
            }
        } else {
            for (int i = sortedTrackRequests.size() - 1; i >= 0; i--) {
                int track = sortedTrackRequests.get(i);
                if (track <= headPosition) {
                    serviceOrder.add(track);
                }
            }

            for (int i = sortedTrackRequests.size() - 1; i >= 0; i--) {
                int track = sortedTrackRequests.get(i);
                if (track > headPosition) {
                    serviceOrder.add(track);
                }
            }
        }

        if (!serviceOrder.isEmpty()) {
            headPosition = serviceOrder.get(serviceOrder.size() - 1);
        }

        return serviceOrder;
    }

    public int getCurrentHeadPosition() {
        return headPosition;
    }

    public boolean isMovingTowardsHigherTracks() {
        return movingTowardsHigherTracks;
    }
}