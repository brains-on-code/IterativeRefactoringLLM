package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of the SCAN (Elevator) disk scheduling algorithm.
 *
 * <p>The SCAN algorithm moves the disk head in one direction (up or down),
 * servicing all pending requests in that direction until it reaches the end
 * of the disk. It then reverses direction and services requests on the way
 * back.</p>
 *
 * <p>This approach provides a more uniform wait time compared to simple
 * algorithms like FCFS, while reducing overall head movement.</p>
 *
 * @see <a href="https://en.wikipedia.org/wiki/Elevator_algorithm">Elevator algorithm</a>
 */
public class ScanScheduling {

    private final int headPosition;
    private final int diskSize;
    private final boolean movingUp;

    /**
     * Creates a new SCAN scheduler.
     *
     * @param headPosition initial position of the disk head
     * @param movingUp     initial direction of head movement; {@code true} for
     *                     towards higher-numbered tracks, {@code false} for
     *                     towards lower-numbered tracks
     * @param diskSize     total number of tracks on the disk
     */
    public ScanScheduling(int headPosition, boolean movingUp, int diskSize) {
        this.headPosition = headPosition;
        this.movingUp = movingUp;
        this.diskSize = diskSize;
    }

    /**
     * Executes the SCAN scheduling algorithm on the given list of requests.
     *
     * @param requests list of requested track numbers
     * @return ordered list of track numbers representing the service sequence
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
            processWhenMovingUp(serviceOrder, lowerTracks, higherTracks);
        } else {
            processWhenMovingDown(serviceOrder, lowerTracks, higherTracks);
        }

        return serviceOrder;
    }

    /**
     * Splits the incoming requests into those below and above the current head position.
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
     * Handles the case when the head is initially moving towards higher-numbered tracks.
     */
    private void processWhenMovingUp(
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
     * Handles the case when the head is initially moving towards lower-numbered tracks.
     */
    private void processWhenMovingDown(
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