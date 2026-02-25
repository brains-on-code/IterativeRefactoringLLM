package com.thealgorithms.others;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author dimgrichr
 */
public class CRCAlgorithm {

    private int correctMessageCount;
    private int erroneousMessageCount;
    private int detectedErroneousMessageCount;
    private int undetectedErroneousMessageCount;

    private int messageLength;
    private double bitErrorRate;

    private boolean messageWasModified;

    private List<Integer> messageBits;
    private List<Integer> generatorPolynomialBits;

    private Random random;

    /**
     * The algorithm's main constructor. The most significant variables, used in
     * the algorithm, are set in their initial values.
     *
     * @param generatorPolynomial The binary number P, in a string form, which is used by the
     *                            CRC algorithm
     * @param messageLength       The size of every transmitted message
     * @param bitErrorRate        The Bit Error Rate
     */
    public CRCAlgorithm(String generatorPolynomial, int messageLength, double bitErrorRate) {
        this.messageWasModified = false;
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

    /**
     * @return erroneousMessageCount, the number of erroneous messages
     */
    public int getErroneousMessageCount() {
        return erroneousMessageCount;
    }

    /**
     * @return detectedErroneousMessageCount, the number of erroneous messages, which are caught
     * by the CRC algorithm
     */
    public int getDetectedErroneousMessageCount() {
        return detectedErroneousMessageCount;
    }

    /**
     * @return undetectedErroneousMessageCount, the number of erroneous messages, which are not
     * caught by the CRC algorithm
     */
    public int getUndetectedErroneousMessageCount() {
        return undetectedErroneousMessageCount;
    }

    /**
     * @return correctMessageCount, the number of the correct messages
     */
    public int getCorrectMessageCount() {
        return correctMessageCount;
    }

    /**
     * Resets some of the object's values, used on the main function, so that it
     * can be re-used, in order not to waste too much memory and time, by
     * creating new objects.
     */
    public void resetMessage() {
        messageWasModified = false;
        messageBits = new ArrayList<>();
    }

    /**
     * Random messages, consisted of 0's and 1's, are generated, so that they
     * can later be transmitted
     */
    public void generateRandomMessage() {
        for (int i = 0; i < messageLength; i++) {
            int bit = ThreadLocalRandom.current().nextInt(0, 2);
            messageBits.add(bit);
        }
    }

    /**
     * The most significant part of the CRC algorithm. The message is divided by
     * the generator polynomial, so the remainderBits list is created. If check == true,
     * the remainderBits is examined, in order to see if it contains any 1's.
     * If it does, the message is considered to be wrong by the receiver, so the
     * variable detectedErroneousMessageCount changes. If it does not, it is accepted, so one
     * of the variables correctMessageCount, undetectedErroneousMessageCount, changes. If check ==
     * false, the remainderBits is added at the end of the messageBits list.
     *
     * @param check the variable used to determine, if the message is going to
     *              be checked from the receiver if true, it is checked otherwise, it is not
     */
    public void divideMessageWithGeneratorPolynomial(boolean check) {
        List<Integer> currentDividendBits = new ArrayList<>();
        List<Integer> workingMessageBits = new ArrayList<>(messageBits);

        if (!check) {
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

        if (!check) {
            messageBits.addAll(remainderBits);
        } else {
            boolean remainderHasOne = remainderBits.contains(1);
            if (remainderHasOne && messageWasModified) {
                detectedErroneousMessageCount++;
            } else if (!remainderHasOne && messageWasModified) {
                undetectedErroneousMessageCount++;
            } else if (!messageWasModified) {
                correctMessageCount++;
            }
        }
    }

    /**
     * Once the message is transmitted, some of its elements are possible to
     * change from 1 to 0, or from 0 to 1, because of the Bit Error Rate (bitErrorRate).
     * For every element of the message, a random double number is created. If
     * that number is smaller than bitErrorRate, then the specific element changes. On
     * the other hand, if it's bigger than bitErrorRate, it does not. Based on these
     * changes, the boolean variable messageWasModified gets the value: true, or
     * false.
     */
    public void applyBitErrorsToMessage() {
        for (int i = 0; i < messageBits.size(); i++) {
            double randomValue = random.nextDouble();
            while (randomValue < 0.0 || randomValue > 1.0) {
                randomValue = random.nextDouble();
            }
            if (randomValue < bitErrorRate) {
                messageWasModified = true;
                int currentBit = messageBits.get(i);
                messageBits.set(i, currentBit == 1 ? 0 : 1);
            }
        }
        if (messageWasModified) {
            erroneousMessageCount++;
        }
    }
}