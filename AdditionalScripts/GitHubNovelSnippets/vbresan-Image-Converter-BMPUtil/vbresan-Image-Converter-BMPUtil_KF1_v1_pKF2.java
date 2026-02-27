package biz.binarysolutions.imageconverter.image;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 * Utility for writing a {@link Bitmap} as a 24-bit BMP image.
 */
@SuppressWarnings("FieldCanBeLocal")
class BmpWriter {

    // BMP file header sizes
    private static final int FILE_HEADER_SIZE = 14;
    private static final int INFO_HEADER_SIZE = 40;

    // BMP signature ("BM")
    private final byte[] bmpSignature = {'B', 'M'};

    // File header fields
    private int fileSize = 0;
    private int reserved1 = 0;
    private int reserved2 = 0;
    private int pixelDataOffset = FILE_HEADER_SIZE + INFO_HEADER_SIZE;

    // Info header fields
    private int infoHeaderSize = INFO_HEADER_SIZE;
    private int width = 0;
    private int height = 0;
    private int planes = 1;
    private int bitsPerPixel = 24;
    private int compression = 0;
    private int imageSize = 0x030000;
    private int xPixelsPerMeter = 0x0;
    private int yPixelsPerMeter = 0x0;
    private int colorsUsed = 0;
    private int importantColors = 0;

    // Pixel data
    private int[] pixels;

    // Output buffer and stream
    private ByteBuffer buffer = null;
    private OutputStream outputStream;

    private void write(Bitmap bitmap, String path) throws IOException {
        outputStream = new FileOutputStream(path);
        write(bitmap);
        outputStream.close();
    }

    private void write(Bitmap bitmap, OutputStream outputStream) throws IOException {
        this.outputStream = outputStream;
        write(bitmap);
    }

    private void write(Bitmap bitmap) throws IOException {
        prepareBitmapData(bitmap);
        writeFileHeader();
        writeInfoHeader();
        writePixelData();
        outputStream.write(buffer.array());
    }

    private void prepareBitmapData(Bitmap bitmap) {
        int bmpWidth = bitmap.getWidth();
        int bmpHeight = bitmap.getHeight();

        pixels = new int[bmpWidth * bmpHeight];
        bitmap.getPixels(pixels, 0, bmpWidth, 0, 0, bmpWidth, bmpHeight);

        int rowPadding = (4 - ((bmpWidth * 3) % 4)) * bmpHeight;
        imageSize = (bmpWidth * bmpHeight * 3) + rowPadding;
        fileSize = imageSize + FILE_HEADER_SIZE + INFO_HEADER_SIZE;

        buffer = ByteBuffer.allocate(fileSize);
        width = bmpWidth;
        height = bmpHeight;
    }

    private void writePixelData() {
        int lastPixelIndex = (width * height) - 1;
        int rowPadding = 4 - ((width * 3) % 4);
        if (rowPadding == 4) {
            rowPadding = 0;
        }

        int pixelsInRow = 1;
        int totalPaddingWritten = 0;
        int rowStartIndex = lastPixelIndex - width;
        int currentIndex = rowStartIndex;

        byte[] rgb = new byte[3];

        for (int i = 0; i < lastPixelIndex; i++) {
            int color = pixels[currentIndex];

            rgb[0] = (byte) (color & 0xFF);         // Blue
            rgb[1] = (byte) ((color >> 8) & 0xFF);  // Green
            rgb[2] = (byte) ((color >> 16) & 0xFF); // Red
            buffer.put(rgb);

            if (pixelsInRow == width) {
                totalPaddingWritten += rowPadding;
                for (int p = 0; p < rowPadding; p++) {
                    buffer.put((byte) 0x00);
                }
                pixelsInRow = 1;
                rowStartIndex -= width;
                currentIndex = rowStartIndex;
            } else {
                pixelsInRow++;
                currentIndex++;
            }
        }

        fileSize += totalPaddingWritten - rowPadding;
        imageSize += totalPaddingWritten - rowPadding;
    }

    private void writeFileHeader() {
        buffer.put(bmpSignature);
        buffer.put(intTo4Bytes(fileSize));
        buffer.put(intTo2Bytes(reserved1));
        buffer.put(intTo2Bytes(reserved2));
        buffer.put(intTo4Bytes(pixelDataOffset));
    }

    private void writeInfoHeader() {
        buffer.put(intTo4Bytes(infoHeaderSize));
        buffer.put(intTo4Bytes(width));
        buffer.put(intTo4Bytes(height));
        buffer.put(intTo2Bytes(planes));
        buffer.put(intTo2Bytes(bitsPerPixel));
        buffer.put(intTo4Bytes(compression));
        buffer.put(intTo4Bytes(imageSize));
        buffer.put(intTo4Bytes(xPixelsPerMeter));
        buffer.put(intTo4Bytes(yPixelsPerMeter));
        buffer.put(intTo4Bytes(colorsUsed));
        buffer.put(intTo4Bytes(importantColors));
    }

    private byte[] intTo2Bytes(int value) {
        byte[] result = new byte[2];
        result[0] = (byte) (value & 0x00FF);
        result[1] = (byte) ((value >> 8) & 0x00FF);
        return result;
    }

    private byte[] intTo4Bytes(int value) {
        byte[] result = new byte[4];
        result[0] = (byte) (value & 0x00FF);
        result[1] = (byte) ((value >> 8) & 0x000000FF);
        result[2] = (byte) ((value >> 16) & 0x000000FF);
        result[3] = (byte) ((value >> 24) & 0x000000FF);
        return result;
    }

    static void write(Bitmap bitmap, File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        new BmpWriter().write(bitmap, fos);
        fos.close();
    }
}