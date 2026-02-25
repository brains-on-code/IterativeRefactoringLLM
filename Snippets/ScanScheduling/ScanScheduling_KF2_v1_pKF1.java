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

    public List<Integer> execute(List<Integer> requestQueue) {
        if (requestQueue.isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> scanOrder = new ArrayList<>();
        List<Integer> lowerCylinders = new ArrayList<>();
        List<Integer> higherCylinders = new ArrayList<>();

        for (int requestCylinder : requestQueue) {
            if (requestCylinder < currentHeadPosition) {
                lowerCylinders.add(requestCylinder);
            } else {
                higherCylinders.add(requestCylinder);
            }
        }

        Collections.sort(lowerCylinders);
        Collections.sort(higherCylinders);

        if (isMovingTowardsHigherCylinders) {
            scanOrder.addAll(higherCylinders);
            scanOrder.add(totalCylinders - 1);
            Collections.reverse(lowerCylinders);
            scanOrder.addAll(lowerCylinders);
        } else {
            Collections.reverse(lowerCylinders);
            scanOrder.addAll(lowerCylinders);
            scanOrder.add(0);
            scanOrder.addAll(higherCylinders);
        }

        return scanOrder;
    }

    public int getHeadPosition() {
        return currentHeadPosition;
    }

    public boolean isMovingUp() {
        return isMovingTowardsHigherCylinders;
    }
}