package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implements the Circular SCAN (C-SCAN) disk scheduling algorithm.
 */
public class CScanScheduler {
    private int currentHeadPosition;
    private final boolean isMovingTowardsHigherTracks;
    private final int maximumTrackNumber;

    public CScanScheduler(int initialHeadPosition, boolean isMovingTowardsHigherTracks, int maximumTrackNumber) {
        this.currentHeadPosition = initialHeadPosition;
        this.isMovingTowardsHigherTracks = isMovingTowardsHigherTracks;
        this.maximumTrackNumber = maximumTrackNumber;
    }

    public List<Integer> getServiceOrder(List<Integer> trackRequests) {
        if (trackRequests.isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> sortedTrackRequests = new ArrayList<>(trackRequests);
        Collections.sort(sortedTrackRequests);

        List<Integer> serviceOrder = new ArrayList<>();

        if (isMovingTowardsHigherTracks) {
            for (int track : sortedTrackRequests) {
                if (track >= currentHeadPosition && track < maximumTrackNumber) {
                    serviceOrder.add(track);
                }
            }

            for (int track : sortedTrackRequests) {
                if (track < currentHeadPosition) {
                    serviceOrder.add(track);
                }
            }
        } else {
            for (int index = sortedTrackRequests.size() - 1; index >= 0; index--) {
                int track = sortedTrackRequests.get(index);
                if (track <= currentHeadPosition) {
                    serviceOrder.add(track);
                }
            }

            for (int index = sortedTrackRequests.size() - 1; index >= 0; index--) {
                int track = sortedTrackRequests.get(index);
                if (track > currentHeadPosition) {
                    serviceOrder.add(track);
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
        return isMovingTowardsHigherTracks;
    }
}