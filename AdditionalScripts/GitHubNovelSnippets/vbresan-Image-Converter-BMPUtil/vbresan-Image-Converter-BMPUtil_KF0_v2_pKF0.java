package biz.binarysolutions.imageconverter.image;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

@SuppressWarnings("FieldCanBeLocal")
class BMPUtil {

    private static final int BITMAPFILEHEADER_SIZE = 14;
    private static final int BITMAPINFOHEADER_SIZE = 40;

    // Bitmap file header
    private static final byte[] BF_TYPE = {'B', 'M'};
    private int bfSize = 0;
    private static final int BF_RESERVED_1 = 0;
    private static final int BF_RESERVED_2 = 0;
    private static final int BF_OFF_BITS = BITMAPFILEHEADER_SIZE + BITMAPINFOHEADER_SIZE;

    // Bitmap info header
    private static final int BI_SIZE = BITMAPINFOHEADER_SIZE;
    private int biWidth = 0;
    private int biHeight = 0;
    private static final int BI_PLANES = 1;
    private static final int BI_BIT_COUNT = 24;
    private static final int BI_COMPRESSION = 0;
    private int biSizeImage = 0x030000;
    private static final int BI_X_PELS_PER_METER = 0x0;
    private static final int BI_Y_PELS_PER_METER = 0x0;
    private static final int BI_CLR_USED = 0;
    private static final int BI_CLR_IMPORTANT = 0;

    // Bitmap raw data
    private int[] pixels;

    // File section
    private ByteBuffer buffer = null;
    private OutputStream outputStream;

    private void saveBitmap(Bitmap bitmap, String filename) throws IOException {
        try (FileOutputStream out = new FileOutputStream(filename)) {
            this.outputStream = out;
            save(bitmap);
        }
    }

    private void saveBitmap(Bitmap bitmap, OutputStream outputStream) throws IOException {
        this.outputStream = outputStream;
        save(bitmap);
    }

    private void save(Bitmap bitmap) throws IOException {
        prepareBitmapData(bitmap);
        writeBitmapFileHeader();
        writeBitmapInfoHeader();
        writeBitmapPixels();
        outputStream.write(buffer.array());
    }

    private void prepareBitmapData(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

        int rowSize = width * 3;
        int rowPadding = (4 - (rowSize % 4)) % 4;
        int totalPadding = rowPadding * height;

        biSizeImage = (rowSize * height) + totalPadding;
        bfSize = biSizeImage + BITMAPFILEHEADER_SIZE + BITMAPINFOHEADER_SIZE;

        buffer = ByteBuffer.allocate(bfSize);
        biWidth = width;
        biHeight = height;
    }

    private void writeBitmapPixels() {
        int totalPixels = biWidth * biHeight;
        int lastRowStartIndex = totalPixels - biWidth;

        int rowSize = biWidth * 3;
        int pad = (4 - (rowSize % 4)) % 4;

        byte[] rgb = new byte[3];

        int rowIndex = lastRowStartIndex;
        int lastRowIndex = rowIndex;
        int rowPixelCount = 1;
        int totalPadCount = 0;

        for (int j = 0; j < totalPixels - 1; j++) {
            int value = pixels[rowIndex];

            rgb[0] = (byte) (value & 0xFF);
            rgb[1] = (byte) ((value >> 8) & 0xFF);
            rgb[2] = (byte) ((value >> 16) & 0xFF);
            buffer.put(rgb);

            if (rowPixelCount == biWidth) {
                totalPadCount += pad;
                for (int i = 0; i < pad; i++) {
                    buffer.put((byte) 0x00);
                }
                rowPixelCount = 1;
                rowIndex = lastRowIndex - biWidth;
                lastRowIndex = rowIndex;
            } else {
                rowPixelCount++;
            }
            rowIndex++;
        }

        bfSize += totalPadCount - pad;
        biSizeImage += totalPadCount - pad;
    }

    private void writeBitmapFileHeader() {
        buffer.put(BF_TYPE);
        buffer.put(intToDWord(bfSize));
        buffer.put(intToWord(BF_RESERVED_1));
        buffer.put(intToWord(BF_RESERVED_2));
        buffer.put(intToDWord(BF_OFF_BITS));
    }

    private void writeBitmapInfoHeader() {
        buffer.put(intToDWord(BI_SIZE));
        buffer.put(intToDWord(biWidth));
        buffer.put(intToDWord(biHeight));
        buffer.put(intToWord(BI_PLANES));
        buffer.put(intToWord(BI_BIT_COUNT));
        buffer.put(intToDWord(BI_COMPRESSION));
        buffer.put(intToDWord(biSizeImage));
        buffer.put(intToDWord(BI_X_PELS_PER_METER));
        buffer.put(intToDWord(BI_Y_PELS_PER_METER));
        buffer.put(intToDWord(BI_CLR_USED));
        buffer.put(intToDWord(BI_CLR_IMPORTANT));
    }

    private byte[] intToWord(int value) {
        return new byte[] {
                (byte) (value & 0x00FF),
                (byte) ((value >> 8) & 0x00FF)
        };
    }

    private byte[] intToDWord(int value) {
        return new byte[] {
                (byte) (value & 0x00FF),
                (byte) ((value >> 8) & 0xFF),
                (byte) ((value >> 16) & 0xFF),
                (byte) ((value >> 24) & 0xFF)
        };
    }

    static void encodeToBMP(Bitmap bitmap, File file) throws IOException {
        try (FileOutputStream out = new FileOutputStream(file)) {
            new BMPUtil().saveBitmap(bitmap, out);
        }
    }
}