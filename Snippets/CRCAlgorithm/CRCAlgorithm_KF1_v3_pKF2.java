package com.thealgorithms.others;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Class1 {

    private int falsePositiveCount;
    private int mutationCount;
    private int truePositiveCount;
    private int trueNegativeCount;

    private final int sequenceLength;
    private final double mutationProbability;

    private boolean mutatedInCurrentRun;

    private List<Integer> bitSequence;
    private final List<Integer> pattern;

    private final Random random;

    public Class1(String patternString, int sequenceLength, double mutationProbability) {
        this.sequenceLength = sequenceLength;
        this.mutationProbability = mutationProbability;

        this.mutatedInCurrentRun = false;
        this.bitSequence = new ArrayList<>();
        this.pattern = new ArrayList<>();

        for (char c : patternString.toCharArray()) {
            pattern.add(Character.getNumericValue(c));
        }

        this.random = new Random();
        this.falsePositiveCount = 0;
        this.mutationCount = 0;
        this.truePositiveCount = 0;
        this.trueNegativeCount = 0;
    }

    public int getMutationCount() {
        return mutationCount;
    }

    public int getTruePositiveCount() {
        return truePositiveCount;
    }

    public int getTrueNegativeCount() {
        return trueNegativeCount;
    }

    public int getFalsePositiveCount() {
        return falsePositiveCount;
    }

    public void reset() {
        mutatedInCurrentRun = false;
        bitSequence = new ArrayList<>();
    }

    public void generateRandomSequence() {
        bitSequence.clear();
        for (int i = 0; i < sequenceLength; i++) {
            bitSequence.add(ThreadLocalRandom.current().nextInt(0, 2));
        }
    }

    public void processSequence(boolean updateSequence) {
        List<Integer> window = new ArrayList<>();
        List<Integer> workingSequence = new ArrayList<>(bitSequence);

        if (!updateSequence) {
            padWorkingSequence(workingSequence);
        }

        slideAndComparePattern(window, workingSequence);

        List<Integer> result = new ArrayList<>(window);

        if (!updateSequence) {
            bitSequence.addAll(result);
            return;
        }

        updateStatistics(result);
    }

    private void padWorkingSequence(List<Integer> workingSequence) {
        for (int i = 0; i < pattern.size() - 1; i++) {
            workingSequence.add(0);
        }
    }

    private void slideAndComparePattern(List<Integer> window, List<Integer> workingSequence) {
        while (!workingSequence.isEmpty()) {
            fillWindow(window, workingSequence);

            if (window.size() == pattern.size()) {
                xorWindowWithPattern(window);
                trimLeadingZeros(window);
            }
        }
    }

    private void fillWindow(List<Integer> window, List<Integer> workingSequence) {
        while (window.size() < pattern.size() && !workingSequence.isEmpty()) {
            window.add(workingSequence.remove(0));
        }
    }

    private void xorWindowWithPattern(List<Integer> window) {
        for (int i = 0; i < pattern.size(); i++) {
            window.set(i, window.get(i).equals(pattern.get(i)) ? 0 : 1);
        }
    }

    private void trimLeadingZeros(List<Integer> window) {
        while (!window.isEmpty() && window.get(0) != 1) {
            window.remove(0);
        }
    }

    private void updateStatistics(List<Integer> result) {
        boolean containsOne = result.contains(1);

        if (containsOne && mutatedInCurrentRun) {
            truePositiveCount++;
        } else if (!containsOne && mutatedInCurrentRun) {
            trueNegativeCount++;
        } else if (!mutatedInCurrentRun) {
            falsePositiveCount++;
        }
    }

    public void mutateSequence() {
        mutatedInCurrentRun = false;

        for (int i = 0; i < bitSequence.size(); i++) {
            if (random.nextDouble() < mutationProbability) {
                mutatedInCurrentRun = true;
                int bit = bitSequence.get(i);
                bitSequence.set(i, bit == 1 ? 0 : 1);
            }
        }

        if (mutatedInCurrentRun) {
            mutationCount++;
        }
    }
}