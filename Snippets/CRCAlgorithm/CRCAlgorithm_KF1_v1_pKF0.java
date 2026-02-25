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
    private int sequenceLength;

    private double mutationProbability;

    private boolean mutationOccurredInCurrentRun;

    private List<Integer> bitSequence;
    private List<Integer> pattern;

    private final Random random;

    public Class1(String patternString, int sequenceLength, double mutationProbability) {
        this.mutationOccurredInCurrentRun = false;
        this.bitSequence = new ArrayList<>();
        this.sequenceLength = sequenceLength;
        this.pattern = new ArrayList<>();

        for (int i = 0; i < patternString.length(); i++) {
            pattern.add(Character.getNumericValue(patternString.charAt(i)));
        }

        this.random = new Random();
        this.falsePositiveCount = 0;
        this.mutationCount = 0;
        this.truePositiveCount = 0;
        this.trueNegativeCount = 0;
        this.mutationProbability = mutationProbability;
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
        for (int i = 0; i < sequenceLength; i++) {
            int bit = ThreadLocalRandom.current().nextInt(0, 2);
            bitSequence.add(bit);
        }
    }

    public void processPattern(boolean evaluateOnly) {
        List<Integer> window = new ArrayList<>();
        List<Integer> workingSequence = new ArrayList<>(bitSequence);

        if (!evaluateOnly) {
            for (int i = 0; i < pattern.size() - 1; i++) {
                workingSequence.add(0);
            }
        }

        while (!workingSequence.isEmpty()) {
            while (window.size() < pattern.size() && !workingSequence.isEmpty()) {
                window.add(workingSequence.get(0));
                workingSequence.remove(0);
            }

            if (window.size() == pattern.size()) {
                for (int i = 0; i < pattern.size(); i++) {
                    if (window.get(i).equals(pattern.get(i))) {
                        window.set(i, 0);
                    } else {
                        window.set(i, 1);
                    }
                }

                while (!window.isEmpty() && window.get(0) != 1) {
                    window.remove(0);
                }
            }
        }

        List<Integer> result = new ArrayList<>(window);

        if (!evaluateOnly) {
            bitSequence.addAll(result);
        } else {
            if (result.contains(1) && mutationOccurredInCurrentRun) {
                truePositiveCount++;
            } else if (!result.contains(1) && mutationOccurredInCurrentRun) {
                trueNegativeCount++;
            } else if (!mutationOccurredInCurrentRun) {
                falsePositiveCount++;
            }
        }
    }

    public void applyMutations() {
        for (int i = 0; i < bitSequence.size(); i++) {
            double value = random.nextDouble();
            while (value < 0.0 || value > 1.0) {
                value = random.nextDouble();
            }

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