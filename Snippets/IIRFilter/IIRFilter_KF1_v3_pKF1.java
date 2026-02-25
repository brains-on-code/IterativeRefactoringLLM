package com.thealgorithms.audiofilters;

/**
 * Infinite Impulse Response (IIR) filter implementation.
 *
 * The filter is defined by its order and its numerator (b) and denominator (a)
 * coefficients. The output is computed using the standard IIR difference
 * equation.
 */
public class IIRFilter {

    private final int order;
    private final double[] denominator; // a-coefficients
    private final double[] numerator;   // b-coefficients
    private final double[] previousInputs;
    private final double[] previousOutputs;

    /**
     * Constructs an IIR filter of the given order.
     *
     * @param order the order of the filter (must be >= 1)
     * @throws IllegalArgumentException if order is less than 1
     */
    public IIRFilter(int order) throws IllegalArgumentException {
        if (order < 1) {
            throw new IllegalArgumentException("order must be greater than zero");
        }

        this.order = order;
        this.denominator = new double[order + 1];
        this.numerator = new double[order + 1];

        denominator[0] = 1.0;
        numerator[0] = 1.0;

        this.previousInputs = new double[order];
        this.previousOutputs = new double[order];
    }

    /**
     * Sets the filter coefficients.
     *
     * @param denominatorCoefficients the denominator coefficients (a), length must be order + 1
     * @param numeratorCoefficients   the numerator coefficients (b), length must be order + 1
     * @throws IllegalArgumentException if the lengths are incorrect or denominatorCoefficients[0] is zero
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
     * Processes a single input sample and returns the filtered output sample.
     *
     * @param inputSample the input sample
     * @return the filtered output sample
     */
    public double processSample(double inputSample) {
        double outputSample = 0.0;

        for (int i = 1; i <= order; i++) {
            outputSample += numerator[i] * previousInputs[i - 1]
                - denominator[i] * previousOutputs[i - 1];
        }

        outputSample = (outputSample + numerator[0] * inputSample) / denominator[0];

        for (int i = order - 1; i > 0; i--) {
            previousInputs[i] = previousInputs[i - 1];
            previousOutputs[i] = previousOutputs[i - 1];
        }

        previousInputs[0] = inputSample;
        previousOutputs[0] = outputSample;

        return outputSample;
    }
}