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
    private final int cylinderCount;

    public ScanDiskScheduler(int initialHeadPosition, boolean initialDirectionUpwards, int cylinderCount) {
        this.headPosition = initialHeadPosition;
        this.scanningUpwards = initialDirectionUpwards;
        this.cylinderCount = cylinderCount;
    }

    public List<Integer> scheduleRequests(List<Integer> requestQueue) {
        List<Integer> scheduledRequests = new ArrayList<>();

        List<Integer> requestsAboveHead = new ArrayList<>();
        List<Integer> requestsBelowHead = new ArrayList<>();

        for (int requestCylinder : requestQueue) {
            if (requestCylinder >= 0 && requestCylinder < cylinderCount) {
                if (requestCylinder > headPosition) {
                    requestsAboveHead.add(requestCylinder);
                } else if (requestCylinder < headPosition) {
                    requestsBelowHead.add(requestCylinder);
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