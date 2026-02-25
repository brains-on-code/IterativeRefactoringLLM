package com.thealgorithms.audiofilters;

/**
 * N-Order IIR (Infinite Impulse Response) filter.
 *
 * <p>Assumes inputs are normalized to the range [-1, 1].</p>
 *
 * <p>Implements the standard IIR difference equation as described in:
 * <a href="https://en.wikipedia.org/wiki/Infinite_impulse_response">Wikipedia: Infinite impulse response</a></p>
 */
public class IIRFilter {

    /** Filter order (number of poles/zeros). */
    private final int order;

    /** Denominator coefficients (a[0..order]). */
    private final double[] coeffsA;

    /** Numerator coefficients (b[0..order]). */
    private final double[] coeffsB;

    /** Input history buffer (x[n-1], x[n-2], ..., x[n-order]). */
    private final double[] historyX;

    /** Output history buffer (y[n-1], y[n-2], ..., y[n-order]). */
    private final double[] historyY;

    /**
     * Constructs an IIR filter of the given order.
     *
     * @param order the filter order (must be >= 1)
     * @throws IllegalArgumentException if {@code order < 1}
     */
    public IIRFilter(int order) {
        if (order < 1) {
            throw new IllegalArgumentException("order must be greater than zero");
        }

        this.order = order;
        this.coeffsA = new double[order + 1];
        this.coeffsB = new double[order + 1];

        // Default to a pass-through filter: y[n] = x[n]
        this.coeffsA[0] = 1.0;
        this.coeffsB[0] = 1.0;

        this.historyX = new double[order];
        this.historyY = new double[order];
    }

    /**
     * Sets the filter coefficients.
     *
     * <p>The implemented difference equation is:</p>
     * <pre>
     * y[n] = (b[0] * x[n] + Σ_{i=1..order} (b[i] * x[n - i] - a[i] * y[n - i])) / a[0]
     * </pre>
     *
     * @param aCoeffs denominator coefficients a[0..order]
     * @param bCoeffs numerator coefficients b[0..order]
     * @throws IllegalArgumentException if:
     *                                  <ul>
     *                                      <li>{@code aCoeffs.length != order + 1}</li>
     *                                      <li>{@code bCoeffs.length != order + 1}</li>
     *                                      <li>{@code aCoeffs[0] == 0.0}</li>
     *                                  </ul>
     */
    public void setCoeffs(double[] aCoeffs, double[] bCoeffs) {
        if (aCoeffs.length != order + 1) {
            throw new IllegalArgumentException(
                "aCoeffs must be of size " + (order + 1) + ", got " + aCoeffs.length
            );
        }

        if (bCoeffs.length != order + 1) {
            throw new IllegalArgumentException(
                "bCoeffs must be of size " + (order + 1) + ", got " + bCoeffs.length
            );
        }

        if (aCoeffs[0] == 0.0) {
            throw new IllegalArgumentException("aCoeffs[0] must not be zero");
        }

        System.arraycopy(aCoeffs, 0, coeffsA, 0, order + 1);
        System.arraycopy(bCoeffs, 0, coeffsB, 0, order + 1);
    }

    /**
     * Processes a single input sample and returns the filtered output sample.
     *
     * @param sample the current input sample x[n]
     * @return the filtered output sample y[n]
     */
    public double process(double sample) {
        double result = 0.0;

        // Accumulate contributions from past inputs and outputs.
        for (int i = 1; i <= order; i++) {
            result += coeffsB[i] * historyX[i - 1] - coeffsA[i] * historyY[i - 1];
        }

        // Add current input contribution and normalize by a[0].
        result = (result + coeffsB[0] * sample) / coeffsA[0];

        // Shift history buffers: newest at index 0, oldest at index order - 1.
        for (int i = order - 1; i > 0; i--) {
            historyX[i] = historyX[i - 1];
            historyY[i] = historyY[i - 1];
        }

        historyX[0] = sample;
        historyY[0] = result;

        return result;
    }
}