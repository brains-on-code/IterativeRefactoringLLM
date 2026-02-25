package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Class1 {

    private int currentPosition;
    private final boolean scanDirectionUp;
    private final int diskSize;

    public Class1(int startPosition, boolean scanDirectionUp, int diskSize) {
        this.currentPosition = startPosition;
        this.scanDirectionUp = scanDirectionUp;
        this.diskSize = diskSize;
    }

    public List<Integer> schedule(List<Integer> requests) {
        List<Integer> higherRequests = new ArrayList<>();
        List<Integer> lowerRequests = new ArrayList<>();

        partitionRequests(requests, higherRequests, lowerRequests);

        Collections.sort(higherRequests);
        Collections.sort(lowerRequests);

        List<Integer> scheduledOrder = scanDirectionUp
            ? scheduleAscending(higherRequests, lowerRequests)
            : scheduleDescending(higherRequests, lowerRequests);

        updateCurrentPosition(scheduledOrder);
        return scheduledOrder;
    }

    private void partitionRequests(List<Integer> requests,
                                   List<Integer> higherRequests,
                                   List<Integer> lowerRequests) {
        for (int request : requests) {
            if (!isValidRequest(request) || request == currentPosition) {
                continue;
            }
            if (request > currentPosition) {
                higherRequests.add(request);
            } else {
                lowerRequests.add(request);
            }
        }
    }

    private boolean isValidRequest(int request) {
        return request >= 0 && request < diskSize;
    }

    private List<Integer> scheduleAscending(List<Integer> higherRequests,
                                            List<Integer> lowerRequests) {
        List<Integer> scheduledOrder =
            new ArrayList<>(higherRequests.size() + lowerRequests.size());
        scheduledOrder.addAll(higherRequests);
        scheduledOrder.addAll(lowerRequests);
        return scheduledOrder;
    }

    private List<Integer> scheduleDescending(List<Integer> higherRequests,
                                             List<Integer> lowerRequests) {
        List<Integer> scheduledOrder =
            new ArrayList<>(higherRequests.size() + lowerRequests.size());

        Collections.reverse(lowerRequests);
        Collections.reverse(higherRequests);

        scheduledOrder.addAll(lowerRequests);
        scheduledOrder.addAll(higherRequests);

        return scheduledOrder;
    }

    private void updateCurrentPosition(List<Integer> scheduledOrder) {
        if (!scheduledOrder.isEmpty()) {
            currentPosition = scheduledOrder.get(scheduledOrder.size() - 1);
        }
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public boolean isScanDirectionUp() {
        return scanDirectionUp;
    }
}