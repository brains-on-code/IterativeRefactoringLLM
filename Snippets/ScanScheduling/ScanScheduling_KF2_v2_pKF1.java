package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScanScheduling {
    private int headPosition;
    private int cylinderCount;
    private boolean movingTowardsHigherCylinders;

    public ScanScheduling(int initialHeadPosition, boolean movingTowardsHigherCylinders, int cylinderCount) {
        this.headPosition = initialHeadPosition;
        this.movingTowardsHigherCylinders = movingTowardsHigherCylinders;
        this.cylinderCount = cylinderCount;
    }

    public List<Integer> execute(List<Integer> requestQueue) {
        if (requestQueue.isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> scanSequence = new ArrayList<>();
        List<Integer> lowerCylinderRequests = new ArrayList<>();
        List<Integer> higherCylinderRequests = new ArrayList<>();

        for (int requestCylinder : requestQueue) {
            if (requestCylinder < headPosition) {
                lowerCylinderRequests.add(requestCylinder);
            } else {
                higherCylinderRequests.add(requestCylinder);
            }
        }

        Collections.sort(lowerCylinderRequests);
        Collections.sort(higherCylinderRequests);

        if (movingTowardsHigherCylinders) {
            scanSequence.addAll(higherCylinderRequests);
            scanSequence.add(cylinderCount - 1);
            Collections.reverse(lowerCylinderRequests);
            scanSequence.addAll(lowerCylinderRequests);
        } else {
            Collections.reverse(lowerCylinderRequests);
            scanSequence.addAll(lowerCylinderRequests);
            scanSequence.add(0);
            scanSequence.addAll(higherCylinderRequests);
        }

        return scanSequence;
    }

    public int getHeadPosition() {
        return headPosition;
    }

    public boolean isMovingTowardsHigherCylinders() {
        return movingTowardsHigherCylinders;
    }
}