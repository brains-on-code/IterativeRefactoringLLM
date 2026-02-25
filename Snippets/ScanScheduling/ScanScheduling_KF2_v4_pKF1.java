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
        List<Integer> lowerCylinders = new ArrayList<>();
        List<Integer> higherCylinders = new ArrayList<>();

        for (int requestCylinder : requestQueue) {
            if (requestCylinder < headPosition) {
                lowerCylinders.add(requestCylinder);
            } else {
                higherCylinders.add(requestCylinder);
            }
        }

        Collections.sort(lowerCylinders);
        Collections.sort(higherCylinders);

        if (movingTowardsHigherCylinders) {
            scanSequence.addAll(higherCylinders);
            scanSequence.add(cylinderCount - 1);
            Collections.reverse(lowerCylinders);
            scanSequence.addAll(lowerCylinders);
        } else {
            Collections.reverse(lowerCylinders);
            scanSequence.addAll(lowerCylinders);
            scanSequence.add(0);
            scanSequence.addAll(higherCylinders);
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