package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * SCAN (Elevator) Disk Scheduling Algorithm implementation.
 */
public class ScanDiskScheduler {
    private final int diskSize;
    private final int initialHeadPosition;
    private boolean movingTowardsHigherCylinders;
    private int lastServicedCylinder;

    public ScanDiskScheduler(int initialHeadPosition, boolean movingTowardsHigherCylinders, int diskSize) {
        this.initialHeadPosition = initialHeadPosition;
        this.movingTowardsHigherCylinders = movingTowardsHigherCylinders;
        this.diskSize = diskSize;
    }

    /**
     * Schedules disk I/O requests using the SCAN algorithm.
     *
     * @param cylinderRequests list of requested cylinder positions
     * @return ordered list of cylinder positions representing the service sequence
     */
    public List<Integer> scheduleRequests(List<Integer> cylinderRequests) {
        List<Integer> scheduledCylinders = new ArrayList<>();
        List<Integer> lowerCylinderRequests = new ArrayList<>();
        List<Integer> higherCylinderRequests = new ArrayList<>();

        for (int cylinder : cylinderRequests) {
            if (isValidCylinder(cylinder)) {
                if (cylinder < initialHeadPosition) {
                    lowerCylinderRequests.add(cylinder);
                } else {
                    higherCylinderRequests.add(cylinder);
                }
            }
        }

        Collections.sort(lowerCylinderRequests);
        Collections.sort(higherCylinderRequests);

        if (movingTowardsHigherCylinders) {
            processWhenMovingTowardsHigherCylinders(
                scheduledCylinders,
                lowerCylinderRequests,
                higherCylinderRequests
            );
        } else {
            processWhenMovingTowardsLowerCylinders(
                scheduledCylinders,
                lowerCylinderRequests,
                higherCylinderRequests
            );
        }

        return scheduledCylinders;
    }

    private boolean isValidCylinder(int cylinder) {
        return cylinder >= 0 && cylinder < diskSize;
    }

    private void processWhenMovingTowardsHigherCylinders(
        List<Integer> scheduledCylinders,
        List<Integer> lowerCylinderRequests,
        List<Integer> higherCylinderRequests
    ) {
        scheduledCylinders.addAll(higherCylinderRequests);
        if (!higherCylinderRequests.isEmpty()) {
            lastServicedCylinder = higherCylinderRequests.get(higherCylinderRequests.size() - 1);
        }

        movingTowardsHigherCylinders = false;
        Collections.reverse(lowerCylinderRequests);
        scheduledCylinders.addAll(lowerCylinderRequests);
        if (!lowerCylinderRequests.isEmpty()) {
            lastServicedCylinder = Math.max(lastServicedCylinder, lowerCylinderRequests.get(0));
        }
    }

    private void processWhenMovingTowardsLowerCylinders(
        List<Integer> scheduledCylinders,
        List<Integer> lowerCylinderRequests,
        List<Integer> higherCylinderRequests
    ) {
        Collections.reverse(lowerCylinderRequests);
        scheduledCylinders.addAll(lowerCylinderRequests);
        if (!lowerCylinderRequests.isEmpty()) {
            lastServicedCylinder = lowerCylinderRequests.get(0);
        }

        movingTowardsHigherCylinders = true;
        scheduledCylinders.addAll(higherCylinderRequests);
        if (!higherCylinderRequests.isEmpty()) {
            lastServicedCylinder =
                Math.max(lastServicedCylinder, higherCylinderRequests.get(higherCylinderRequests.size() - 1));
        }
    }

    public int getInitialHeadPosition() {
        return initialHeadPosition;
    }

    public boolean isMovingTowardsHigherCylinders() {
        return movingTowardsHigherCylinders;
    }

    public int getLastServicedCylinder() {
        return lastServicedCylinder;
    }
}