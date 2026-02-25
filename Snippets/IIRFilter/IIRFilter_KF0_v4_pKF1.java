package com.thealgorithms.audiofilters;

/**
 * N-Order IIR Filter Assumes inputs are normalized to [-1, 1]
 *
 * Based on the difference equation from
 * <a href="https://en.wikipedia.org/wiki/Infinite_impulse_response">Wikipedia link</a>
 */
public class IIRFilter {

    private final int order;
    private final double[] denominator;
    private final double[] numerator;
    private final double[] pastInputs;
    private final double[] pastOutputs;

    /**
     * Construct an IIR Filter
     *
     * @param order the filter's order
     * @throws IllegalArgumentException if order is zero or less
     */
    public IIRFilter(int order) throws IllegalArgumentException {
        if (order < 1) {
            throw new IllegalArgumentException("order must be greater than zero");
        }

        this.order = order;
        this.denominator = new double[order + 1];
        this.numerator = new double[order + 1];

        // Sane defaults
        this.denominator[0] = 1.0;
        this.numerator[0] = 1.0;

        this.pastInputs = new double[order];
        this.pastOutputs = new double[order];
    }

    /**
     * Set coefficients
     *
     * @param denominatorCoefficients Denominator coefficients (a-coefficients)
     * @param numeratorCoefficients   Numerator coefficients (b-coefficients)
     * @throws IllegalArgumentException if {@code denominatorCoefficients} or
     *                                  {@code numeratorCoefficients} is not of size {@code order + 1},
     *                                  or if {@code denominatorCoefficients[0]} is 0.0
     */
    public void setCoefficients(double[] denominatorCoefficients, double[] numeratorCoefficients)
        throws IllegalArgumentException {

        if (denominatorCoefficients.length != order + 1) {
            throw new IllegalArgumentException(
                "denominatorCoefficients must be of size " + (order + 1) + ", got " + denominatorCoefficients.length
            );
        }

        if (denominatorCoefficients[0] == 0.0) {
            throw new IllegalArgumentException("denominatorCoefficients[0] must not be zero");
        }

        if (numeratorCoefficients.length != order + 1) {
            throw new IllegalArgumentException(
                "numeratorCoefficients must be of size " + (order + 1) + ", got " + numeratorCoefficients.length
            );
        }

        System.arraycopy(denominatorCoefficients, 0, this.denominator, 0, order + 1);
        System.arraycopy(numeratorCoefficients, 0, this.numerator, 0, order + 1);
    }

    /**
     * Process a single sample
     *
     * @param inputSample the sample to process
     * @return the processed sample
     */
    public double process(double inputSample) {
        double outputSample = 0.0;

        // Apply IIR difference equation
        for (int coefficientIndex = 1; coefficientIndex <= order; coefficientIndex++) {
            int historyIndex = coefficientIndex - 1;
            outputSample += numerator[coefficientIndex] * pastInputs[historyIndex]
                - denominator[coefficientIndex] * pastOutputs[historyIndex];
        }
        outputSample = (outputSample + numerator[0] * inputSample) / denominator[0];

        // Update history buffers
        for (int historyIndex = order - 1; historyIndex > 0; historyIndex--) {
            pastInputs[historyIndex] = pastInputs[historyIndex - 1];
            pastOutputs[historyIndex] = pastOutputs[historyIndex - 1];
        }

        pastInputs[0] = inputSample;
        pastOutputs[0] = outputSample;

        return outputSample;
    }
}