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

    private static final byte[] BMP_SIGNATURE = {'B', 'M'};

    private int fileSize = 0;
    private final int reserved1 = 0;
    private final int reserved2 = 0;
    private final int dataOffset = BITMAPFILEHEADER_SIZE + BITMAPINFOHEADER_SIZE;

    private final int infoHeaderSize = BITMAPINFOHEADER_SIZE;
    private int width = 0;
    private int height = 0;
    private final int planes = 1;
    private final int bitCount = 24;
    private final int compression = 0;
    private int imageSize = 0x030000;
    private final int xPelsPerMeter = 0x0;
    private final int yPelsPerMeter = 0x0;
    private final int colorsUsed = 0;
    private final int colorsImportant = 0;

    private int[] pixels;

    private ByteBuffer buffer = null;
    private OutputStream outputStream;

    private void saveBitmap(Bitmap bitmap, String filename) throws IOException {
        try (OutputStream out = new FileOutputStream(filename)) {
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
        int bmpWidth = bitmap.getWidth();
        int bmpHeight = bitmap.getHeight();

        pixels = new int[bmpWidth * bmpHeight];
        bitmap.getPixels(pixels, 0, bmpWidth, 0, 0, bmpWidth, bmpHeight);

        int rowSize = bmpWidth * 3;
        int rowPadding = (4 - (rowSize % 4)) % 4;
        int totalPadding = rowPadding * bmpHeight;

        imageSize = (bmpWidth * bmpHeight * 3) + totalPadding;
        fileSize = imageSize + BITMAPFILEHEADER_SIZE + BITMAPINFOHEADER_SIZE;

        buffer = ByteBuffer.allocate(fileSize);
        width = bmpWidth;
        height = bmpHeight;
    }

    private void writeBitmapPixels() {
        int totalPixels = width * height;
        int lastIndex = totalPixels - 1;

        int rowSize = width * 3;
        int rowPadding = (4 - (rowSize % 4)) % 4;

        byte[] rgb = new byte[3];

        int rowCount = 1;
        int padCount = 0;
        int rowIndex = lastIndex - width;
        int lastRowIndex = rowIndex;

        for (int j = 0; j < lastIndex; j++) {
            int value = pixels[rowIndex];

            rgb[0] = (byte) (value & 0xFF);
            rgb[1] = (byte) ((value >> 8) & 0xFF);
            rgb[2] = (byte) ((value >> 16) & 0xFF);
            buffer.put(rgb);

            if (rowCount == width) {
                padCount += rowPadding;
                for (int i = 0; i < rowPadding; i++) {
                    buffer.put((byte) 0x00);
                }
                rowCount = 1;
                rowIndex = lastRowIndex - width;
                lastRowIndex = rowIndex;
            } else {
                rowCount++;
            }
            rowIndex++;
        }

        fileSize += padCount - rowPadding;
        imageSize += padCount - rowPadding;
    }

    private void writeBitmapFileHeader() {
        buffer.put(BMP_SIGNATURE);
        buffer.put(intToDWord(fileSize));
        buffer.put(intToWord(reserved1));
        buffer.put(intToWord(reserved2));
        buffer.put(intToDWord(dataOffset));
    }

    private void writeBitmapInfoHeader() {
        buffer.put(intToDWord(infoHeaderSize));
        buffer.put(intToDWord(width));
        buffer.put(intToDWord(height));
        buffer.put(intToWord(planes));
        buffer.put(intToWord(bitCount));
        buffer.put(intToDWord(compression));
        buffer.put(intToDWord(imageSize));
        buffer.put(intToDWord(xPelsPerMeter));
        buffer.put(intToDWord(yPelsPerMeter));
        buffer.put(intToDWord(colorsUsed));
        buffer.put(intToDWord(colorsImportant));
    }

    private byte[] intToWord(int value) {
        byte[] result = new byte[2];
        result[0] = (byte) (value & 0x00FF);
        result[1] = (byte) ((value >> 8) & 0x00FF);
        return result;
    }

    private byte[] intToDWord(int value) {
        byte[] result = new byte[4];
        result[0] = (byte) (value & 0x00FF);
        result[1] = (byte) ((value >> 8) & 0xFF);
        result[2] = (byte) ((value >> 16) & 0xFF);
        result[3] = (byte) ((value >> 24) & 0xFF);
        return result;
    }

    static void encodeToBMP(Bitmap bitmap, File file) throws IOException {
        try (FileOutputStream out = new FileOutputStream(file)) {
            new BMPUtil().saveBitmap(bitmap, out);
        }
    }
}