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

        // Default leading coefficients
        this.coeffsA[0] = 1.0;
        this.coeffsB[0] = 1.0;

        this.historyX = new double[order];
        this.historyY = new double[order];
    }

    public void setCoeffs(double[] aCoeffs, double[] bCoeffs) {
        validateCoefficients(aCoeffs, bCoeffs);

        // Copy user-provided coefficients into internal arrays starting at index 1
        for (int i = 0; i < order; i++) {
            coeffsA[i + 1] = aCoeffs[i];
            coeffsB[i + 1] = bCoeffs[i];
        }
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

    public double process(double sample) {
        double result = 0.0;

        // Apply IIR difference equation
        for (int i = 1; i <= order; i++) {
            result += coeffsB[i] * historyX[i - 1] - coeffsA[i] * historyY[i - 1];
        }
        result = (result + coeffsB[0] * sample) / coeffsA[0];

        // Shift history buffers
        for (int i = order - 1; i > 0; i--) {
            historyX[i] = historyX[i - 1];
            historyY[i] = historyY[i - 1];
        }

        historyX[0] = sample;
        historyY[0] = result;

        return result;
    }
}