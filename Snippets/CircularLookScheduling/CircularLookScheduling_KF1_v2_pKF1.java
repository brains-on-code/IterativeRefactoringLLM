package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Disk scheduling using a simple SCAN-like strategy.
 */
public class ScanDiskScheduler {
    private int headPosition;
    private boolean scanningUpwards;
    private final int diskSize;

    public ScanDiskScheduler(int initialHeadPosition, boolean scanningUpwards, int diskSize) {
        this.headPosition = initialHeadPosition;
        this.scanningUpwards = scanningUpwards;
        this.diskSize = diskSize;
    }

    public List<Integer> scheduleRequests(List<Integer> requests) {
        List<Integer> scheduledRequests = new ArrayList<>();

        List<Integer> requestsAboveHead = new ArrayList<>();
        List<Integer> requestsBelowHead = new ArrayList<>();

        for (int request : requests) {
            if (request >= 0 && request < diskSize) {
                if (request > headPosition) {
                    requestsAboveHead.add(request);
                } else if (request < headPosition) {
                    requestsBelowHead.add(request);
                }
            }
        }

        Collections.sort(requestsAboveHead);
        Collections.sort(requestsBelowHead);

        if (scanningUpwards) {
            scheduledRequests.addAll(requestsAboveHead);
            scheduledRequests.addAll(requestsBelowHead);
        } else {
            Collections.reverse(requestsBelowHead);
            scheduledRequests.addAll(requestsBelowHead);

            Collections.reverse(requestsAboveHead);
            scheduledRequests.addAll(requestsAboveHead);
        }

        if (!scheduledRequests.isEmpty()) {
            headPosition = scheduledRequests.get(scheduledRequests.size() - 1);
        }

        return scheduledRequests;
    }

    public int getHeadPosition() {
        return headPosition;
    }

    public boolean isScanningUpwards() {
        return scanningUpwards;
    }
}