package com.thealgorithms.others;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class CRCAlgorithm {

    private int correctMessages;
    private int wrongMessages;
    private int wrongMessagesCaught;
    private int wrongMessagesNotCaught;

    private final int messageSize;
    private final double bitErrorRate;

    private boolean messageChanged;

    private List<Integer> message;
    private final List<Integer> generatorPolynomial;

    private final Random randomGenerator;

    public CRCAlgorithm(String polynomial, int messageSize, double bitErrorRate) {
        this.messageChanged = false;
        this.message = new ArrayList<>();
        this.messageSize = messageSize;
        this.generatorPolynomial = parsePolynomial(polynomial);
        this.randomGenerator = new Random();
        this.correctMessages = 0;
        this.wrongMessages = 0;
        this.wrongMessagesCaught = 0;
        this.wrongMessagesNotCaught = 0;
        this.bitErrorRate = bitErrorRate;
    }

    public int getWrongMessages() {
        return wrongMessages;
    }

    public int getWrongMessagesCaught() {
        return wrongMessagesCaught;
    }

    public int getWrongMessagesNotCaught() {
        return wrongMessagesNotCaught;
    }

    public int getCorrectMessages() {
        return correctMessages;
    }

    public void resetMessage() {
        messageChanged = false;
        message = new ArrayList<>();
    }

    public void generateRandomMessage() {
        for (int i = 0; i < messageSize; i++) {
            int bit = ThreadLocalRandom.current().nextInt(0, 2);
            message.add(bit);
        }
    }

    /**
     * Performs CRC division.
     *
     * @param check if false, computes and appends CRC bits to the message;
     *              if true, checks the received message and updates statistics.
     */
    public void divideMessageWithP(boolean check) {
        List<Integer> remainder = new ArrayList<>();
        List<Integer> workingMessage = new ArrayList<>(message);

        if (!check) {
            appendZeroPadding(workingMessage);
        }

        performPolynomialDivision(workingMessage, remainder);

        if (!check) {
            appendRemainderToMessage(remainder);
        } else {
            updateStatisticsBasedOnRemainder(remainder);
        }
    }

    /**
     * Randomly flips bits in the message according to the bit error rate.
     * Updates error counters if any bit was changed.
     */
    public void changeMessage() {
        for (int i = 0; i < message.size(); i++) {
            double randomValue = randomGenerator.nextDouble();
            if (randomValue < bitErrorRate) {
                messageChanged = true;
                message.set(i, flipBit(message.get(i)));
            }
        }
        if (messageChanged) {
            wrongMessages++;
        }
    }

    private List<Integer> parsePolynomial(String polynomial) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < polynomial.length(); i++) {
            result.add(Character.getNumericValue(polynomial.charAt(i)));
        }
        return result;
    }

    private void appendZeroPadding(List<Integer> workingMessage) {
        int paddingLength = generatorPolynomial.size() - 1;
        for (int i = 0; i < paddingLength; i++) {
            workingMessage.add(0);
        }
    }

    private void performPolynomialDivision(List<Integer> workingMessage, List<Integer> remainder) {
        while (!workingMessage.isEmpty()) {
            fillRemainder(workingMessage, remainder);
            if (remainder.size() == generatorPolynomial.size()) {
                xorWithGenerator(remainder);
                removeLeadingZeros(remainder);
            }
        }
    }

    private void fillRemainder(List<Integer> workingMessage, List<Integer> remainder) {
        while (remainder.size() < generatorPolynomial.size() && !workingMessage.isEmpty()) {
            remainder.add(workingMessage.remove(0));
        }
    }

    private void xorWithGenerator(List<Integer> remainder) {
        for (int i = 0; i < generatorPolynomial.size(); i++) {
            int bit = remainder.get(i).equals(generatorPolynomial.get(i)) ? 0 : 1;
            remainder.set(i, bit);
        }
    }

    private void removeLeadingZeros(List<Integer> remainder) {
        while (!remainder.isEmpty() && remainder.get(0) == 0) {
            remainder.remove(0);
        }
    }

    private void appendRemainderToMessage(List<Integer> remainder) {
        message.addAll(new ArrayList<>(remainder));
    }

    private void updateStatisticsBasedOnRemainder(List<Integer> remainder) {
        boolean hasError = remainder.contains(1);
        if (hasError && messageChanged) {
            wrongMessagesCaught++;
        } else if (!hasError && messageChanged) {
            wrongMessagesNotCaught++;
        } else if (!messageChanged) {
            correctMessages++;
        }
    }

    private int flipBit(int bit) {
        return bit == 1 ? 0 : 1;
    }
}