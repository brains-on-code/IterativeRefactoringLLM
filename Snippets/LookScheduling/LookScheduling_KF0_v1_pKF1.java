package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * https://en.wikipedia.org/wiki/LOOK_algorithm
 * Look Scheduling algorithm implementation.
 * The Look algorithm moves the disk arm to the closest request in the current direction,
 * and once it processes all requests in that direction, it reverses the direction.
 */
public class LookScheduling {
    private final int maxTrackNumber;
    private final int initialHeadPosition;
    private boolean isMovingUpward;
    private int farthestServicedTrack;

    public LookScheduling(int initialHeadPosition, boolean initialDirectionUpward, int maxTrackNumber) {
        this.initialHeadPosition = initialHeadPosition;
        this.isMovingUpward = initialDirectionUpward;
        this.maxTrackNumber = maxTrackNumber;
    }

    /**
     * Executes the Look Scheduling algorithm on the given list of requests.
     *
     * @param trackRequests List of disk track requests.
     * @return Order in which requests are processed.
     */
    public List<Integer> execute(List<Integer> trackRequests) {
        List<Integer> serviceOrder = new ArrayList<>();
        List<Integer> lowerTrackRequests = new ArrayList<>();
        List<Integer> upperTrackRequests = new ArrayList<>();

        // Split requests into two lists based on their position relative to current head position
        for (int track : trackRequests) {
            if (track >= 0 && track < maxTrackNumber) {
                if (track < initialHeadPosition) {
                    lowerTrackRequests.add(track);
                } else {
                    upperTrackRequests.add(track);
                }
            }
        }

        // Sort the requests
        Collections.sort(lowerTrackRequests);
        Collections.sort(upperTrackRequests);

        // Process the requests depending on the initial moving direction
        if (isMovingUpward) {
            // Process requests in the upward direction
            serviceOrder.addAll(upperTrackRequests);
            if (!upperTrackRequests.isEmpty()) {
                farthestServicedTrack = upperTrackRequests.get(upperTrackRequests.size() - 1);
            }

            // Reverse the direction and process downward
            isMovingUpward = false;
            Collections.reverse(lowerTrackRequests);
            serviceOrder.addAll(lowerTrackRequests);
            if (!lowerTrackRequests.isEmpty()) {
                farthestServicedTrack = Math.max(farthestServicedTrack, lowerTrackRequests.get(0));
            }
        } else {
            // Process requests in the downward direction
            Collections.reverse(lowerTrackRequests);
            serviceOrder.addAll(lowerTrackRequests);
            if (!lowerTrackRequests.isEmpty()) {
                farthestServicedTrack = lowerTrackRequests.get(0);
            }

            // Reverse the direction and process upward
            isMovingUpward = true;
            serviceOrder.addAll(upperTrackRequests);
            if (!upperTrackRequests.isEmpty()) {
                farthestServicedTrack = Math.max(farthestServicedTrack, upperTrackRequests.get(upperTrackRequests.size() - 1));
            }
        }

        return serviceOrder;
    }

    public int getInitialHeadPosition() {
        return initialHeadPosition;
    }

    public boolean isMovingUpward() {
        return isMovingUpward;
    }

    public int getFarthestServicedTrack() {
        return farthestServicedTrack;
    }
}