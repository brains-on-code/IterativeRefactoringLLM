package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a simple disk scheduling mechanism that processes requests
 * based on a current head position, direction, and disk size.
 */
public class DiskScheduler {

    /** Total number of tracks on the disk (disk size). */
    private final int diskSize;

    /** Current head position. */
    private int currentHeadPosition;

    /** Current direction of head movement: true = upward, false = downward. */
    private boolean movingUpward;

    /**
     * Creates a new disk scheduler.
     *
     * @param initialHeadPosition initial head position
     * @param initialDirection    initial direction (true = upward, false = downward)
     * @param diskSize            total number of tracks on the disk
     */
    public DiskScheduler(int initialHeadPosition, boolean initialDirection, int diskSize) {
        this.currentHeadPosition = initialHeadPosition;
        this.movingUpward = initialDirection;
        this.diskSize = diskSize;
    }

    /**
     * Schedules the given list of disk requests based on the current head
     * position and direction, returning the order in which they will be served.
     *
     * @param requests list of requested track positions
     * @return ordered list of track positions to be served
     */
    public List<Integer> scheduleRequests(List<Integer> requests) {
        List<Integer> scheduledOrder = new ArrayList<>();
        List<Integer> lowerRequests = new ArrayList<>();
        List<Integer> higherRequests = new ArrayList<>();

        partitionRequests(requests, lowerRequests, higherRequests);

        Collections.sort(lowerRequests);
        Collections.sort(higherRequests);

        if (movingUpward) {
            processUpwardMovement(scheduledOrder, lowerRequests, higherRequests);
        } else {
            processDownwardMovement(scheduledOrder, lowerRequests, higherRequests);
        }

        return scheduledOrder;
    }

    private void partitionRequests(
        List<Integer> requests,
        List<Integer> lowerRequests,
        List<Integer> higherRequests
    ) {
        for (int request : requests) {
            if (!isValidRequest(request)) {
                continue;
            }
            if (request < currentHeadPosition) {
                lowerRequests.add(request);
            } else {
                higherRequests.add(request);
            }
        }
    }

    private boolean isValidRequest(int request) {
        return request >= 0 && request < diskSize;
    }

    private void processUpwardMovement(
        List<Integer> scheduledOrder,
        List<Integer> lowerRequests,
        List<Integer> higherRequests
    ) {
        scheduledOrder.addAll(higherRequests);
        updateHeadToLast(higherRequests);

        movingUpward = false;

        Collections.reverse(lowerRequests);
        scheduledOrder.addAll(lowerRequests);
        updateHeadToMaxWithFirst(lowerRequests);
    }

    private void processDownwardMovement(
        List<Integer> scheduledOrder,
        List<Integer> lowerRequests,
        List<Integer> higherRequests
    ) {
        Collections.reverse(lowerRequests);
        scheduledOrder.addAll(lowerRequests);
        updateHeadToFirst(lowerRequests);

        movingUpward = true;

        scheduledOrder.addAll(higherRequests);
        updateHeadToMaxWithLast(higherRequests);
    }

    private void updateHeadToLast(List<Integer> requests) {
        if (!requests.isEmpty()) {
            currentHeadPosition = requests.get(requests.size() - 1);
        }
    }

    private void updateHeadToFirst(List<Integer> requests) {
        if (!requests.isEmpty()) {
            currentHeadPosition = requests.get(0);
        }
    }

    private void updateHeadToMaxWithFirst(List<Integer> requests) {
        if (!requests.isEmpty()) {
            currentHeadPosition = Math.max(currentHeadPosition, requests.get(0));
        }
    }

    private void updateHeadToMaxWithLast(List<Integer> requests) {
        if (!requests.isEmpty()) {
            currentHeadPosition = Math.max(
                currentHeadPosition,
                requests.get(requests.size() - 1)
            );
        }
    }

    public int getCurrentHeadPosition() {
        return currentHeadPosition;
    }

    public boolean isMovingUpward() {
        return movingUpward;
    }

    public int getDiskSize() {
        return diskSize;
    }
}