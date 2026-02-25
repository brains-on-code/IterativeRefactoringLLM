package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScanScheduling {

    private final int headPosition;
    private final int diskSize;
    private final boolean movingUp;

    public ScanScheduling(int headPosition, boolean movingUp, int diskSize) {
        this.headPosition = headPosition;
        this.movingUp = movingUp;
        this.diskSize = diskSize;
    }

    public List<Integer> execute(List<Integer> requests) {
        if (requests == null || requests.isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> leftRequests = new ArrayList<>();
        List<Integer> rightRequests = new ArrayList<>();

        partitionRequests(requests, leftRequests, rightRequests);
        sortRequests(leftRequests, rightRequests);

        return movingUp
            ? buildScheduleMovingUp(leftRequests, rightRequests)
            : buildScheduleMovingDown(leftRequests, rightRequests);
    }

    private void partitionRequests(
        List<Integer> requests,
        List<Integer> leftRequests,
        List<Integer> rightRequests
    ) {
        for (int request : requests) {
            if (request < headPosition) {
                leftRequests.add(request);
            } else {
                rightRequests.add(request);
            }
        }
    }

    private void sortRequests(List<Integer> leftRequests, List<Integer> rightRequests) {
        Collections.sort(leftRequests);
        Collections.sort(rightRequests);
    }

    private List<Integer> buildScheduleMovingUp(
        List<Integer> leftRequests,
        List<Integer> rightRequests
    ) {
        int capacity = rightRequests.size() + leftRequests.size() + 1;
        List<Integer> schedule = new ArrayList<>(capacity);

        schedule.addAll(rightRequests);
        schedule.add(diskSize - 1);

        Collections.reverse(leftRequests);
        schedule.addAll(leftRequests);

        return schedule;
    }

    private List<Integer> buildScheduleMovingDown(
        List<Integer> leftRequests,
        List<Integer> rightRequests
    ) {
        int capacity = leftRequests.size() + rightRequests.size() + 1;
        List<Integer> schedule = new ArrayList<>(capacity);

        Collections.reverse(leftRequests);
        schedule.addAll(leftRequests);
        schedule.add(0);
        schedule.addAll(rightRequests);

        return schedule;
    }

    public int getHeadPosition() {
        return headPosition;
    }

    public boolean isMovingUp() {
        return movingUp;
    }

    public int getDiskSize() {
        return diskSize;
    }
}