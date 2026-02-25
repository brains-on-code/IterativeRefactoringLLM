package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ScanScheduling {
    private int headPosition;
    private int diskSize;
    private boolean movingUp;

    public ScanScheduling(int headPosition, boolean movingUp, int diskSize) {
        this.headPosition = headPosition;
        this.movingUp = movingUp;
        this.diskSize = diskSize;
    }

    public List<Integer> execute(List<Integer> requests) {
        if (requests.isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> result = new ArrayList<>();
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();

        for (int request : requests) {
            if (request < headPosition) {
                left.add(request);
            } else {
                right.add(request);
            }
        }

        Collections.sort(left);
        Collections.sort(right);

        if (movingUp) {
            result.addAll(right);
            result.add(diskSize - 1);
            Collections.reverse(left);
            result.addAll(left);
        } else {
            Collections.reverse(left);
            result.addAll(left);
            result.add(0);
            result.addAll(right);
        }

        return result;
    }

    public int getHeadPosition() {
        return headPosition;
    }

    public boolean isMovingUp() {
        return movingUp;
    }
}
