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
     * @param aCoeffs denominator coefficients (a), length must equal {@code order}
     * @param bCoeffs numerator coefficients (b), length must equal {@code order}
     * @throws IllegalArgumentException if lengths are incorrect or {@code aCoeffs[0] == 0.0}
     */
    public void setCoefficients(double[] aCoeffs, double[] bCoeffs) {
        validateCoefficients(aCoeffs, bCoeffs);

        // Copy provided coefficients into internal arrays
        for (int i = 0; i < order; i++) {
            this.denominatorCoefficients[i] = aCoeffs[i];
            this.numeratorCoefficients[i] = bCoeffs[i];
        }
    }

    /**
     * Processes a single input sample and returns the filtered output sample.
     *
     * @param inputSample the input sample
     * @return the filtered output sample
     */
    public double processSample(double inputSample) {
        double outputSample = 0.0;

        // Compute current output based on history and coefficients
        for (int i = 1; i <= order; i++) {
            outputSample += numeratorCoefficients[i] * inputHistory[i - 1]
                - denominatorCoefficients[i] * outputHistory[i - 1];
        }
        outputSample = (outputSample + numeratorCoefficients[0] * inputSample)
            / denominatorCoefficients[0];

        updateHistory(inputSample, outputSample);

        return outputSample;
    }

    private void validateCoefficients(double[] aCoeffs, double[] bCoeffs) {
        if (aCoeffs == null || bCoeffs == null) {
            throw new IllegalArgumentException("Coefficient arrays must not be null");
        }

        if (aCoeffs.length != order) {
            throw new IllegalArgumentException(
                "aCoeffs must be of size " + order + ", got " + aCoeffs.length
            );
        }

        if (bCoeffs.length != order) {
            throw new IllegalArgumentException(
                "bCoeffs must be of size " + order + ", got " + bCoeffs.length
            );
        }

        if (aCoeffs[0] == 0.0) {
            throw new IllegalArgumentException("aCoeffs[0] must not be zero");
        }
    }

    private void updateHistory(double inputSample, double outputSample) {
        for (int i = order - 1; i > 0; i--) {
            inputHistory[i] = inputHistory[i - 1];
            outputHistory[i] = outputHistory[i - 1];
        }

        inputHistory[0] = inputSample;
        outputHistory[0] = outputSample;
    }
}