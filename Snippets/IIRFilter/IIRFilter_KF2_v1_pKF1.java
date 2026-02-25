package com.thealgorithms.audiofilters;

public class IIRFilter {

    private final int filterOrder;
    private final double[] denominatorCoefficients;
    private final double[] numeratorCoefficients;
    private final double[] inputHistory;
    private final double[] outputHistory;

    public IIRFilter(int filterOrder) throws IllegalArgumentException {
        if (filterOrder < 1) {
            throw new IllegalArgumentException("Filter order must be greater than zero");
        }

        this.filterOrder = filterOrder;
        denominatorCoefficients = new double[filterOrder + 1];
        numeratorCoefficients = new double[filterOrder + 1];

        denominatorCoefficients[0] = 1.0;
        numeratorCoefficients[0] = 1.0;

        inputHistory = new double[filterOrder];
        outputHistory = new double[filterOrder];
    }

    public void setCoefficients(double[] denominator, double[] numerator) throws IllegalArgumentException {
        if (denominator.length != filterOrder) {
            throw new IllegalArgumentException(
                "Denominator coefficients must be of size " + filterOrder + ", got " + denominator.length
            );
        }

        if (denominator[0] == 0.0) {
            throw new IllegalArgumentException("First denominator coefficient must not be zero");
        }

        if (numerator.length != filterOrder) {
            throw new IllegalArgumentException(
                "Numerator coefficients must be of size " + filterOrder + ", got " + numerator.length
            );
        }

        for (int i = 0; i < filterOrder; i++) {
            denominatorCoefficients[i] = denominator[i];
            numeratorCoefficients[i] = numerator[i];
        }
    }

    public double process(double inputSample) {
        double outputSample = 0.0;

        for (int i = 1; i <= filterOrder; i++) {
            outputSample += numeratorCoefficients[i] * inputHistory[i - 1]
                - denominatorCoefficients[i] * outputHistory[i - 1];
        }
        outputSample = (outputSample + numeratorCoefficients[0] * inputSample) / denominatorCoefficients[0];

        for (int i = filterOrder - 1; i > 0; i--) {
            inputHistory[i] = inputHistory[i - 1];
            outputHistory[i] = outputHistory[i - 1];
        }

        inputHistory[0] = inputSample;
        outputHistory[0] = outputSample;

        return outputSample;
    }
}