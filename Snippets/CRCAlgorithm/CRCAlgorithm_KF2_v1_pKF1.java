package com.thealgorithms.others;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class CRCAlgorithm {

    private int correctMessages;
    private int incorrectMessages;
    private int detectedIncorrectMessages;
    private int undetectedIncorrectMessages;

    private final int messageSize;
    private final double bitErrorRate;

    private boolean messageModified;

    private List<Integer> messageBits;
    private final List<Integer> generatorPolynomial;

    private final Random randomGenerator;

    public CRCAlgorithm(String generatorPolynomialString, int messageSize, double bitErrorRate) {
        this.messageModified = false;
        this.messageBits = new ArrayList<>();
        this.messageSize = messageSize;
        this.generatorPolynomial = new ArrayList<>();
        for (int i = 0; i < generatorPolynomialString.length(); i++) {
            generatorPolynomial.add(Character.getNumericValue(generatorPolynomialString.charAt(i)));
        }
        this.randomGenerator = new Random();
        this.correctMessages = 0;
        this.incorrectMessages = 0;
        this.detectedIncorrectMessages = 0;
        this.undetectedIncorrectMessages = 0;
        this.bitErrorRate = bitErrorRate;
    }

    public int getIncorrectMessages() {
        return incorrectMessages;
    }

    public int getDetectedIncorrectMessages() {
        return detectedIncorrectMessages;
    }

    public int getUndetectedIncorrectMessages() {
        return undetectedIncorrectMessages;
    }

    public int getCorrectMessages() {
        return correctMessages;
    }

    public void resetMessage() {
        messageModified = false;
        messageBits = new ArrayList<>();
    }

    public void generateRandomMessage() {
        for (int i = 0; i < messageSize; i++) {
            int bit = ThreadLocalRandom.current().nextInt(0, 2);
            messageBits.add(bit);
        }
    }

    public void processMessageWithGenerator(boolean isCheckPhase) {
        List<Integer> remainder = new ArrayList<>();
        List<Integer> workingBits = new ArrayList<>(messageBits);

        if (!isCheckPhase) {
            for (int i = 0; i < generatorPolynomial.size() - 1; i++) {
                workingBits.add(0);
            }
        }

        while (!workingBits.isEmpty()) {
            while (remainder.size() < generatorPolynomial.size() && !workingBits.isEmpty()) {
                remainder.add(workingBits.get(0));
                workingBits.remove(0);
            }
            if (remainder.size() == generatorPolynomial.size()) {
                for (int i = 0; i < generatorPolynomial.size(); i++) {
                    remainder.set(i, remainder.get(i).equals(generatorPolynomial.get(i)) ? 0 : 1);
                }
                while (!remainder.isEmpty() && remainder.get(0) == 0) {
                    remainder.remove(0);
                }
            }
        }

        List<Integer> finalRemainder = new ArrayList<>(remainder);

        if (!isCheckPhase) {
            messageBits.addAll(finalRemainder);
        } else {
            boolean hasError = finalRemainder.contains(1);
            if (hasError && messageModified) {
                detectedIncorrectMessages++;
            } else if (!hasError && messageModified) {
                undetectedIncorrectMessages++;
            } else if (!messageModified) {
                correctMessages++;
            }
        }
    }

    public void introduceErrorsInMessage() {
        for (int i = 0; i < messageBits.size(); i++) {
            double randomValue = randomGenerator.nextDouble();
            while (randomValue < 0.0 || randomValue > 1.0) {
                randomValue = randomGenerator.nextDouble();
            }
            if (randomValue < bitErrorRate) {
                messageModified = true;
                messageBits.set(i, messageBits.get(i) == 1 ? 0 : 1);
            }
        }
        if (messageModified) {
            incorrectMessages++;
        }
    }
}