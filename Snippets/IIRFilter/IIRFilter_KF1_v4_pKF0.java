package com.thealgorithms.audiofilters;

/**
 * Simple IIR filter implementation with configurable order and coefficients.
 *
 * The filter operates on input samples in the range [-1, 1].
 */
public class IirFilter {

    /** Filter order (number of poles/zeros). */
    private final int order;

    /** Denominator coefficients (a-coefficients), length = order + 1. */
    private final double[] denominatorCoefficients;

    /** Numerator coefficients (b-coefficients), length = order + 1. */
    private final double[] numeratorCoefficients;

    /** Input history buffer, length = order. */
    private final double[] inputHistory;

    /** Output history buffer, length = order. */
    private final double[] outputHistory;

    /**
     * Creates a new IIR filter of the given order.
     *
     * @param order filter order, must be >= 1
     * @throws IllegalArgumentException if {@code order < 1}
     */
    public IirFilter(int order) {
        if (order < 1) {
            throw new IllegalArgumentException("order must be greater than zero");
        }

        this.order = order;
        this.denominatorCoefficients = new double[order + 1];
        this.numeratorCoefficients = new double[order + 1];

        // Initialize leading coefficients
        this.denominatorCoefficients[0] = 1.0;
        this.numeratorCoefficients[0] = 1.0;

        this.inputHistory = new double[order];
        this.outputHistory = new double[order];
    }

    /**
     * Sets the filter coefficients.
     *
     * @param aCoeffs denominator coefficients (a), length must equal {@code order + 1}
     * @param bCoeffs numerator coefficients (b), length must equal {@code order + 1}
     * @throws IllegalArgumentException if lengths are incorrect or {@code aCoeffs[0] == 0.0}
     */
    public void setCoefficients(double[] aCoeffs, double[] bCoeffs) {
        validateCoefficients(aCoeffs, bCoeffs);
        int length = order + 1;
        System.arraycopy(aCoeffs, 0, this.denominatorCoefficients, 0, length);
        System.arraycopy(bCoeffs, 0, this.numeratorCoefficients, 0, length);
    }

    /**
     * Processes a single input sample and returns the filtered output sample.
     *
     * @param inputSample the input sample
     * @return the filtered output sample
     */
    public double processSample(double inputSample) {
        double outputSample = numeratorCoefficients[0] * inputSample;

        for (int i = 1; i <= order; i++) {
            int historyIndex = i - 1;
            outputSample += numeratorCoefficients[i] * inputHistory[historyIndex]
                - denominatorCoefficients[i] * outputHistory[historyIndex];
        }

        outputSample /= denominatorCoefficients[0];
        updateHistory(inputSample, outputSample);
        return outputSample;
    }

    private void validateCoefficients(double[] aCoeffs, double[] bCoeffs) {
        if (aCoeffs == null || bCoeffs == null) {
            throw new IllegalArgumentException("Coefficient arrays must not be null");
        }

        int expectedLength = order + 1;

        if (aCoeffs.length != expectedLength) {
            throw new IllegalArgumentException(
                "aCoeffs must be of size " + expectedLength + ", got " + aCoeffs.length
            );
        }

        if (bCoeffs.length != expectedLength) {
            throw new IllegalArgumentException(
                "bCoeffs must be of size " + expectedLength + ", got " + bCoeffs.length
            );
        }

        if (aCoeffs[0] == 0.0) {
            throw new IllegalArgumentException("aCoeffs[0] must not be zero");
        }
    }

    private void updateHistory(double inputSample, double outputSample) {
        for (int i = order - 1; i > 0; i--) {
            int previousIndex = i - 1;
            inputHistory[i] = inputHistory[previousIndex];
            outputHistory[i] = outputHistory[previousIndex];
        }

        inputHistory[0] = inputSample;
        outputHistory[0] = outputSample;
    }
}