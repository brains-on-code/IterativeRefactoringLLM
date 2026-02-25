package com.thealgorithms.others;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * CRC Algorithm implementation.
 *
 * @author dimgrichr
 */
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

    /**
     * Main constructor. Initializes the CRC algorithm with the given generator
     * polynomial, message size, and bit error rate.
     *
     * @param polynomial Binary number P, in string form, used by the CRC algorithm
     * @param size       Size of every transmitted message
     * @param ber        Bit Error Rate
     */
    public CRCAlgorithm(String polynomial, int size, double ber) {
        this.messageChanged = false;
        this.message = new ArrayList<>();
        this.messageSize = size;
        this.generatorPolynomial = parsePolynomial(polynomial);

        this.randomGenerator = new Random();
        this.correctMessages = 0;
        this.wrongMessages = 0;
        this.wrongMessagesCaught = 0;
        this.wrongMessagesNotCaught = 0;
        this.bitErrorRate = ber;
    }

    private List<Integer> parsePolynomial(String polynomial) {
        List<Integer> result = new ArrayList<>(polynomial.length());
        for (int i = 0; i < polynomial.length(); i++) {
            result.add(Character.getNumericValue(polynomial.charAt(i)));
        }
        return result;
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

    /**
     * Resets the message-related state so that the instance can be reused.
     */
    public void reset() {
        messageChanged = false;
        message = new ArrayList<>();
    }

    /**
     * Generates a random binary message of length {@code messageSize}.
     */
    public void generateRandomMessage() {
        message.clear();
        for (int i = 0; i < messageSize; i++) {
            int bit = ThreadLocalRandom.current().nextInt(0, 2);
            message.add(bit);
        }
    }

    /**
     * Core CRC operation. Divides the message by the generator polynomial.
     *
     * <p>If {@code check == false}, the remainder is appended to the message
     * (encoding step). If {@code check == true}, the remainder is examined:
     * <ul>
     *   <li>If it contains any 1s and the message was changed, the error is caught.</li>
     *   <li>If it contains no 1s and the message was changed, the error is not caught.</li>
     *   <li>If the message was not changed, it is counted as correct.</li>
     * </ul>
     *
     * @param check if true, the remainder is checked (receiver side);
     *              if false, the remainder is appended (sender side)
     */
    public void divideMessageWithP(boolean check) {
        List<Integer> workingMessage = new ArrayList<>(message);

        if (!check) {
            appendZerosForEncoding(workingMessage);
        }

        List<Integer> remainder = computeRemainder(workingMessage);

        if (!check) {
            message.addAll(remainder);
        } else {
            updateStatisticsAfterCheck(remainder);
        }
    }

    private void appendZerosForEncoding(List<Integer> workingMessage) {
        int zerosToAppend = generatorPolynomial.size() - 1;
        for (int i = 0; i < zerosToAppend; i++) {
            workingMessage.add(0);
        }
    }

    private List<Integer> computeRemainder(List<Integer> workingMessage) {
        List<Integer> remainder = new ArrayList<>();

        while (!workingMessage.isEmpty()) {
            fillRemainderFromWorkingMessage(remainder, workingMessage);

            if (remainder.size() == generatorPolynomial.size()) {
                xorWithGeneratorPolynomial(remainder);
                removeLeadingZeros(remainder);
            }
        }

        return new ArrayList<>(remainder);
    }

    private void fillRemainderFromWorkingMessage(List<Integer> remainder, List<Integer> workingMessage) {
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

    private void updateStatisticsAfterCheck(List<Integer> remainder) {
        boolean hasError = remainder.contains(1);

        if (!messageChanged) {
            correctMessages++;
            return;
        }

        if (hasError) {
            wrongMessagesCaught++;
        } else {
            wrongMessagesNotCaught++;
        }
    }

    /**
     * Simulates transmission errors on the message based on the bit error rate.
     * Each bit is flipped with probability {@code bitErrorRate}.
     */
    public void applyTransmissionErrors() {
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