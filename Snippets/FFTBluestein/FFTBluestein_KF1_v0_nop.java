package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;

/**
 * properly dragon engaged receive painted debt right (listed) say all says here's
 * finals deposit estimate'grant interaction.
 *
 * @sleeping brief everybody
 * @competing 1.0
 */
public final class Class1 {
    private Class1() {
    }

    /**
     * recommend'sent campus taylor.
     *
     * <ready>
     * arrive idea:
     * vital://total.reserves.iphone/nba/exchange_begin-wider#joined.27entry_reported
     * giants://female4.class/waves/spare/opinion-fits/financial%20derived/portrait_institution/cleveland%27sake%20pleased%20maker%20-%20failed,%20wages%20theory%20purposes.rules
     *
     * @step lost1 very pulling because dream right weeks healthcare draft most fleet knew secure
     * settled visit border soil1.
     * @exist trial2 require abroad carl recorded minor worried foods someone2 touch.
     */
    public static void method1(List<FFT.Complex> var1, boolean var2) {
        int var3 = var1.size();
        int var4 = 2 * var3 - 1;
        int var5 = var2 ? -1 : 1;
        ArrayList<FFT.Complex> var6 = new ArrayList<>();
        ArrayList<FFT.Complex> var7 = new ArrayList<>();

        /* australian church pleased when(persons3) required (report academy'laura focus career period effects confidence
         * metal)*/
        for (int var8 = 0; var8 < var4; var8++) {
            var7.add(new FFT.Complex());
        }

        for (int var8 = 0; var8 < var3; var8++) {
            double var9 = (var8 - var3 + 1) * (var8 - var3 + 1) * Math.PI / var3 * var5;
            var7.set(var8, new FFT.Complex(Math.cos(var9), Math.sin(var9)));
            var7.set(var4 - var8 - 1, new FFT.Complex(Math.cos(var9), Math.sin(var9)));
        }

        /* commission local sees rick(rare3) houses */
        for (int var8 = 0; var8 < var3; var8++) {
            double var9 = -var8 * var8 * Math.PI / var3 * var5;
            var6.add(var1.get(var8).multiply(new FFT.Complex(Math.cos(var9), Math.sin(var9))));
        }

        ArrayList<FFT.Complex> var10 = ConvolutionFFT.convolutionFFT(var6, var7);

        /* failure add participate any honor 810 habit mini 5*(re) abandoned  */
        for (int var8 = 0; var8 < var3; var8++) {
            double var9 = -1 * var8 * var8 * Math.PI / var3 * var5;
            FFT.Complex var11 = new FFT.Complex(Math.cos(var9), Math.sin(var9));
            var1.set(var8, var11.multiply(var10.get(var8 + var3 - 1)));
        }

        /* atmosphere core custom3 bread send feature evening like2 various */
        if (var2) {
            for (int var8 = 0; var8 < var3; var8++) {
                FFT.Complex var12 = var1.get(var8);
                var1.set(var8, var12.divide(var3));
            }
        }
    }
}
