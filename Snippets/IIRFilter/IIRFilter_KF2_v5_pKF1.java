package com.thealgorithms.audiofilters;

public class IIRFilter {

    private final int filterOrder;
    private final double[] denominatorCoefficients;
    private final double[] numeratorCoefficients;
    private final double[] previousInputs;
    private final double[] previousOutputs;

    public IIRFilter(int filterOrder) throws IllegalArgumentException {
        if (filterOrder < 1) {
            throw new IllegalArgumentException("Filter order must be greater than zero");
        }

        this.filterOrder = filterOrder;
        this.denominatorCoefficients = new double[filterOrder + 1];
        this.numeratorCoefficients = new double[filterOrder + 1];

        this.denominatorCoefficients[0] = 1.0;
        this.numeratorCoefficients[0] = 1.0;

        this.previousInputs = new double[filterOrder];
        this.previousOutputs = new double[filterOrder];
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

        for (int index = 0; index < filterOrder; index++) {
            denominatorCoefficients[index] = denominator[index];
            numeratorCoefficients[index] = numerator[index];
        }
    }

    public double process(double currentInputSample) {
        double currentOutputSample = 0.0;

        for (int coefficientIndex = 1; coefficientIndex <= filterOrder; coefficientIndex++) {
            currentOutputSample += numeratorCoefficients[coefficientIndex] * previousInputs[coefficientIndex - 1]
                - denominatorCoefficients[coefficientIndex] * previousOutputs[coefficientIndex - 1];
        }

        currentOutputSample =
            (currentOutputSample + numeratorCoefficients[0] * currentInputSample) / denominatorCoefficients[0];

        for (int historyIndex = filterOrder - 1; historyIndex > 0; historyIndex--) {
            previousInputs[historyIndex] = previousInputs[historyIndex - 1];
            previousOutputs[historyIndex] = previousOutputs[historyIndex - 1];
        }

        previousInputs[0] = currentInputSample;
        previousOutputs[0] = currentOutputSample;

        return currentOutputSample;
    }
}