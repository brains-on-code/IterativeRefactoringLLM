package com.thealgorithms.audiofilters;

/**
 * N-Order IIR Filter. Assumes inputs are normalized to [-1, 1].
 *
 * Based on the difference equation from
 * <a href="https://en.wikipedia.org/wiki/Infinite_impulse_response">Wikipedia link</a>
 */
public class IIRFilter {

    private final int order;
    private final double[] denominatorCoeffs; // a-coefficients
    private final double[] numeratorCoeffs;   // b-coefficients
    private final double[] inputHistory;      // x[n-1], x[n-2], ...
    private final double[] outputHistory;     // y[n-1], y[n-2], ...

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
        this.denominatorCoeffs = new double[order + 1];
        this.numeratorCoeffs = new double[order + 1];

        initializeDefaultCoefficients();
        this.inputHistory = new double[order];
        this.outputHistory = new double[order];
    }

    private void initializeDefaultCoefficients() {
        // Default to pass-through
        this.denominatorCoeffs[0] = 1.0;
        this.numeratorCoeffs[0] = 1.0;
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
        copyCoefficients(aCoeffs, bCoeffs);
    }

    private void copyCoefficients(double[] aCoeffs, double[] bCoeffs) {
        int length = order + 1;
        System.arraycopy(aCoeffs, 0, denominatorCoeffs, 0, length);
        System.arraycopy(bCoeffs, 0, numeratorCoeffs, 0, length);
    }

    private void validateCoefficients(double[] aCoeffs, double[] bCoeffs) {
        if (aCoeffs == null || bCoeffs == null) {
            throw new IllegalArgumentException("Coefficient arrays must not be null");
        }

        int expectedLength = order + 1;
        validateArrayLength("aCoeffs", aCoeffs, expectedLength);
        validateArrayLength("bCoeffs", bCoeffs, expectedLength);

        if (aCoeffs[0] == 0.0) {
            throw new IllegalArgumentException("aCoeffs[0] must not be zero");
        }
    }

    private void validateArrayLength(String name, double[] array, int expectedLength) {
        if (array.length != expectedLength) {
            throw new IllegalArgumentException(
                name + " must be of size " + expectedLength + ", got " + array.length
            );
        }
    }

    /**
     * Process a single sample.
     *
     * @param sample the sample to process
     * @return the processed sample
     */
    public double process(double sample) {
        double output = computeOutput(sample);
        updateHistory(sample, output);
        return output;
    }

    private double computeOutput(double sample) {
        double accumulator = 0.0;

        for (int i = 1; i <= order; i++) {
            accumulator += numeratorCoeffs[i] * inputHistory[i - 1]
                - denominatorCoeffs[i] * outputHistory[i - 1];
        }

        return (accumulator + numeratorCoeffs[0] * sample) / denominatorCoeffs[0];
    }

    private void updateHistory(double sample, double output) {
        for (int i = order - 1; i > 0; i--) {
            inputHistory[i] = inputHistory[i - 1];
            outputHistory[i] = outputHistory[i - 1];
        }

        inputHistory[0] = sample;
        outputHistory[0] = output;
    }
}