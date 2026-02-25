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

    private boolean mutationOccurredInCurrentRun;

    private List<Integer> bitSequence;
    private final List<Integer> pattern;

    private final Random random;

    public Class1(String patternString, int sequenceLength, double mutationProbability) {
        this.sequenceLength = sequenceLength;
        this.mutationProbability = mutationProbability;

        this.mutationOccurredInCurrentRun = false;
        this.bitSequence = new ArrayList<>();
        this.pattern = parsePattern(patternString);

        this.random = new Random();
        this.falsePositiveCount = 0;
        this.mutationCount = 0;
        this.truePositiveCount = 0;
        this.trueNegativeCount = 0;
    }

    private List<Integer> parsePattern(String patternString) {
        List<Integer> parsedPattern = new ArrayList<>(patternString.length());
        for (int i = 0; i < patternString.length(); i++) {
            parsedPattern.add(Character.getNumericValue(patternString.charAt(i)));
        }
        return parsedPattern;
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

    public void resetSequence() {
        mutationOccurredInCurrentRun = false;
        bitSequence = new ArrayList<>();
    }

    public void generateRandomSequence() {
        bitSequence.clear();
        for (int i = 0; i < sequenceLength; i++) {
            int bit = ThreadLocalRandom.current().nextInt(0, 2);
            bitSequence.add(bit);
        }
    }

    public void processPattern(boolean evaluateOnly) {
        List<Integer> window = new ArrayList<>();
        List<Integer> workingSequence = new ArrayList<>(bitSequence);

        if (!evaluateOnly) {
            padWorkingSequence(workingSequence);
        }

        slideAndCompare(window, workingSequence);

        List<Integer> result = new ArrayList<>(window);

        if (!evaluateOnly) {
            bitSequence.addAll(result);
        } else {
            evaluateResult(result);
        }
    }

    private void padWorkingSequence(List<Integer> workingSequence) {
        int paddingSize = pattern.size() - 1;
        for (int i = 0; i < paddingSize; i++) {
            workingSequence.add(0);
        }
    }

    private void slideAndCompare(List<Integer> window, List<Integer> workingSequence) {
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
            int xorResult = window.get(i).equals(pattern.get(i)) ? 0 : 1;
            window.set(i, xorResult);
        }
    }

    private void trimLeadingZeros(List<Integer> window) {
        while (!window.isEmpty() && window.get(0) == 0) {
            window.remove(0);
        }
    }

    private void evaluateResult(List<Integer> result) {
        boolean hasOnes = result.contains(1);

        if (mutationOccurredInCurrentRun) {
            if (hasOnes) {
                truePositiveCount++;
            } else {
                trueNegativeCount++;
            }
        } else {
            falsePositiveCount++;
        }
    }

    public void applyMutations() {
        for (int i = 0; i < bitSequence.size(); i++) {
            double value = random.nextDouble();

            if (value < mutationProbability) {
                mutationOccurredInCurrentRun = true;
                int currentBit = bitSequence.get(i);
                bitSequence.set(i, currentBit == 1 ? 0 : 1);
            }
        }

        if (mutationOccurredInCurrentRun) {
            mutationCount++;
        }
    }
}