package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScanScheduling {

    private final int headPosition;
    private final int diskSize;
    private final boolean movingUp;

    public ScanScheduling(int headPosition, boolean movingUp, int diskSize) {
        this.headPosition = headPosition;
        this.movingUp = movingUp;
        this.diskSize = diskSize;
    }

    public List<Integer> execute(List<Integer> requests) {
        if (requests == null || requests.isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> orderedSequence = new ArrayList<>();
        List<Integer> leftOfHead = new ArrayList<>();
        List<Integer> rightOfHead = new ArrayList<>();

        for (int request : requests) {
            if (request < headPosition) {
                leftOfHead.add(request);
            } else {
                rightOfHead.add(request);
            }
        }

        Collections.sort(leftOfHead);
        Collections.sort(rightOfHead);

        if (movingUp) {
            orderedSequence.addAll(rightOfHead);
            orderedSequence.add(diskSize - 1);
            Collections.reverse(leftOfHead);
            orderedSequence.addAll(leftOfHead);
        } else {
            Collections.reverse(leftOfHead);
            orderedSequence.addAll(leftOfHead);
            orderedSequence.add(0);
            orderedSequence.addAll(rightOfHead);
        }

        return orderedSequence;
    }

    public int getHeadPosition() {
        return headPosition;
    }

    public boolean isMovingUp() {
        return movingUp;
    }
}