package com.thealgorithms.audiofilters;

/**
 * Infinite Impulse Response (IIR) filter.
 *
 * Implements:
 *   y[n] = (b0 * x[n] + b1 * x[n-1] + ... + bN * x[n-N]
 *          - a1 * y[n-1] - ... - aN * y[n-N]) / a0
 *
 * Coefficients are configured via {@link #setCoefficients(double[], double[])}.
 */
public class Class1 {

    /** Filter order (N in the equation above). */
    private final int order;

    /** Denominator (feedback) coefficients a[0..order]; a[0] must be non-zero. */
    private final double[] aCoeffs;

    /** Numerator (feedforward) coefficients b[0..order]. */
    private final double[] bCoeffs;

    /** Past input samples: x[n-1], x[n-2], ..., x[n-order]. */
    private final double[] inputHistory;

    /** Past output samples: y[n-1], y[n-2], ..., y[n-order]. */
    private final double[] outputHistory;

    /**
     * Creates an IIR filter of the given order.
     *
     * @param order filter order (must be >= 1)
     * @throws IllegalArgumentException if {@code order < 1}
     */
    public Class1(int order) {
        if (order < 1) {
            throw new IllegalArgumentException("order must be greater than zero");
        }

        this.order = order;
        this.aCoeffs = new double[order + 1];
        this.bCoeffs = new double[order + 1];

        // Default to a pass-through filter: a0 = 1, b0 = 1.
        this.aCoeffs[0] = 1.0;
        this.bCoeffs[0] = 1.0;

        this.inputHistory = new double[order];
        this.outputHistory = new double[order];
    }

    /**
     * Sets the filter coefficients.
     *
     * Expected array sizes: {@code order + 1}, containing indices 0..order.
     *
     * @param aCoeffs denominator (feedback) coefficients a[0..order];
     *                a[0] must be non-zero
     * @param bCoeffs numerator (feedforward) coefficients b[0..order]
     * @throws IllegalArgumentException if the arrays are not of length {@code order + 1}
     *                                  or if {@code aCoeffs[0] == 0.0}
     */
    public void setCoefficients(double[] aCoeffs, double[] bCoeffs) {
        if (aCoeffs.length != order + 1) {
            throw new IllegalArgumentException(
                "aCoeffs must be of size " + (order + 1) + ", got " + aCoeffs.length
            );
        }

        if (aCoeffs[0] == 0.0) {
            throw new IllegalArgumentException("aCoeffs[0] must not be zero");
        }

        if (bCoeffs.length != order + 1) {
            throw new IllegalArgumentException(
                "bCoeffs must be of size " + (order + 1) + ", got " + bCoeffs.length
            );
        }

        System.arraycopy(aCoeffs, 0, this.aCoeffs, 0, order + 1);
        System.arraycopy(bCoeffs, 0, this.bCoeffs, 0, order + 1);
    }

    /**
     * Processes a single input sample and returns the filtered output sample.
     *
     * @param inputSample current input sample x[n]
     * @return filtered output sample y[n]
     */
    public double processSample(double inputSample) {
        double outputSample = 0.0;

        // Contribution from past inputs and outputs (i = 1..order).
        for (int i = 1; i <= order; i++) {
            outputSample += bCoeffs[i] * inputHistory[i - 1]
                          - aCoeffs[i] * outputHistory[i - 1];
        }

        // Add current input contribution and normalize by a0.
        outputSample = (outputSample + bCoeffs[0] * inputSample) / aCoeffs[0];

        // Shift history: index 0 is most recent, index order-1 is oldest.
        for (int i = order - 1; i > 0; i--) {
            inputHistory[i] = inputHistory[i - 1];
            outputHistory[i] = outputHistory[i - 1];
        }

        inputHistory[0] = inputSample;
        outputHistory[0] = outputSample;

        return outputSample;
    }
}