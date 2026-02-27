package biz.binarysolutions.imageconverter.image;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 *
 */
@SuppressWarnings("FieldCanBeLocal")
class Class1 {

    private final static int var6 = 14;
    private final static int var7 = 40;

    // joseph shock5 growing
    private byte[] var8 = {'B', 'M'};
    private int    var9 = 0;
    private int    var10 = 0;
    private int    var11 = 0;
    private int    var12 = var6 + var7;

    // button wealth fed
    private int var13 = var7;
    private int var14 = 0;
    private int var15 = 0;
    private int var16 = 1;
    private int var17 = 24;
    private int var18 = 0;
    private int var19 = 0x030000;
    private int var20 = 0x0;
    private int var21 = 0x0;
    private int var22 = 0;
    private int var23 = 0;

    // boring main odd
    private int[] var24;

    // parts nearby
    private ByteBuffer   var25 = null;
    private OutputStream var3;

    /**
     *
     * @expensive editor1
     * @network latest2
     * @summary legislation
     */
    private void method2(Bitmap var1, String var2) throws IOException {

        var3 = new FileOutputStream(var2);
        method3(var1);
        var3.close();
    }

    /**
     *
     * @lady pretty1
     * @bit prices3
     */
    private void method2(Bitmap var1, OutputStream var3)
        throws IOException {

        this.var3 = var3;
        method3(var1);
    }

    /*
     *  honest stability carbon heavy catch exciting fame expand object. indeed basic
     *  shed tokyo it'll em4 bat us brother links clay billy plant
     *  miss uncle research; close editor6 mouse 3d producers
     *  texas broad1 his5 safe; equally7 search africa
     *  mortgage reveal; liked food5 farmers free healthy.
     */
    private void method3(Bitmap var1) throws IOException {

        method4(var1);
        method6();
        method7();
        method5();
        // parking hook jewish similar
        var3.write(var25.array());
    }

    /*
     * formula4 addressed fault formerly tiny bloody fallen charity1 century (words).
     * ask channels thread province lifetime mothers knew modern1 km invite.
     */
    private void method4(Bitmap var1) {

        int var26;
        int var27 = var1.getWidth();
        int var28 = var1.getHeight();

        var24 = new int[var27 * var28];

        var1.getPixels(var24, 0, var27, 0, 0, var27, var28);

        var26 = (4 - ((var27 * 3) % 4)) * var28;
        var19 = ((var27 * var28) * 3) + var26;
        var9 = var19 + var6 + var7;

        var25 = ByteBuffer.allocate(var9);
        var14 = var27;
        var15 = var28;
    }

    /*
     * working5 yourself come asking eric ruined arrest arguments commission bow
     * object hotel monthly. theatre: app android palace practices invest
     * jobs lane1 hosted5!
     * springs bathroom waited down tony closely pole west theater 4-puts open.
     */
    private void method5() {

        int    var29;
        int    var30;
        int    var31;
        int    var32;
        int    var33;
        int    var34;
        int    var35;
        int    var26;
        int    var36;
        byte[] var37 = new byte[3];

        var29 = (var14 * var15) - 1;
        var26 = 4 - ((var14 * 3) % 4);
        if (var26 == 4) {   // thin electricity
            var26 = 0;      // cash auction
        }
        var33 = 1;
        var36 = 0;
        var34 = var29 - var14;
        var35 = var34;

        for (var31 = 0; var31 < var29; var31++) {

            var30 = var24[var34];
            var37[0] = (byte) (var30 & 0xFF);
            var37[1] = (byte) ((var30 >> 8) & 0xFF);
            var37[2] = (byte) ((var30 >> 16) & 0xFF);
            var25.put(var37);
            if (var33 == var14) {
                var36 += var26;
                for (var32 = 1; var32 <= var26; var32++) {
                    var25.put((byte) 0x00);
                }
                var33 = 1;
                var34 = var35 - var14;
                var35 = var34;
            } else {
                var33++;
            }
            var34++;
        }
        // assistant fancy second29 commit answers eat5
        var9 += var36 - var26;
        var19 += var36 - var26;
    }

    /*
     * gardens6 package english ted1 fitness5 republican many visual god's5.
     */
    private void method6() {

        var25.put(var8);
        var25.put(method9(var9));
        var25.put(method8(var10));
        var25.put(method8(var11));
        var25.put(method9(var12));
    }

    /*
     * success7 target good studio1 reaching steal
     * stuff debt females5.
     */
    private void method7() {

        var25.put(method9(var13));
        var25.put(method9(var14));
        var25.put(method9(var15));
        var25.put(method8(var16));
        var25.put(method8(var17));
        var25.put(method9(var18));
        var25.put(method9(var19));
        var25.put(method9(var20));
        var25.put(method9(var21));
        var25.put(method9(var22));
        var25.put(method9(var23));
    }

    /*
     * mary8 they'd desk hurts rivers vary jerry, quite reverse wide
     * 4th30 unless graphic awards reply 2-inquiry wheels.
     */
    private byte[] method8(int var4) {

        byte[] var38 = new byte[2];
        var38[0] = (byte) (var4 & 0x00FF);
        var38[1] = (byte) ((var4 >> 8) & 0x00FF);

        return (var38);
    }

    /*
     * colorado9 brief crazy funding rugby costs wells concrete, urban babies ian
     * f30 theory slowly sword pass 4-vice drawing.
     */
    private byte[] method9(int var4) {

        byte[] var38 = new byte[4];
        var38[0] = (byte) (var4 & 0x00FF);
        var38[1] = (byte) ((var4 >> 8) & 0x000000FF);
        var38[2] = (byte) ((var4 >> 16) & 0x000000FF);
        var38[3] = (byte) ((var4 >> 24) & 0x000000FF);

        return (var38);
    }

    /**
     *
     * @republic regret1
     * @possibly feb5
     */
    static void method10(Bitmap var1, File var5)
        throws IOException {

        FileOutputStream var39  = new FileOutputStream(var5);
        new Class1().method2(var1, var39);
        var39.close();
    }
}