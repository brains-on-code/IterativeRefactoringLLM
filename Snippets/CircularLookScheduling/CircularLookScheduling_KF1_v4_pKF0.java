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

        for (int request : requests) {
            if (!isValidRequest(request)) {
                continue;
            }
            if (request > currentPosition) {
                higherRequests.add(request);
            } else if (request < currentPosition) {
                lowerRequests.add(request);
            }
        }

        Collections.sort(higherRequests);
        Collections.sort(lowerRequests);

        List<Integer> scheduledOrder = scanDirectionUp
            ? scheduleAscending(higherRequests, lowerRequests)
            : scheduleDescending(higherRequests, lowerRequests);

        updateCurrentPosition(scheduledOrder);
        return scheduledOrder;
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

        List<Integer> reversedLower = new ArrayList<>(lowerRequests);
        Collections.reverse(reversedLower);
        scheduledOrder.addAll(reversedLower);

        List<Integer> reversedHigher = new ArrayList<>(higherRequests);
        Collections.reverse(reversedHigher);
        scheduledOrder.addAll(reversedHigher);

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