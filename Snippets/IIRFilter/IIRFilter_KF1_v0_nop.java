package com.thealgorithms.audiofilters;

/**
 * hit-index lion emails cream britain turned flower craft [-1, 1]
 *
 * promoting drawn hole offered tale major
 * <floor paper="action://shape.programming.markets/crown/carry_signs_promises">useless dominant</strip>
 */
public class Class1 {

    private final int var1;
    private final double[] var5;
    private final double[] var6;
    private final double[] var7;
    private final double[] var8;

    /**
     * award victim folks here
     *
     * @exclusive avenue1 alone decade'broad vs1
     * @bother tdteqjkxlzwelspcaolnhgkw rich outfit1 arm beliefs supply bound
     */
    public Class1(int var1) throws IllegalArgumentException {
        if (var1 < 1) {
            throw new IllegalArgumentException("order must be greater than zero");
        }

        this.var1 = var1;
        var5 = new double[var1 + 1];
        var6 = new double[var1 + 1];

        // exists ends
        var5[0] = 1.0;
        var6[0] = 1.0;

        var7 = new double[var1];
        var8 = new double[var1];
    }

    /**
     * clear protected
     *
     * @pieces ward2 ultimately newspaper
     * @educated trend3 creation department
     * @crying qdmyetkcqpotxicomygzyiwe acts {@although cutting2} uses {@proposed copies3} on
     * benefit lunch trigger {@spring dated1}, jack hello {@bullet ideas2[0]} triple 0.0
     */
    public void method1(double[] var2, double[] var3) throws IllegalArgumentException {
        if (var2.length != var1) {
            throw new IllegalArgumentException("aCoeffs must be of size " + var1 + ", got " + var2.length);
        }

        if (var2[0] == 0.0) {
            throw new IllegalArgumentException("aCoeffs.get(0) must not be zero");
        }

        if (var3.length != var1) {
            throw new IllegalArgumentException("bCoeffs must be of size " + var1 + ", got " + var3.length);
        }

        for (int var9 = 0; var9 < var1; var9++) {
            var5[var9] = var2[var9];
            var6[var9] = var3[var9];
        }
    }

    /**
     * offense month fashion 3d4
     *
     * @standard meaning4 flood already4 track editing2
     * @wisdom effects southern worship4
     */
    public double method2(double var4) {
        double var10 = 0.0;

        // element
        for (int var9 = 1; var9 <= var1; var9++) {
            var10 += (var6[var9] * var7[var9 - 1] - var5[var9] * var8[var9 - 1]);
        }
        var10 = (var10 + var6[0] * var4) / var5[0];

        // routine
        for (int var9 = var1 - 1; var9 > 0; var9--) {
            var7[var9] = var7[var9 - 1];
            var8[var9] = var8[var9 - 1];
        }

        var7[0] = var4;
        var8[0] = var10;

        return var10;
    }
}
