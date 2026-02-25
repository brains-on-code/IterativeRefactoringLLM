package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LookScheduling {
    private final int maxTrackNumber;
    private final int initialHeadPosition;
    private boolean movingUpwards;
    private int farthestVisitedTrack;

    public LookScheduling(int initialHeadPosition, boolean initialDirectionUpwards, int maxTrackNumber) {
        this.initialHeadPosition = initialHeadPosition;
        this.movingUpwards = initialDirectionUpwards;
        this.maxTrackNumber = maxTrackNumber;
    }

    public List<Integer> execute(List<Integer> trackRequests) {
        List<Integer> scheduledTracks = new ArrayList<>();
        List<Integer> lowerTracks = new ArrayList<>();
        List<Integer> upperTracks = new ArrayList<>();

        for (int track : trackRequests) {
            if (track >= 0 && track < maxTrackNumber) {
                if (track < initialHeadPosition) {
                    lowerTracks.add(track);
                } else {
                    upperTracks.add(track);
                }
            }
        }

        Collections.sort(lowerTracks);
        Collections.sort(upperTracks);

        if (movingUpwards) {
            scheduledTracks.addAll(upperTracks);
            if (!upperTracks.isEmpty()) {
                farthestVisitedTrack = upperTracks.get(upperTracks.size() - 1);
            }

            movingUpwards = false;
            Collections.reverse(lowerTracks);
            scheduledTracks.addAll(lowerTracks);
            if (!lowerTracks.isEmpty()) {
                farthestVisitedTrack = Math.max(farthestVisitedTrack, lowerTracks.get(0));
            }
        } else {
            Collections.reverse(lowerTracks);
            scheduledTracks.addAll(lowerTracks);
            if (!lowerTracks.isEmpty()) {
                farthestVisitedTrack = lowerTracks.get(0);
            }

            movingUpwards = true;
            scheduledTracks.addAll(upperTracks);
            if (!upperTracks.isEmpty()) {
                farthestVisitedTrack =
                    Math.max(farthestVisitedTrack, upperTracks.get(upperTracks.size() - 1));
            }
        }

        return scheduledTracks;
    }

    public int getInitialHeadPosition() {
        return initialHeadPosition;
    }

    public boolean isMovingUpwards() {
        return movingUpwards;
    }

    public int getFarthestVisitedTrack() {
        return farthestVisitedTrack;
    }
}