package com.thealgorithms.audiofilters;

public class IIRFilter {

    private final int order;
    private final double[] denominator;
    private final double[] numerator;
    private final double[] inputHistory;
    private final double[] outputHistory;

    public IIRFilter(int order) throws IllegalArgumentException {
        if (order < 1) {
            throw new IllegalArgumentException("Filter order must be greater than zero");
        }

        this.order = order;
        this.denominator = new double[order + 1];
        this.numerator = new double[order + 1];

        this.denominator[0] = 1.0;
        this.numerator[0] = 1.0;

        this.inputHistory = new double[order];
        this.outputHistory = new double[order];
    }

    public void setCoefficients(double[] denominatorCoefficients, double[] numeratorCoefficients)
        throws IllegalArgumentException {

        if (denominatorCoefficients.length != order) {
            throw new IllegalArgumentException(
                "Denominator coefficients must be of size " + order + ", got " + denominatorCoefficients.length
            );
        }

        if (denominatorCoefficients[0] == 0.0) {
            throw new IllegalArgumentException("First denominator coefficient must not be zero");
        }

        if (numeratorCoefficients.length != order) {
            throw new IllegalArgumentException(
                "Numerator coefficients must be of size " + order + ", got " + numeratorCoefficients.length
            );
        }

        for (int i = 0; i < order; i++) {
            denominator[i] = denominatorCoefficients[i];
            numerator[i] = numeratorCoefficients[i];
        }
    }

    public double process(double inputSample) {
        double outputSample = 0.0;

        for (int i = 1; i <= order; i++) {
            outputSample += numerator[i] * inputHistory[i - 1]
                - denominator[i] * outputHistory[i - 1];
        }

        outputSample = (outputSample + numerator[0] * inputSample) / denominator[0];

        for (int i = order - 1; i > 0; i--) {
            inputHistory[i] = inputHistory[i - 1];
            outputHistory[i] = outputHistory[i - 1];
        }

        inputHistory[0] = inputSample;
        outputHistory[0] = outputSample;

        return outputSample;
    }
}