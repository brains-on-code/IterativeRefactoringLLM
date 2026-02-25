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

    private boolean messageCorrupted;

    private List<Integer> messageBits;
    private final List<Integer> generatorPolynomialBits;

    private final Random random;

    public CRCAlgorithm(String generatorPolynomial, int messageLength, double bitErrorProbability) {
        this.messageCorrupted = false;
        this.messageBits = new ArrayList<>();
        this.messageLength = messageLength;
        this.generatorPolynomialBits = new ArrayList<>();
        for (int i = 0; i < generatorPolynomial.length(); i++) {
            generatorPolynomialBits.add(Character.getNumericValue(generatorPolynomial.charAt(i)));
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
        List<Integer> remainderBits = new ArrayList<>();
        List<Integer> workingBits = new ArrayList<>(messageBits);

        if (!verificationPhase) {
            for (int i = 0; i < generatorPolynomialBits.size() - 1; i++) {
                workingBits.add(0);
            }
        }

        while (!workingBits.isEmpty()) {
            while (remainderBits.size() < generatorPolynomialBits.size() && !workingBits.isEmpty()) {
                remainderBits.add(workingBits.get(0));
                workingBits.remove(0);
            }
            if (remainderBits.size() == generatorPolynomialBits.size()) {
                for (int i = 0; i < generatorPolynomialBits.size(); i++) {
                    remainderBits.set(
                        i,
                        remainderBits.get(i).equals(generatorPolynomialBits.get(i)) ? 0 : 1
                    );
                }
                while (!remainderBits.isEmpty() && remainderBits.get(0) == 0) {
                    remainderBits.remove(0);
                }
            }
        }

        List<Integer> finalRemainderBits = new ArrayList<>(remainderBits);

        if (!verificationPhase) {
            messageBits.addAll(finalRemainderBits);
        } else {
            boolean hasError = finalRemainderBits.contains(1);
            if (hasError && messageCorrupted) {
                detectedCorruptedMessageCount++;
            } else if (!hasError && messageCorrupted) {
                undetectedCorruptedMessageCount++;
            } else if (!messageCorrupted) {
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
                messageCorrupted = true;
                messageBits.set(i, messageBits.get(i) == 1 ? 0 : 1);
            }
        }
        if (messageCorrupted) {
            corruptedMessageCount++;
        }
    }
}