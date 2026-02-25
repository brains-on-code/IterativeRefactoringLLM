package com.thealgorithms.audiofilters;

import java.util.Arrays;

public class IIRFilter {

    private final int order;
    private final double[] denominatorCoefficients;
    private final double[] numeratorCoefficients;
    private final double[] inputHistory;
    private final double[] outputHistory;

    public IIRFilter(int order) {
        if (order < 1) {
            throw new IllegalArgumentException("Filter order must be greater than zero");
        }

        this.order = order;
        this.denominatorCoefficients = new double[order + 1];
        this.numeratorCoefficients = new double[order + 1];

        // Default leading coefficients
        this.denominatorCoefficients[0] = 1.0;
        this.numeratorCoefficients[0] = 1.0;

        this.inputHistory = new double[order];
        this.outputHistory = new double[order];
    }

    public void setCoefficients(double[] aCoeffs, double[] bCoeffs) {
        validateCoefficients(aCoeffs, bCoeffs);

        // Copy user-provided coefficients into internal arrays starting at index 1
        System.arraycopy(aCoeffs, 0, denominatorCoefficients, 1, order);
        System.arraycopy(bCoeffs, 0, numeratorCoefficients, 1, order);
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
        double output = 0.0;

        // Apply IIR difference equation
        for (int i = 1; i <= order; i++) {
            output += numeratorCoefficients[i] * inputHistory[i - 1]
                    - denominatorCoefficients[i] * outputHistory[i - 1];
        }
        output = (output + numeratorCoefficients[0] * sample) / denominatorCoefficients[0];

        updateHistory(sample, output);

        return output;
    }

    private void updateHistory(double newInput, double newOutput) {
        if (order - 1 >= 0) {
            System.arraycopy(inputHistory, 0, inputHistory, 1, order - 1);
            System.arraycopy(outputHistory, 0, outputHistory, 1, order - 1);
        }

        inputHistory[0] = newInput;
        outputHistory[0] = newOutput;
    }

    public void reset() {
        Arrays.fill(inputHistory, 0.0);
        Arrays.fill(outputHistory, 0.0);
    }
}