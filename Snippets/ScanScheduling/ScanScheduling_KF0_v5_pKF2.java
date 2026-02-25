package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * SCAN (Elevator) disk scheduling algorithm.
 *
 * <p>The head moves in one direction, servicing all requests in that direction
 * until it reaches the end of the disk, then reverses direction.</p>
 */
public class ScanScheduling {

    private final int headPosition;
    private final int diskSize;
    private final boolean movingUp;

    /**
     * Creates a new SCAN scheduler.
     *
     * @param headPosition initial head position
     * @param movingUp     initial direction; {@code true} for higher tracks
     * @param diskSize     total number of tracks
     */
    public ScanScheduling(int headPosition, boolean movingUp, int diskSize) {
        this.headPosition = headPosition;
        this.movingUp = movingUp;
        this.diskSize = diskSize;
    }

    /**
     * Executes the SCAN algorithm on the given requests.
     *
     * @param requests requested track numbers
     * @return service order of track numbers
     */
    public List<Integer> execute(List<Integer> requests) {
        if (requests.isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> serviceOrder = new ArrayList<>();
        List<Integer> lowerTracks = new ArrayList<>();
        List<Integer> higherTracks = new ArrayList<>();

        splitRequestsByHeadPosition(requests, lowerTracks, higherTracks);

        Collections.sort(lowerTracks);
        Collections.sort(higherTracks);

        if (movingUp) {
            scheduleWhenMovingUp(serviceOrder, lowerTracks, higherTracks);
        } else {
            scheduleWhenMovingDown(serviceOrder, lowerTracks, higherTracks);
        }

        return serviceOrder;
    }

    /**
     * Splits requests into those below and at/above the head position.
     */
    private void splitRequestsByHeadPosition(
        List<Integer> requests,
        List<Integer> lowerTracks,
        List<Integer> higherTracks
    ) {
        for (int request : requests) {
            if (request < headPosition) {
                lowerTracks.add(request);
            } else {
                higherTracks.add(request);
            }
        }
    }

    /**
     * Schedules requests when the head is initially moving towards higher tracks.
     *
     * <p>Order:
     * <ol>
     *   <li>All higher (and equal) tracks in ascending order</li>
     *   <li>Move to the last track on the disk</li>
     *   <li>All lower tracks in descending order</li>
     * </ol>
     * </p>
     */
    private void scheduleWhenMovingUp(
        List<Integer> serviceOrder,
        List<Integer> lowerTracks,
        List<Integer> higherTracks
    ) {
        serviceOrder.addAll(higherTracks);
        serviceOrder.add(diskSize - 1);
        Collections.reverse(lowerTracks);
        serviceOrder.addAll(lowerTracks);
    }

    /**
     * Schedules requests when the head is initially moving towards lower tracks.
     *
     * <p>Order:
     * <ol>
     *   <li>All lower tracks in descending order</li>
     *   <li>Move to track 0</li>
     *   <li>All higher (and equal) tracks in ascending order</li>
     * </ol>
     * </p>
     */
    private void scheduleWhenMovingDown(
        List<Integer> serviceOrder,
        List<Integer> lowerTracks,
        List<Integer> higherTracks
    ) {
        Collections.reverse(lowerTracks);
        serviceOrder.addAll(lowerTracks);
        serviceOrder.add(0);
        serviceOrder.addAll(higherTracks);
    }

    public int getHeadPosition() {
        return headPosition;
    }

    public boolean isMovingUp() {
        return movingUp;
    }
}