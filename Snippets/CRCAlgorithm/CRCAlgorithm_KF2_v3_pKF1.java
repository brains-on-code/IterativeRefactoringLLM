package com.thealgorithms.others;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class CRCAlgorithm {

    private int validMessages;
    private int corruptedMessages;
    private int detectedCorruptedMessages;
    private int undetectedCorruptedMessages;

    private final int messageLength;
    private final double bitErrorProbability;

    private boolean messageCorrupted;

    private List<Integer> messageBits;
    private final List<Integer> generatorPolynomial;

    private final Random random;

    public CRCAlgorithm(String generatorPolynomialString, int messageLength, double bitErrorProbability) {
        this.messageCorrupted = false;
        this.messageBits = new ArrayList<>();
        this.messageLength = messageLength;
        this.generatorPolynomial = new ArrayList<>();
        for (int i = 0; i < generatorPolynomialString.length(); i++) {
            generatorPolynomial.add(Character.getNumericValue(generatorPolynomialString.charAt(i)));
        }
        this.random = new Random();
        this.validMessages = 0;
        this.corruptedMessages = 0;
        this.detectedCorruptedMessages = 0;
        this.undetectedCorruptedMessages = 0;
        this.bitErrorProbability = bitErrorProbability;
    }

    public int getCorruptedMessages() {
        return corruptedMessages;
    }

    public int getDetectedCorruptedMessages() {
        return detectedCorruptedMessages;
    }

    public int getUndetectedCorruptedMessages() {
        return undetectedCorruptedMessages;
    }

    public int getValidMessages() {
        return validMessages;
    }

    public void resetMessage() {
        messageCorrupted = false;
        messageBits = new ArrayList<>();
    }

    public void generateRandomMessage() {
        for (int i = 0; i < messageLength; i++) {
            int bit = ThreadLocalRandom.current().nextInt(0, 2);
            messageBits.add(bit);
        }
    }

    public void processMessageWithGenerator(boolean verificationPhase) {
        List<Integer> remainder = new ArrayList<>();
        List<Integer> workingBits = new ArrayList<>(messageBits);

        if (!verificationPhase) {
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
                    remainder.set(
                        i,
                        remainder.get(i).equals(generatorPolynomial.get(i)) ? 0 : 1
                    );
                }
                while (!remainder.isEmpty() && remainder.get(0) == 0) {
                    remainder.remove(0);
                }
            }
        }

        List<Integer> finalRemainder = new ArrayList<>(remainder);

        if (!verificationPhase) {
            messageBits.addAll(finalRemainder);
        } else {
            boolean hasError = finalRemainder.contains(1);
            if (hasError && messageCorrupted) {
                detectedCorruptedMessages++;
            } else if (!hasError && messageCorrupted) {
                undetectedCorruptedMessages++;
            } else if (!messageCorrupted) {
                validMessages++;
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
                messageCorrupted = true;
                messageBits.set(i, messageBits.get(i) == 1 ? 0 : 1);
            }
        }
        if (messageCorrupted) {
            corruptedMessages++;
        }
    }
}