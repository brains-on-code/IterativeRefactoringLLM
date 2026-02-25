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
    private boolean movingUpward;
    private int farthestServicedTrack;

    public LookScheduling(int initialHeadPosition, boolean initialDirectionUpward, int maxTrackNumber) {
        this.initialHeadPosition = initialHeadPosition;
        this.movingUpward = initialDirectionUpward;
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
        List<Integer> lowerTracks = new ArrayList<>();
        List<Integer> upperTracks = new ArrayList<>();

        // Split requests into two lists based on their position relative to current head position
        for (int track : trackRequests) {
            if (track >= 0 && track < maxTrackNumber) {
                if (track < initialHeadPosition) {
                    lowerTracks.add(track);
                } else {
                    upperTracks.add(track);
                }
            }
        }

        // Sort the requests
        Collections.sort(lowerTracks);
        Collections.sort(upperTracks);

        // Process the requests depending on the initial moving direction
        if (movingUpward) {
            // Process requests in the upward direction
            serviceOrder.addAll(upperTracks);
            if (!upperTracks.isEmpty()) {
                farthestServicedTrack = upperTracks.get(upperTracks.size() - 1);
            }

            // Reverse the direction and process downward
            movingUpward = false;
            Collections.reverse(lowerTracks);
            serviceOrder.addAll(lowerTracks);
            if (!lowerTracks.isEmpty()) {
                farthestServicedTrack = Math.max(farthestServicedTrack, lowerTracks.get(0));
            }
        } else {
            // Process requests in the downward direction
            Collections.reverse(lowerTracks);
            serviceOrder.addAll(lowerTracks);
            if (!lowerTracks.isEmpty()) {
                farthestServicedTrack = lowerTracks.get(0);
            }

            // Reverse the direction and process upward
            movingUpward = true;
            serviceOrder.addAll(upperTracks);
            if (!upperTracks.isEmpty()) {
                farthestServicedTrack =
                    Math.max(farthestServicedTrack, upperTracks.get(upperTracks.size() - 1));
            }
        }

        return serviceOrder;
    }

    public int getInitialHeadPosition() {
        return initialHeadPosition;
    }

    public boolean isMovingUpward() {
        return movingUpward;
    }

    public int getFarthestServicedTrack() {
        return farthestServicedTrack;
    }
}