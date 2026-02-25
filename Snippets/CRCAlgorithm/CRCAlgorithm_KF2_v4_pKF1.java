package com.thealgorithms.others;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class CRCAlgorithm {

    private int validMessageCount;
    private int corruptedMessageCount;
    private int detectedCorruptedMessageCount;
    private int undetectedCorruptedMessageCount;

    private final int messageLength;
    private final double bitErrorProbability;

    private boolean isMessageCorrupted;

    private List<Integer> messageBits;
    private final List<Integer> generatorPolynomialBits;

    private final Random random;

    public CRCAlgorithm(String generatorPolynomialString, int messageLength, double bitErrorProbability) {
        this.isMessageCorrupted = false;
        this.messageBits = new ArrayList<>();
        this.messageLength = messageLength;
        this.generatorPolynomialBits = new ArrayList<>();
        for (int i = 0; i < generatorPolynomialString.length(); i++) {
            generatorPolynomialBits.add(Character.getNumericValue(generatorPolynomialString.charAt(i)));
        }
        this.random = new Random();
        this.validMessageCount = 0;
        this.corruptedMessageCount = 0;
        this.detectedCorruptedMessageCount = 0;
        this.undetectedCorruptedMessageCount = 0;
        this.bitErrorProbability = bitErrorProbability;
    }

    public int getCorruptedMessages() {
        return corruptedMessageCount;
    }

    public int getDetectedCorruptedMessages() {
        return detectedCorruptedMessageCount;
    }

    public int getUndetectedCorruptedMessages() {
        return undetectedCorruptedMessageCount;
    }

    public int getValidMessages() {
        return validMessageCount;
    }

    public void resetMessage() {
        isMessageCorrupted = false;
        messageBits = new ArrayList<>();
    }

    public void generateRandomMessage() {
        for (int i = 0; i < messageLength; i++) {
            int bit = ThreadLocalRandom.current().nextInt(0, 2);
            messageBits.add(bit);
        }
    }

    public void processMessageWithGenerator(boolean isVerificationPhase) {
        List<Integer> currentRemainder = new ArrayList<>();
        List<Integer> workingBits = new ArrayList<>(messageBits);

        if (!isVerificationPhase) {
            for (int i = 0; i < generatorPolynomialBits.size() - 1; i++) {
                workingBits.add(0);
            }
        }

        while (!workingBits.isEmpty()) {
            while (currentRemainder.size() < generatorPolynomialBits.size() && !workingBits.isEmpty()) {
                currentRemainder.add(workingBits.get(0));
                workingBits.remove(0);
            }
            if (currentRemainder.size() == generatorPolynomialBits.size()) {
                for (int i = 0; i < generatorPolynomialBits.size(); i++) {
                    currentRemainder.set(
                        i,
                        currentRemainder.get(i).equals(generatorPolynomialBits.get(i)) ? 0 : 1
                    );
                }
                while (!currentRemainder.isEmpty() && currentRemainder.get(0) == 0) {
                    currentRemainder.remove(0);
                }
            }
        }

        List<Integer> finalRemainder = new ArrayList<>(currentRemainder);

        if (!isVerificationPhase) {
            messageBits.addAll(finalRemainder);
        } else {
            boolean hasError = finalRemainder.contains(1);
            if (hasError && isMessageCorrupted) {
                detectedCorruptedMessageCount++;
            } else if (!hasError && isMessageCorrupted) {
                undetectedCorruptedMessageCount++;
            } else if (!isMessageCorrupted) {
                validMessageCount++;
            }
        }
    }

    public void introduceErrorsInMessage() {
        for (int i = 0; i < messageBits.size(); i++) {
            double randomValue = random.nextDouble();
            while (randomValue < 0.0 || randomValue > 1.0) {
                randomValue = random.nextDouble();
            }
            if (randomValue < bitErrorProbability) {
                isMessageCorrupted = true;
                messageBits.set(i, messageBits.get(i) == 1 ? 0 : 1);
            }
        }
        if (isMessageCorrupted) {
            corruptedMessageCount++;
        }
    }
}