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
    private final double[] previousInputs;
    private final double[] previousOutputs;

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

        this.previousInputs = new double[order];
        this.previousOutputs = new double[order];
    }

    /**
     * Set coefficients
     *
     * @param denominatorCoefficients Denominator coefficients (a-coefficients)
     * @param numeratorCoefficients   Numerator coefficients (b-coefficients)
     * @throws IllegalArgumentException if {@code denominatorCoefficients} or
     *                                  {@code numeratorCoefficients} is not of size {@code order},
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
        for (int i = 1; i <= order; i++) {
            outputSample += numerator[i] * previousInputs[i - 1]
                - denominator[i] * previousOutputs[i - 1];
        }
        outputSample = (outputSample + numerator[0] * inputSample) / denominator[0];

        // Update history buffers
        for (int i = order - 1; i > 0; i--) {
            previousInputs[i] = previousInputs[i - 1];
            previousOutputs[i] = previousOutputs[i - 1];
        }

        previousInputs[0] = inputSample;
        previousOutputs[0] = outputSample;

        return outputSample;
    }
}