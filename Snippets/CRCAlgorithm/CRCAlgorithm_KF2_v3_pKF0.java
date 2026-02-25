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

    public CRCAlgorithm(String polynomial, int size, double ber) {
        this.messageSize = size;
        this.bitErrorRate = ber;

        this.messageChanged = false;
        this.message = new ArrayList<>();
        this.generatorPolynomial = new ArrayList<>();

        for (int i = 0; i < polynomial.length(); i++) {
            generatorPolynomial.add(Character.getNumericValue(polynomial.charAt(i)));
        }

        this.randomGenerator = new Random();

        this.correctMessages = 0;
        this.wrongMessages = 0;
        this.wrongMessagesCaught = 0;
        this.wrongMessagesNotCaught = 0;
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
        message.clear();
        for (int i = 0; i < messageSize; i++) {
            int bit = ThreadLocalRandom.current().nextInt(0, 2);
            message.add(bit);
        }
    }

    public void divideMessageWithPolynomial(boolean check) {
        List<Integer> remainder = new ArrayList<>();
        List<Integer> workingMessage = new ArrayList<>(message);

        if (!check) {
            appendZerosForCrc(workingMessage);
        }

        computeRemainder(workingMessage, remainder);

        if (!check) {
            appendRemainderToMessage(remainder);
        } else {
            updateStatisticsBasedOnRemainder(remainder);
        }
    }

    private void appendZerosForCrc(List<Integer> workingMessage) {
        int zerosToAppend = generatorPolynomial.size() - 1;
        for (int i = 0; i < zerosToAppend; i++) {
            workingMessage.add(0);
        }
    }

    private void computeRemainder(List<Integer> workingMessage, List<Integer> remainder) {
        while (!workingMessage.isEmpty()) {
            fillRemainder(workingMessage, remainder);
            if (remainder.size() == generatorPolynomial.size()) {
                xorWithGeneratorPolynomial(remainder);
                removeLeadingZeros(remainder);
            }
        }
    }

    private void fillRemainder(List<Integer> workingMessage, List<Integer> remainder) {
        while (remainder.size() < generatorPolynomial.size() && !workingMessage.isEmpty()) {
            remainder.add(workingMessage.remove(0));
        }
    }

    private void xorWithGeneratorPolynomial(List<Integer> remainder) {
        for (int i = 0; i < generatorPolynomial.size(); i++) {
            int bit = remainder.get(i) ^ generatorPolynomial.get(i);
            remainder.set(i, bit);
        }
    }

    private void removeLeadingZeros(List<Integer> remainder) {
        while (!remainder.isEmpty() && remainder.get(0) == 0) {
            remainder.remove(0);
        }
    }

    private void appendRemainderToMessage(List<Integer> remainder) {
        message.addAll(remainder);
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

    public void introduceErrors() {
        messageChanged = false;

        for (int i = 0; i < message.size(); i++) {
            if (randomGenerator.nextDouble() < bitErrorRate) {
                flipBitAtIndex(i);
                messageChanged = true;
            }
        }

        if (messageChanged) {
            wrongMessages++;
        }
    }

    private void flipBitAtIndex(int index) {
        int flippedBit = message.get(index) == 1 ? 0 : 1;
        message.set(index, flippedBit);
    }
}