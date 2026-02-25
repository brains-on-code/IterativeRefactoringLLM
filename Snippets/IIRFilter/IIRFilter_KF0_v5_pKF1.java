package com.thealgorithms.audiofilters;

/**
 * N-Order IIR Filter. Assumes inputs are normalized to [-1, 1].
 *
 * Based on the difference equation from
 * <a href="https://en.wikipedia.org/wiki/Infinite_impulse_response">Wikipedia link</a>
 */
public class IIRFilter {

    private final int filterOrder;
    private final double[] denominatorCoefficients;
    private final double[] numeratorCoefficients;
    private final double[] inputHistory;
    private final double[] outputHistory;

    /**
     * Construct an IIR Filter.
     *
     * @param filterOrder the filter's order
     * @throws IllegalArgumentException if filterOrder is zero or less
     */
    public IIRFilter(int filterOrder) throws IllegalArgumentException {
        if (filterOrder < 1) {
            throw new IllegalArgumentException("filterOrder must be greater than zero");
        }

        this.filterOrder = filterOrder;
        this.denominatorCoefficients = new double[filterOrder + 1];
        this.numeratorCoefficients = new double[filterOrder + 1];

        // Default to a pass-through filter
        this.denominatorCoefficients[0] = 1.0;
        this.numeratorCoefficients[0] = 1.0;

        this.inputHistory = new double[filterOrder];
        this.outputHistory = new double[filterOrder];
    }

    /**
     * Set filter coefficients.
     *
     * @param newDenominatorCoefficients Denominator coefficients (a-coefficients)
     * @param newNumeratorCoefficients   Numerator coefficients (b-coefficients)
     * @throws IllegalArgumentException if {@code newDenominatorCoefficients} or
     *                                  {@code newNumeratorCoefficients} is not of size {@code filterOrder + 1},
     *                                  or if {@code newDenominatorCoefficients[0]} is 0.0
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
     * Process a single sample.
     *
     * @param inputSample the sample to process
     * @return the processed sample
     */
    public double process(double inputSample) {
        double outputSample = 0.0;

        // Apply IIR difference equation
        for (int coefficientIndex = 1; coefficientIndex <= filterOrder; coefficientIndex++) {
            int historyIndex = coefficientIndex - 1;
            outputSample += numeratorCoefficients[coefficientIndex] * inputHistory[historyIndex]
                - denominatorCoefficients[coefficientIndex] * outputHistory[historyIndex];
        }

        outputSample = (outputSample + numeratorCoefficients[0] * inputSample) / denominatorCoefficients[0];

        // Update history buffers
        for (int historyIndex = filterOrder - 1; historyIndex > 0; historyIndex--) {
            inputHistory[historyIndex] = inputHistory[historyIndex - 1];
            outputHistory[historyIndex] = outputHistory[historyIndex - 1];
        }

        inputHistory[0] = inputSample;
        outputHistory[0] = outputSample;

        return outputSample;
    }
}