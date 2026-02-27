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

    // Bitmap file header fields
    private final byte[] bfType = {'B', 'M'};
    private int bfSize = 0;
    private final int bfReserved1 = 0;
    private final int bfReserved2 = 0;
    private final int bfOffBits = BITMAPFILEHEADER_SIZE + BITMAPINFOHEADER_SIZE;

    // Bitmap info header fields
    private final int biSize = BITMAPINFOHEADER_SIZE;
    private int biWidth = 0;
    private int biHeight = 0;
    private final int biPlanes = 1;
    private final int biBitCount = 24;
    private final int biCompression = 0;
    private int biSizeImage = 0x030000;
    private final int biXPelsPerMeter = 0x0;
    private final int biYPelsPerMeter = 0x0;
    private final int biClrUsed = 0;
    private final int biClrImportant = 0;

    // Pixel data
    private int[] pixels;

    // Output
    private ByteBuffer buffer = null;
    private OutputStream outputStream;

    private void saveBitmap(Bitmap bitmap, String filename) throws IOException {
        outputStream = new FileOutputStream(filename);
        save(bitmap);
        outputStream.close();
    }

    private void saveBitmap(Bitmap bitmap, OutputStream outputStream) throws IOException {
        this.outputStream = outputStream;
        save(bitmap);
    }

    private void save(Bitmap bitmap) throws IOException {
        convertImage(bitmap);
        writeBitmapFileHeader();
        writeBitmapInfoHeader();
        writeBitmap();
        outputStream.write(buffer.array());
    }

    private void convertImage(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

        int pad = (4 - ((width * 3) % 4)) * height;
        biSizeImage = (width * height * 3) + pad;
        bfSize = biSizeImage + BITMAPFILEHEADER_SIZE + BITMAPINFOHEADER_SIZE;

        buffer = ByteBuffer.allocate(bfSize);
        biWidth = width;
        biHeight = height;
    }

    private void writeBitmap() {
        int size = (biWidth * biHeight) - 1;
        int pad = 4 - ((biWidth * 3) % 4);
        if (pad == 4) {
            pad = 0;
        }

        int rowCount = 1;
        int padCount = 0;
        int rowIndex = size - biWidth;
        int lastRowIndex = rowIndex;

        byte[] rgb = new byte[3];

        for (int j = 0; j < size; j++) {
            int value = pixels[rowIndex];

            rgb[0] = (byte) (value & 0xFF);
            rgb[1] = (byte) ((value >> 8) & 0xFF);
            rgb[2] = (byte) ((value >> 16) & 0xFF);
            buffer.put(rgb);

            if (rowCount == biWidth) {
                padCount += pad;
                for (int i = 0; i < pad; i++) {
                    buffer.put((byte) 0x00);
                }
                rowCount = 1;
                rowIndex = lastRowIndex - biWidth;
                lastRowIndex = rowIndex;
            } else {
                rowCount++;
            }
            rowIndex++;
        }

        bfSize += padCount - pad;
        biSizeImage += padCount - pad;
    }

    private void writeBitmapFileHeader() {
        buffer.put(bfType);
        buffer.put(intToDWord(bfSize));
        buffer.put(intToWord(bfReserved1));
        buffer.put(intToWord(bfReserved2));
        buffer.put(intToDWord(bfOffBits));
    }

    private void writeBitmapInfoHeader() {
        buffer.put(intToDWord(biSize));
        buffer.put(intToDWord(biWidth));
        buffer.put(intToDWord(biHeight));
        buffer.put(intToWord(biPlanes));
        buffer.put(intToWord(biBitCount));
        buffer.put(intToDWord(biCompression));
        buffer.put(intToDWord(biSizeImage));
        buffer.put(intToDWord(biXPelsPerMeter));
        buffer.put(intToDWord(biYPelsPerMeter));
        buffer.put(intToDWord(biClrUsed));
        buffer.put(intToDWord(biClrImportant));
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
        result[1] = (byte) ((value >> 8) & 0x000000FF);
        result[2] = (byte) ((value >> 16) & 0x000000FF);
        result[3] = (byte) ((value >> 24) & 0x000000FF);
        return result;
    }

    static void encodeToBMP(Bitmap bitmap, File file) throws IOException {
        FileOutputStream out = new FileOutputStream(file);
        new BMPUtil().saveBitmap(bitmap, out);
        out.close();
    }
}