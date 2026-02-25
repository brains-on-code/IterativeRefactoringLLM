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

    /**
     * Creates a CRCAlgorithm instance.
     *
     * @param polynomial    binary generator polynomial P as a string
     * @param messageSize   size of each transmitted message (in bits)
     * @param bitErrorRate  bit error rate used when corrupting messages
     */
    public CRCAlgorithm(String polynomial, int messageSize, double bitErrorRate) {
        this.messageChanged = false;
        this.message = new ArrayList<>();
        this.messageSize = messageSize;
        this.generatorPolynomial = new ArrayList<>();
        for (int i = 0; i < polynomial.length(); i++) {
            generatorPolynomial.add(Character.getNumericValue(polynomial.charAt(i)));
        }
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

    /**
     * Clears the current message and resets the change flag.
     */
    public void resetMessage() {
        messageChanged = false;
        message = new ArrayList<>();
    }

    /**
     * Generates a new random message of length {@code messageSize}.
     * Each bit is either 0 or 1 with equal probability.
     */
    public void generateRandomMessage() {
        for (int i = 0; i < messageSize; i++) {
            int bit = ThreadLocalRandom.current().nextInt(0, 2);
            message.add(bit);
        }
    }

    /**
     * Performs CRC division of the current message by the generator polynomial.
     *
     * <p>If {@code check} is {@code false}, this method appends the computed
     * remainder (CRC bits) to the message (encoding step).</p>
     *
     * <p>If {@code check} is {@code true}, this method checks the received
     * message (which already contains CRC bits) and updates the internal
     * counters for correct and incorrect messages.</p>
     *
     * @param check {@code false} to encode (append CRC), {@code true} to verify
     */
    public void divideMessageWithP(boolean check) {
        List<Integer> remainder = new ArrayList<>();
        List<Integer> workingMessage = new ArrayList<>(message);

        // For encoding, append zeros equal to degree of generator polynomial
        if (!check) {
            for (int i = 0; i < generatorPolynomial.size() - 1; i++) {
                workingMessage.add(0);
            }
        }

        // Polynomial long division in GF(2)
        while (!workingMessage.isEmpty()) {
            // Fill remainder up to generator size
            while (remainder.size() < generatorPolynomial.size() && !workingMessage.isEmpty()) {
                remainder.add(workingMessage.remove(0));
            }

            // XOR with generator polynomial when sizes match
            if (remainder.size() == generatorPolynomial.size()) {
                for (int i = 0; i < generatorPolynomial.size(); i++) {
                    int bit = remainder.get(i).equals(generatorPolynomial.get(i)) ? 0 : 1;
                    remainder.set(i, bit);
                }
                // Remove leading zeros
                while (!remainder.isEmpty() && remainder.get(0) == 0) {
                    remainder.remove(0);
                }
            }
        }

        List<Integer> dividedMessage = new ArrayList<>(remainder);

        if (!check) {
            // Encoding: append CRC bits to original message
            message.addAll(dividedMessage);
        } else {
            // Checking: analyze remainder and update statistics
            boolean hasError = dividedMessage.contains(1);
            if (hasError && messageChanged) {
                wrongMessagesCaught++;
            } else if (!hasError && messageChanged) {
                wrongMessagesNotCaught++;
            } else if (!messageChanged) {
                correctMessages++;
            }
        }
    }

    /**
     * Randomly flips bits in the current message according to {@code bitErrorRate}.
     * Updates error statistics if any bit is changed.
     */
    public void changeMessage() {
        for (int i = 0; i < message.size(); i++) {
            double randomValue = randomGenerator.nextDouble();
            if (randomValue < bitErrorRate) {
                messageChanged = true;
                message.set(i, message.get(i) == 1 ? 0 : 1);
            }
        }
        if (messageChanged) {
            wrongMessages++;
        }
    }
}