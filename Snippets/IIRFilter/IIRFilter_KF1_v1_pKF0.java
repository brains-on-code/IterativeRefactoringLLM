package com.thealgorithms.audiofilters;

/**
 * Simple IIR filter implementation with configurable order and coefficients.
 *
 * The filter operates on input samples in the range [-1, 1].
 */
public class Class1 {

    /** Filter order (number of poles/zeros). */
    private final int order;

    /** Denominator coefficients (a-coefficients), length = order + 1. */
    private final double[] aCoeffs;

    /** Numerator coefficients (b-coefficients), length = order + 1. */
    private final double[] bCoeffs;

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
    public Class1(int order) throws IllegalArgumentException {
        if (order < 1) {
            throw new IllegalArgumentException("order must be greater than zero");
        }

        this.order = order;
        this.aCoeffs = new double[order + 1];
        this.bCoeffs = new double[order + 1];

        // Initialize leading coefficients
        this.aCoeffs[0] = 1.0;
        this.bCoeffs[0] = 1.0;

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
    public void method1(double[] aCoeffs, double[] bCoeffs) throws IllegalArgumentException {
        if (aCoeffs.length != order) {
            throw new IllegalArgumentException(
                "aCoeffs must be of size " + order + ", got " + aCoeffs.length
            );
        }

        if (aCoeffs[0] == 0.0) {
            throw new IllegalArgumentException("aCoeffs[0] must not be zero");
        }

        if (bCoeffs.length != order) {
            throw new IllegalArgumentException(
                "bCoeffs must be of size " + order + ", got " + bCoeffs.length
            );
        }

        for (int i = 0; i < order; i++) {
            this.aCoeffs[i] = aCoeffs[i];
            this.bCoeffs[i] = bCoeffs[i];
        }
    }

    /**
     * Processes a single input sample and returns the filtered output sample.
     *
     * @param inputSample the input sample
     * @return the filtered output sample
     */
    public double method2(double inputSample) {
        double outputSample = 0.0;

        // Compute current output based on history and coefficients
        for (int i = 1; i <= order; i++) {
            outputSample += (bCoeffs[i] * inputHistory[i - 1] - aCoeffs[i] * outputHistory[i - 1]);
        }
        outputSample = (outputSample + bCoeffs[0] * inputSample) / aCoeffs[0];

        // Shift history buffers
        for (int i = order - 1; i > 0; i--) {
            inputHistory[i] = inputHistory[i - 1];
            outputHistory[i] = outputHistory[i - 1];
        }

        inputHistory[0] = inputSample;
        outputHistory[0] = outputSample;

        return outputSample;
    }
}