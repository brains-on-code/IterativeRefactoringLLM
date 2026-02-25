package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScanScheduling {
    private int currentHeadPosition;
    private int totalCylinders;
    private boolean isMovingTowardsHigherCylinders;

    public ScanScheduling(int initialHeadPosition, boolean isMovingTowardsHigherCylinders, int totalCylinders) {
        this.currentHeadPosition = initialHeadPosition;
        this.isMovingTowardsHigherCylinders = isMovingTowardsHigherCylinders;
        this.totalCylinders = totalCylinders;
    }

    public List<Integer> execute(List<Integer> pendingRequests) {
        if (pendingRequests.isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> scanOrder = new ArrayList<>();
        List<Integer> lowerCylinderRequests = new ArrayList<>();
        List<Integer> higherCylinderRequests = new ArrayList<>();

        for (int requestCylinder : pendingRequests) {
            if (requestCylinder < currentHeadPosition) {
                lowerCylinderRequests.add(requestCylinder);
            } else {
                higherCylinderRequests.add(requestCylinder);
            }
        }

        Collections.sort(lowerCylinderRequests);
        Collections.sort(higherCylinderRequests);

        if (isMovingTowardsHigherCylinders) {
            scanOrder.addAll(higherCylinderRequests);
            scanOrder.add(totalCylinders - 1);
            Collections.reverse(lowerCylinderRequests);
            scanOrder.addAll(lowerCylinderRequests);
        } else {
            Collections.reverse(lowerCylinderRequests);
            scanOrder.addAll(lowerCylinderRequests);
            scanOrder.add(0);
            scanOrder.addAll(higherCylinderRequests);
        }

        return scanOrder;
    }

    public int getCurrentHeadPosition() {
        return currentHeadPosition;
    }

    public boolean isMovingTowardsHigherCylinders() {
        return isMovingTowardsHigherCylinders;
    }
}