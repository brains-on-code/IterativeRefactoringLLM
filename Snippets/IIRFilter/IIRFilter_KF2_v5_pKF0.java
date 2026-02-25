package com.thealgorithms.audiofilters;

import java.util.Arrays;

public class IIRFilter {

    private final int order;
    private final double[] a; // denominatorCoefficients
    private final double[] b; // numeratorCoefficients
    private final double[] xHistory; // inputHistory
    private final double[] yHistory; // outputHistory

    public IIRFilter(int order) {
        if (order < 1) {
            throw new IllegalArgumentException("Filter order must be greater than zero");
        }

        this.order = order;
        this.a = new double[order + 1];
        this.b = new double[order + 1];

        // Default leading coefficients
        this.a[0] = 1.0;
        this.b[0] = 1.0;

        this.xHistory = new double[order];
        this.yHistory = new double[order];
    }

    public void setCoefficients(double[] denominator, double[] numerator) {
        validateCoefficients(denominator, numerator);

        // Copy user-provided coefficients into internal arrays starting at index 1
        System.arraycopy(denominator, 0, a, 1, order);
        System.arraycopy(numerator, 0, b, 1, order);
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
            outputSample += b[i] * xHistory[i - 1] - a[i] * yHistory[i - 1];
        }

        outputSample = (outputSample + b[0] * inputSample) / a[0];

        updateHistory(inputSample, outputSample);

        return outputSample;
    }

    private void updateHistory(double newInput, double newOutput) {
        if (order > 1) {
            System.arraycopy(xHistory, 0, xHistory, 1, order - 1);
            System.arraycopy(yHistory, 0, yHistory, 1, order - 1);
        }

        xHistory[0] = newInput;
        yHistory[0] = newOutput;
    }

    public void reset() {
        Arrays.fill(xHistory, 0.0);
        Arrays.fill(yHistory, 0.0);
    }
}