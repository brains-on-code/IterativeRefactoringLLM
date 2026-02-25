package com.thealgorithms.audiofilters;

/**
 * N-Order IIR Filter. Assumes inputs are normalized to [-1, 1].
 *
 * Based on the difference equation from
 * <a href="https://en.wikipedia.org/wiki/Infinite_impulse_response">Wikipedia link</a>
 */
public class IIRFilter {

    private final int order;
    private final double[] coeffsA;
    private final double[] coeffsB;
    private final double[] historyX;
    private final double[] historyY;

    /**
     * Construct an IIR Filter.
     *
     * @param order the filter's order
     * @throws IllegalArgumentException if order is zero or less
     */
    public IIRFilter(int order) {
        if (order < 1) {
            throw new IllegalArgumentException("order must be greater than zero");
        }

        this.order = order;
        this.coeffsA = new double[order + 1];
        this.coeffsB = new double[order + 1];

        // Default to pass-through
        this.coeffsA[0] = 1.0;
        this.coeffsB[0] = 1.0;

        this.historyX = new double[order];
        this.historyY = new double[order];
    }

    /**
     * Set filter coefficients.
     *
     * @param aCoeffs Denominator coefficients (a[0..order])
     * @param bCoeffs Numerator coefficients (b[0..order])
     * @throws IllegalArgumentException if {@code aCoeffs} or {@code bCoeffs} is
     *                                  not of size {@code order + 1}, or if {@code aCoeffs[0]} is 0.0
     */
    public void setCoeffs(double[] aCoeffs, double[] bCoeffs) {
        validateCoefficients(aCoeffs, bCoeffs);

        System.arraycopy(aCoeffs, 0, coeffsA, 0, order + 1);
        System.arraycopy(bCoeffs, 0, coeffsB, 0, order + 1);
    }

    private void validateCoefficients(double[] aCoeffs, double[] bCoeffs) {
        int expectedLength = order + 1;

        if (aCoeffs == null || bCoeffs == null) {
            throw new IllegalArgumentException("Coefficient arrays must not be null");
        }

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

    /**
     * Process a single sample.
     *
     * @param sample the sample to process
     * @return the processed sample
     */
    public double process(double sample) {
        double result = computeOutput(sample);
        updateHistory(sample, result);
        return result;
    }

    private double computeOutput(double sample) {
        double acc = 0.0;

        for (int i = 1; i <= order; i++) {
            acc += coeffsB[i] * historyX[i - 1] - coeffsA[i] * historyY[i - 1];
        }

        return (acc + coeffsB[0] * sample) / coeffsA[0];
    }

    private void updateHistory(double sample, double result) {
        for (int i = order - 1; i > 0; i--) {
            historyX[i] = historyX[i - 1];
            historyY[i] = historyY[i - 1];
        }

        historyX[0] = sample;
        historyY[0] = result;
    }
}