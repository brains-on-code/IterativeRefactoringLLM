package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CircularLookScheduling {
    private int headPosition;
    private boolean movingUpwards;
    private final int maxCylinder;

    public CircularLookScheduling(int initialHeadPosition, boolean initialDirectionUpwards, int maxCylinder) {
        this.headPosition = initialHeadPosition;
        this.movingUpwards = initialDirectionUpwards;
        this.maxCylinder = maxCylinder;
    }

    public List<Integer> execute(List<Integer> cylinderRequests) {
        List<Integer> scheduledCylinders = new ArrayList<>();

        List<Integer> upwardCylinders = new ArrayList<>();
        List<Integer> downwardCylinders = new ArrayList<>();

        for (int cylinder : cylinderRequests) {
            if (cylinder >= 0 && cylinder < maxCylinder) {
                if (cylinder > headPosition) {
                    upwardCylinders.add(cylinder);
                } else if (cylinder < headPosition) {
                    downwardCylinders.add(cylinder);
                }
            }
        }

        Collections.sort(upwardCylinders);
        Collections.sort(downwardCylinders);

        if (movingUpwards) {
            scheduledCylinders.addAll(upwardCylinders);
            scheduledCylinders.addAll(downwardCylinders);
        } else {
            Collections.reverse(downwardCylinders);
            scheduledCylinders.addAll(downwardCylinders);

            Collections.reverse(upwardCylinders);
            scheduledCylinders.addAll(upwardCylinders);
        }

        if (!scheduledCylinders.isEmpty()) {
            headPosition = scheduledCylinders.get(scheduledCylinders.size() - 1);
        }

        return scheduledCylinders;
    }

    public int getHeadPosition() {
        return headPosition;
    }

    public boolean isMovingUpwards() {
        return movingUpwards;
    }
}