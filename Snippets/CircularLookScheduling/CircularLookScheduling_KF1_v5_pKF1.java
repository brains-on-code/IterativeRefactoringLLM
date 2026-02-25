package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Disk scheduling using a simple SCAN-like strategy.
 */
public class ScanDiskScheduler {
    private int currentHeadPosition;
    private boolean isMovingUpwards;
    private final int totalCylinders;

    public ScanDiskScheduler(int initialHeadPosition, boolean initialDirectionUpwards, int totalCylinders) {
        this.currentHeadPosition = initialHeadPosition;
        this.isMovingUpwards = initialDirectionUpwards;
        this.totalCylinders = totalCylinders;
    }

    public List<Integer> scheduleRequests(List<Integer> requestQueue) {
        List<Integer> scheduledOrder = new ArrayList<>();

        List<Integer> requestsAboveHead = new ArrayList<>();
        List<Integer> requestsBelowHead = new ArrayList<>();

        for (int requestCylinder : requestQueue) {
            if (requestCylinder >= 0 && requestCylinder < totalCylinders) {
                if (requestCylinder > currentHeadPosition) {
                    requestsAboveHead.add(requestCylinder);
                } else if (requestCylinder < currentHeadPosition) {
                    requestsBelowHead.add(requestCylinder);
                }
            }
        }

        Collections.sort(requestsAboveHead);
        Collections.sort(requestsBelowHead);

        if (isMovingUpwards) {
            scheduledOrder.addAll(requestsAboveHead);
            scheduledOrder.addAll(requestsBelowHead);
        } else {
            Collections.reverse(requestsBelowHead);
            scheduledOrder.addAll(requestsBelowHead);

            Collections.reverse(requestsAboveHead);
            scheduledOrder.addAll(requestsAboveHead);
        }

        if (!scheduledOrder.isEmpty()) {
            currentHeadPosition = scheduledOrder.get(scheduledOrder.size() - 1);
        }

        return scheduledOrder;
    }

    public int getHeadPosition() {
        return currentHeadPosition;
    }

    public boolean isScanningUpwards() {
        return isMovingUpwards;
    }
}