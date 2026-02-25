package com.thealgorithms.others;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class CRCAlgorithm {

    private int correctMessageCount;
    private int erroneousMessageCount;
    private int detectedErroneousMessageCount;
    private int undetectedErroneousMessageCount;

    private int messageLength;
    private double bitErrorRate;

    private boolean messageModified;

    private List<Integer> messageBits;
    private List<Integer> generatorPolynomialBits;

    private Random random;

    public CRCAlgorithm(String generatorPolynomial, int messageLength, double bitErrorRate) {
        this.messageModified = false;
        this.messageBits = new ArrayList<>();
        this.messageLength = messageLength;
        this.generatorPolynomialBits = new ArrayList<>();
        for (int i = 0; i < generatorPolynomial.length(); i++) {
            generatorPolynomialBits.add(Character.getNumericValue(generatorPolynomial.charAt(i)));
        }
        this.random = new Random();
        this.correctMessageCount = 0;
        this.erroneousMessageCount = 0;
        this.detectedErroneousMessageCount = 0;
        this.undetectedErroneousMessageCount = 0;
        this.bitErrorRate = bitErrorRate;
    }

    public int getErroneousMessageCount() {
        return erroneousMessageCount;
    }

    public int getDetectedErroneousMessageCount() {
        return detectedErroneousMessageCount;
    }

    public int getUndetectedErroneousMessageCount() {
        return undetectedErroneousMessageCount;
    }

    public int getCorrectMessageCount() {
        return correctMessageCount;
    }

    public void resetMessage() {
        messageModified = false;
        messageBits = new ArrayList<>();
    }

    public void generateRandomMessage() {
        for (int i = 0; i < messageLength; i++) {
            int bit = ThreadLocalRandom.current().nextInt(0, 2);
            messageBits.add(bit);
        }
    }

    public void divideMessageWithGeneratorPolynomial(boolean verifyRemainder) {
        List<Integer> currentDividendBits = new ArrayList<>();
        List<Integer> workingMessageBits = new ArrayList<>(messageBits);

        if (!verifyRemainder) {
            for (int i = 0; i < generatorPolynomialBits.size() - 1; i++) {
                workingMessageBits.add(0);
            }
        }

        while (!workingMessageBits.isEmpty()) {
            while (currentDividendBits.size() < generatorPolynomialBits.size() && !workingMessageBits.isEmpty()) {
                currentDividendBits.add(workingMessageBits.get(0));
                workingMessageBits.remove(0);
            }
            if (currentDividendBits.size() == generatorPolynomialBits.size()) {
                for (int i = 0; i < generatorPolynomialBits.size(); i++) {
                    if (currentDividendBits.get(i).equals(generatorPolynomialBits.get(i))) {
                        currentDividendBits.set(i, 0);
                    } else {
                        currentDividendBits.set(i, 1);
                    }
                }
                while (!currentDividendBits.isEmpty() && currentDividendBits.get(0) != 1) {
                    currentDividendBits.remove(0);
                }
            }
        }

        List<Integer> remainderBits = new ArrayList<>(currentDividendBits);

        if (!verifyRemainder) {
            messageBits.addAll(remainderBits);
        } else {
            boolean remainderContainsOne = remainderBits.contains(1);
            if (remainderContainsOne && messageModified) {
                detectedErroneousMessageCount++;
            } else if (!remainderContainsOne && messageModified) {
                undetectedErroneousMessageCount++;
            } else if (!messageModified) {
                correctMessageCount++;
            }
        }
    }

    public void applyBitErrorsToMessage() {
        for (int i = 0; i < messageBits.size(); i++) {
            double randomValue = random.nextDouble();
            while (randomValue < 0.0 || randomValue > 1.0) {
                randomValue = random.nextDouble();
            }
            if (randomValue < bitErrorRate) {
                messageModified = true;
                int currentBit = messageBits.get(i);
                messageBits.set(i, currentBit == 1 ? 0 : 1);
            }
        }
        if (messageModified) {
            erroneousMessageCount++;
        }
    }
}