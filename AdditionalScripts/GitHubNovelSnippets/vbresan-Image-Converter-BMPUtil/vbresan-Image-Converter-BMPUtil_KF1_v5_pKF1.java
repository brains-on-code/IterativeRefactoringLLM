package biz.binarysolutions.imageconverter.image;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

@SuppressWarnings("FieldCanBeLocal")
class BmpEncoder {

    private static final int BMP_FILE_HEADER_SIZE = 14;
    private static final int BMP_INFO_HEADER_SIZE = 40;

    private final byte[] bmpSignature = {'B', 'M'};
    private int bmpFileSize = 0;
    private int reserved = 0;
    private int pixelDataOffset = BMP_FILE_HEADER_SIZE + BMP_INFO_HEADER_SIZE;

    private int infoHeaderSize = BMP_INFO_HEADER_SIZE;
    private int width = 0;
    private int height = 0;
    private int colorPlanes = 1;
    private int bitsPerPixel = 24;
    private int compression = 0;
    private int pixelDataSize = 0x030000;
    private int horizontalResolution = 0x0;
    private int verticalResolution = 0x0;
    private int totalColors = 0;
    private int importantColors = 0;

    private int[] pixels;

    private ByteBuffer buffer = null;
    private OutputStream outputStream;

    private void encode(Bitmap bitmap, String filePath) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
            encode(bitmap, fileOutputStream);
        }
    }

    private void encode(Bitmap bitmap, OutputStream outputStream) throws IOException {
        this.outputStream = outputStream;
        encode(bitmap);
    }

    private void encode(Bitmap bitmap) throws IOException {
        prepareBitmapData(bitmap);
        writeFileHeader();
        writeInfoHeader();
        writePixelData();
        outputStream.write(buffer.array());
    }

    private void prepareBitmapData(Bitmap bitmap) {
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();

        pixels = new int[bitmapWidth * bitmapHeight];
        bitmap.getPixels(pixels, 0, bitmapWidth, 0, 0, bitmapWidth, bitmapHeight);

        int rowPaddingBytes = (4 - ((bitmapWidth * 3) % 4)) * bitmapHeight;
        pixelDataSize = (bitmapWidth * bitmapHeight * 3) + rowPaddingBytes;
        bmpFileSize = pixelDataSize + BMP_FILE_HEADER_SIZE + BMP_INFO_HEADER_SIZE;

        buffer = ByteBuffer.allocate(bmpFileSize);
        width = bitmapWidth;
        height = bitmapHeight;
    }

    private void writePixelData() {
        int lastPixelIndex = (width * height) - 1;
        int rowPaddingBytes = 4 - ((width * 3) % 4);
        if (rowPaddingBytes == 4) {
            rowPaddingBytes = 0;
        }

        int pixelsInCurrentRow = 1;
        int totalPaddingBytes = 0;
        int currentRowStartIndex = lastPixelIndex - width;
        int currentPixelIndex = currentRowStartIndex;

        byte[] bgr = new byte[3];

        for (int i = 0; i < lastPixelIndex; i++) {
            int color = pixels[currentPixelIndex];
            bgr[0] = (byte) (color & 0xFF);
            bgr[1] = (byte) ((color >> 8) & 0xFF);
            bgr[2] = (byte) ((color >> 16) & 0xFF);
            buffer.put(bgr);

            if (pixelsInCurrentRow == width) {
                totalPaddingBytes += rowPaddingBytes;
                for (int paddingIndex = 0; paddingIndex < rowPaddingBytes; paddingIndex++) {
                    buffer.put((byte) 0x00);
                }
                pixelsInCurrentRow = 1;
                currentRowStartIndex -= width;
                currentPixelIndex = currentRowStartIndex;
            } else {
                pixelsInCurrentRow++;
                currentPixelIndex++;
            }
        }

        bmpFileSize += totalPaddingBytes - rowPaddingBytes;
        pixelDataSize += totalPaddingBytes - rowPaddingBytes;
    }

    private void writeFileHeader() {
        buffer.put(bmpSignature);
        buffer.put(intToLittleEndian4(bmpFileSize));
        buffer.put(intToLittleEndian4(reserved));
        buffer.put(intToLittleEndian4(pixelDataOffset));
    }

    private void writeInfoHeader() {
        buffer.put(intToLittleEndian4(infoHeaderSize));
        buffer.put(intToLittleEndian4(width));
        buffer.put(intToLittleEndian4(height));
        buffer.put(intToLittleEndian2(colorPlanes));
        buffer.put(intToLittleEndian2(bitsPerPixel));
        buffer.put(intToLittleEndian4(compression));
        buffer.put(intToLittleEndian4(pixelDataSize));
        buffer.put(intToLittleEndian4(horizontalResolution));
        buffer.put(intToLittleEndian4(verticalResolution));
        buffer.put(intToLittleEndian4(totalColors));
        buffer.put(intToLittleEndian4(importantColors));
    }

    private byte[] intToLittleEndian2(int value) {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) (value & 0x00FF);
        bytes[1] = (byte) ((value >> 8) & 0x00FF);
        return bytes;
    }

    private byte[] intToLittleEndian4(int value) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (value & 0x00FF);
        bytes[1] = (byte) ((value >> 8) & 0x000000FF);
        bytes[2] = (byte) ((value >> 16) & 0x000000FF);
        bytes[3] = (byte) ((value >> 24) & 0x000000FF);
        return bytes;
    }

    static void saveBitmapAsBmp(Bitmap bitmap, File file) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            new BmpEncoder().encode(bitmap, fileOutputStream);
        }
    }
}