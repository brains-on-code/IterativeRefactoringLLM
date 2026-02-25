package com.thealgorithms.audiofilters;

public class IIRFilter {

    private final int order;
    private final double[] coeffsA;
    private final double[] coeffsB;
    private final double[] historyX;
    private final double[] historyY;

    public IIRFilter(int order) {
        if (order < 1) {
            throw new IllegalArgumentException("order must be greater than zero");
        }

        this.order = order;
        this.coeffsA = new double[order + 1];
        this.coeffsB = new double[order + 1];

        // Identity filter by default: y[n] = x[n]
        this.coeffsA[0] = 1.0;
        this.coeffsB[0] = 1.0;

        this.historyX = new double[order];
        this.historyY = new double[order];
    }

    /**
     * Sets the IIR filter coefficients.
     *
     * @param aCoeffs Denominator coefficients a[0..order]; a[0] must be non-zero.
     * @param bCoeffs Numerator coefficients b[0..order].
     */
    public void setCoeffs(double[] aCoeffs, double[] bCoeffs) {
        validateCoeffsLength(aCoeffs, "aCoeffs");
        validateCoeffsLength(bCoeffs, "bCoeffs");

        if (aCoeffs[0] == 0.0) {
            throw new IllegalArgumentException("aCoeffs[0] must not be zero");
        }

        System.arraycopy(aCoeffs, 0, coeffsA, 0, order + 1);
        System.arraycopy(bCoeffs, 0, coeffsB, 0, order + 1);
    }

    private void validateCoeffsLength(double[] coeffs, String name) {
        if (coeffs.length != order + 1) {
            throw new IllegalArgumentException(
                name + " must be of size " + (order + 1) + ", got " + coeffs.length
            );
        }
    }

    /**
     * Processes a single input sample and returns the filtered output.
     *
     * Direct-form I IIR filter:
     * y[n] = (b0*x[n] + Σ_{i=1..order}(b_i*x[n-i] - a_i*y[n-i])) / a0
     *
     * @param sample current input sample x[n]
     * @return filtered output sample y[n]
     */
    public double process(double sample) {
        double acc = 0.0;

        for (int i = 1; i <= order; i++) {
            acc += coeffsB[i] * historyX[i - 1] - coeffsA[i] * historyY[i - 1];
        }

        double result = (acc + coeffsB[0] * sample) / coeffsA[0];

        shiftHistoryBuffers();

        historyX[0] = sample;
        historyY[0] = result;

        return result;
    }

    private void shiftHistoryBuffers() {
        for (int i = order - 1; i > 0; i--) {
            historyX[i] = historyX[i - 1];
            historyY[i] = historyY[i - 1];
        }
    }
}