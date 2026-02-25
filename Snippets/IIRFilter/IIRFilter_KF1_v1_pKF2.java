package com.thealgorithms.audiofilters;

/**
 * Simple IIR (Infinite Impulse Response) filter implementation.
 *
 * The filter is defined by:
 *   y[n] = (b0 * x[n] + b1 * x[n-1] + ... + bN * x[n-N]
 *          - a1 * y[n-1] - ... - aN * y[n-N]) / a0
 *
 * Coefficients are provided via {@link #setCoefficients(double[], double[])}.
 */
public class Class1 {

    /** Filter order (number of feedback/feedforward coefficients excluding a0/b0). */
    private final int order;

    /** Denominator (feedback) coefficients a[0..order]. a[0] must be non-zero. */
    private final double[] aCoeffs;

    /** Numerator (feedforward) coefficients b[0..order]. */
    private final double[] bCoeffs;

    /** Input history buffer x[n-1], x[n-2], ..., x[n-order]. */
    private final double[] inputHistory;

    /** Output history buffer y[n-1], y[n-2], ..., y[n-order]. */
    private final double[] outputHistory;

    /**
     * Creates an IIR filter of the given order.
     *
     * @param order filter order (must be >= 1)
     * @throws IllegalArgumentException if {@code order < 1}
     */
    public Class1(int order) throws IllegalArgumentException {
        if (order < 1) {
            throw new IllegalArgumentException("order must be greater than zero");
        }

        this.order = order;
        this.aCoeffs = new double[order + 1];
        this.bCoeffs = new double[order + 1];

        // Initialize a0 and b0 to 1.0 by default.
        this.aCoeffs[0] = 1.0;
        this.bCoeffs[0] = 1.0;

        this.inputHistory = new double[order];
        this.outputHistory = new double[order];
    }

    /**
     * Sets the filter coefficients.
     *
     * @param aCoeffs denominator (feedback) coefficients a[0..order-1];
     *                a[0] must be non-zero
     * @param bCoeffs numerator (feedforward) coefficients b[0..order-1]
     * @throws IllegalArgumentException if the arrays are not of length {@code order}
     *                                  or if {@code aCoeffs[0] == 0.0}
     */
    public void setCoefficients(double[] aCoeffs, double[] bCoeffs) throws IllegalArgumentException {
        if (aCoeffs.length != order) {
            throw new IllegalArgumentException("aCoeffs must be of size " + order + ", got " + aCoeffs.length);
        }

        if (aCoeffs[0] == 0.0) {
            throw new IllegalArgumentException("aCoeffs[0] must not be zero");
        }

        if (bCoeffs.length != order) {
            throw new IllegalArgumentException("bCoeffs must be of size " + order + ", got " + bCoeffs.length);
        }

        for (int i = 0; i < order; i++) {
            this.aCoeffs[i] = aCoeffs[i];
            this.bCoeffs[i] = bCoeffs[i];
        }
    }

    /**
     * Processes a single input sample and returns the filtered output sample.
     *
     * @param inputSample current input sample x[n]
     * @return filtered output sample y[n]
     */
    public double processSample(double inputSample) {
        double outputSample = 0.0;

        // Compute the current output using the difference equation.
        for (int i = 1; i <= order; i++) {
            outputSample += (bCoeffs[i] * inputHistory[i - 1] - aCoeffs[i] * outputHistory[i - 1]);
        }
        outputSample = (outputSample + bCoeffs[0] * inputSample) / aCoeffs[0];

        // Shift history buffers: newest at index 0, oldest at index order-1.
        for (int i = order - 1; i > 0; i--) {
            inputHistory[i] = inputHistory[i - 1];
            outputHistory[i] = outputHistory[i - 1];
        }

        inputHistory[0] = inputSample;
        outputHistory[0] = outputSample;

        return outputSample;
    }
}