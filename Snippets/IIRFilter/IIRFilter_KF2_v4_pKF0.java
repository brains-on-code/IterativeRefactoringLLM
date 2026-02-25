package com.thealgorithms.audiofilters;

import java.util.Arrays;

public class IIRFilter {

    private final int order;
    private final double[] denominatorCoefficients; // a
    private final double[] numeratorCoefficients;   // b
    private final double[] inputHistory;            // xHistory
    private final double[] outputHistory;           // yHistory

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

    public void setCoefficients(double[] denominator, double[] numerator) {
        validateCoefficients(denominator, numerator);

        // Copy user-provided coefficients into internal arrays starting at index 1
        System.arraycopy(denominator, 0, denominatorCoefficients, 1, order);
        System.arraycopy(numerator, 0, numeratorCoefficients, 1, order);
    }

    private void validateCoefficients(double[] denominator, double[] numerator) {
        if (denominator == null || numerator == null) {
            throw new IllegalArgumentException("Coefficient arrays must not be null");
        }

        if (denominator.length != order) {
            throw new IllegalArgumentException(
                "Denominator coefficients must be of size " + order + ", got " + denominator.length
            );
        }

        if (numerator.length != order) {
            throw new IllegalArgumentException(
                "Numerator coefficients must be of size " + order + ", got " + numerator.length
            );
        }

        if (denominator[0] == 0.0) {
            throw new IllegalArgumentException("First denominator coefficient must not be zero");
        }
    }

    public double process(double inputSample) {
        double outputSample = 0.0;

        // Apply IIR difference equation
        for (int i = 1; i <= order; i++) {
            outputSample += numeratorCoefficients[i] * inputHistory[i - 1]
                - denominatorCoefficients[i] * outputHistory[i - 1];
        }

        outputSample = (outputSample + numeratorCoefficients[0] * inputSample)
            / denominatorCoefficients[0];

        updateHistory(inputSample, outputSample);

        return outputSample;
    }

    private void updateHistory(double newInput, double newOutput) {
        if (order > 1) {
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