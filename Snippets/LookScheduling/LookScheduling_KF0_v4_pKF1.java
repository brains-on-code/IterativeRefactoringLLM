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
        List<Integer> lowerTrackRequests = new ArrayList<>();
        List<Integer> upperTrackRequests = new ArrayList<>();

        for (int trackRequest : trackRequests) {
            if (trackRequest >= 0 && trackRequest < maxTrackNumber) {
                if (trackRequest < initialHeadPosition) {
                    lowerTrackRequests.add(trackRequest);
                } else {
                    upperTrackRequests.add(trackRequest);
                }
            }
        }

        Collections.sort(lowerTrackRequests);
        Collections.sort(upperTrackRequests);

        if (movingUpward) {
            serviceOrder.addAll(upperTrackRequests);
            if (!upperTrackRequests.isEmpty()) {
                farthestServicedTrack = upperTrackRequests.get(upperTrackRequests.size() - 1);
            }

            movingUpward = false;
            Collections.reverse(lowerTrackRequests);
            serviceOrder.addAll(lowerTrackRequests);
            if (!lowerTrackRequests.isEmpty()) {
                farthestServicedTrack = Math.max(farthestServicedTrack, lowerTrackRequests.get(0));
            }
        } else {
            Collections.reverse(lowerTrackRequests);
            serviceOrder.addAll(lowerTrackRequests);
            if (!lowerTrackRequests.isEmpty()) {
                farthestServicedTrack = lowerTrackRequests.get(0);
            }

            movingUpward = true;
            serviceOrder.addAll(upperTrackRequests);
            if (!upperTrackRequests.isEmpty()) {
                farthestServicedTrack =
                    Math.max(farthestServicedTrack, upperTrackRequests.get(upperTrackRequests.size() - 1));
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