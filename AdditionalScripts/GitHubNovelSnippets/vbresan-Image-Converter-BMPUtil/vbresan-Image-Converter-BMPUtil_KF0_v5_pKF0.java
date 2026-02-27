package biz.binarysolutions.imageconverter.image;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

class BMPUtil {

    private static final int BITMAP_FILE_HEADER_SIZE = 14;
    private static final int BITMAP_INFO_HEADER_SIZE = 40;

    // Bitmap file header
    private static final byte[] BF_TYPE = {'B', 'M'};
    private static final int BF_RESERVED_1 = 0;
    private static final int BF_RESERVED_2 = 0;
    private static final int BF_OFF_BITS = BITMAP_FILE_HEADER_SIZE + BITMAP_INFO_HEADER_SIZE;

    // Bitmap info header
    private static final int BI_SIZE = BITMAP_INFO_HEADER_SIZE;
    private static final int BI_PLANES = 1;
    private static final int BI_BIT_COUNT = 24;
    private static final int BI_COMPRESSION = 0;
    private static final int BI_X_PELS_PER_METER = 0;
    private static final int BI_Y_PELS_PER_METER = 0;
    private static final int BI_CLR_USED = 0;
    private static final int BI_CLR_IMPORTANT = 0;

    private int bfSize;
    private int biWidth;
    private int biHeight;
    private int biSizeImage;

    private int[] pixels;
    private ByteBuffer buffer;
    private OutputStream outputStream;

    private void saveBitmap(Bitmap bitmap, String filename) throws IOException {
        try (FileOutputStream out = new FileOutputStream(filename)) {
            saveBitmap(bitmap, out);
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
        biWidth = bitmap.getWidth();
        biHeight = bitmap.getHeight();

        pixels = new int[biWidth * biHeight];
        bitmap.getPixels(pixels, 0, biWidth, 0, 0, biWidth, biHeight);

        int rowSize = biWidth * 3;
        int rowPadding = calculateRowPadding(rowSize);

        biSizeImage = (rowSize + rowPadding) * biHeight;
        bfSize = biSizeImage + BITMAP_FILE_HEADER_SIZE + BITMAP_INFO_HEADER_SIZE;

        buffer = ByteBuffer.allocate(bfSize);
    }

    private int calculateRowPadding(int rowSize) {
        return (4 - (rowSize % 4)) % 4;
    }

    private void writeBitmapPixels() {
        int rowSize = biWidth * 3;
        int paddingPerRow = calculateRowPadding(rowSize);
        byte[] rgb = new byte[3];

        for (int y = biHeight - 1; y >= 0; y--) {
            int rowStart = y * biWidth;
            for (int x = 0; x < biWidth; x++) {
                int value = pixels[rowStart + x];

                rgb[0] = (byte) (value & 0xFF);         // B
                rgb[1] = (byte) ((value >> 8) & 0xFF);  // G
                rgb[2] = (byte) ((value >> 16) & 0xFF); // R
                buffer.put(rgb);
            }
            addRowPadding(paddingPerRow);
        }
    }

    private void addRowPadding(int paddingPerRow) {
        for (int i = 0; i < paddingPerRow; i++) {
            buffer.put((byte) 0x00);
        }
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
        return new byte[]{
                (byte) (value & 0xFF),
                (byte) ((value >> 8) & 0xFF)
        };
    }

    private byte[] intToDWord(int value) {
        return new byte[]{
                (byte) (value & 0xFF),
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