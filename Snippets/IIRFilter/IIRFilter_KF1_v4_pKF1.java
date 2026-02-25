package com.thealgorithms.audiofilters;

/**
 * Infinite Impulse Response (IIR) filter implementation.
 *
 * The filter is defined by its order and its numerator (b) and denominator (a)
 * coefficients. The output is computed using the standard IIR difference
 * equation.
 */
public class IIRFilter {

    private final int filterOrder;
    private final double[] denominatorCoefficients; // a-coefficients
    private final double[] numeratorCoefficients;   // b-coefficients
    private final double[] previousInputs;
    private final double[] previousOutputs;

    /**
     * Constructs an IIR filter of the given order.
     *
     * @param filterOrder the order of the filter (must be >= 1)
     * @throws IllegalArgumentException if filterOrder is less than 1
     */
    public IIRFilter(int filterOrder) throws IllegalArgumentException {
        if (filterOrder < 1) {
            throw new IllegalArgumentException("filterOrder must be greater than zero");
        }

        this.filterOrder = filterOrder;
        this.denominatorCoefficients = new double[filterOrder + 1];
        this.numeratorCoefficients = new double[filterOrder + 1];

        denominatorCoefficients[0] = 1.0;
        numeratorCoefficients[0] = 1.0;

        this.previousInputs = new double[filterOrder];
        this.previousOutputs = new double[filterOrder];
    }

    /**
     * Sets the filter coefficients.
     *
     * @param newDenominatorCoefficients the denominator coefficients (a), length must be filterOrder + 1
     * @param newNumeratorCoefficients   the numerator coefficients (b), length must be filterOrder + 1
     * @throws IllegalArgumentException if the lengths are incorrect or newDenominatorCoefficients[0] is zero
     */
    public void setCoefficients(double[] newDenominatorCoefficients, double[] newNumeratorCoefficients)
        throws IllegalArgumentException {

        if (newDenominatorCoefficients.length != filterOrder + 1) {
            throw new IllegalArgumentException(
                "denominatorCoefficients must be of size " + (filterOrder + 1)
                    + ", got " + newDenominatorCoefficients.length
            );
        }

        if (newDenominatorCoefficients[0] == 0.0) {
            throw new IllegalArgumentException("denominatorCoefficients[0] must not be zero");
        }

        if (newNumeratorCoefficients.length != filterOrder + 1) {
            throw new IllegalArgumentException(
                "numeratorCoefficients must be of size " + (filterOrder + 1)
                    + ", got " + newNumeratorCoefficients.length
            );
        }

        System.arraycopy(newDenominatorCoefficients, 0, this.denominatorCoefficients, 0, filterOrder + 1);
        System.arraycopy(newNumeratorCoefficients, 0, this.numeratorCoefficients, 0, filterOrder + 1);
    }

    /**
     * Processes a single input sample and returns the filtered output sample.
     *
     * @param inputSample the input sample
     * @return the filtered output sample
     */
    public double processSample(double inputSample) {
        double outputSample = 0.0;

        for (int coefficientIndex = 1; coefficientIndex <= filterOrder; coefficientIndex++) {
            outputSample += numeratorCoefficients[coefficientIndex] * previousInputs[coefficientIndex - 1]
                - denominatorCoefficients[coefficientIndex] * previousOutputs[coefficientIndex - 1];
        }

        outputSample = (outputSample + numeratorCoefficients[0] * inputSample) / denominatorCoefficients[0];

        for (int historyIndex = filterOrder - 1; historyIndex > 0; historyIndex--) {
            previousInputs[historyIndex] = previousInputs[historyIndex - 1];
            previousOutputs[historyIndex] = previousOutputs[historyIndex - 1];
        }

        previousInputs[0] = inputSample;
        previousOutputs[0] = outputSample;

        return outputSample;
    }
}