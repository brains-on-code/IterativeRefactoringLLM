package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LookScheduling {
    private final int maxTrackNumber;
    private final int initialHeadPosition;
    private boolean isMovingUpwards;
    private int farthestVisitedTrack;

    public LookScheduling(int initialHeadPosition, boolean initialDirectionUpwards, int maxTrackNumber) {
        this.initialHeadPosition = initialHeadPosition;
        this.isMovingUpwards = initialDirectionUpwards;
        this.maxTrackNumber = maxTrackNumber;
    }

    public List<Integer> execute(List<Integer> trackRequests) {
        List<Integer> scheduledOrder = new ArrayList<>();
        List<Integer> lowerTrackRequests = new ArrayList<>();
        List<Integer> upperTrackRequests = new ArrayList<>();

        for (int track : trackRequests) {
            if (track >= 0 && track < maxTrackNumber) {
                if (track < initialHeadPosition) {
                    lowerTrackRequests.add(track);
                } else {
                    upperTrackRequests.add(track);
                }
            }
        }

        Collections.sort(lowerTrackRequests);
        Collections.sort(upperTrackRequests);

        if (isMovingUpwards) {
            scheduledOrder.addAll(upperTrackRequests);
            if (!upperTrackRequests.isEmpty()) {
                farthestVisitedTrack = upperTrackRequests.get(upperTrackRequests.size() - 1);
            }

            isMovingUpwards = false;
            Collections.reverse(lowerTrackRequests);
            scheduledOrder.addAll(lowerTrackRequests);
            if (!lowerTrackRequests.isEmpty()) {
                farthestVisitedTrack = Math.max(farthestVisitedTrack, lowerTrackRequests.get(0));
            }
        } else {
            Collections.reverse(lowerTrackRequests);
            scheduledOrder.addAll(lowerTrackRequests);
            if (!lowerTrackRequests.isEmpty()) {
                farthestVisitedTrack = lowerTrackRequests.get(0);
            }

            isMovingUpwards = true;
            scheduledOrder.addAll(upperTrackRequests);
            if (!upperTrackRequests.isEmpty()) {
                farthestVisitedTrack =
                    Math.max(farthestVisitedTrack, upperTrackRequests.get(upperTrackRequests.size() - 1));
            }
        }

        return scheduledOrder;
    }

    public int getInitialHeadPosition() {
        return initialHeadPosition;
    }

    public boolean isMovingUpwards() {
        return isMovingUpwards;
    }

    public int getFarthestVisitedTrack() {
        return farthestVisitedTrack;
    }
}