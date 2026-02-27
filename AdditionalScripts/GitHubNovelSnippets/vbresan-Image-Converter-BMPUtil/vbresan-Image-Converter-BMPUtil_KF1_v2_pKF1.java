package biz.binarysolutions.imageconverter.image;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

@SuppressWarnings("FieldCanBeLocal")
class BmpEncoder {

    private static final int FILE_HEADER_SIZE = 14;
    private static final int INFO_HEADER_SIZE = 40;

    private final byte[] bmpSignature = {'B', 'M'};
    private int bmpFileSize = 0;
    private int reservedField = 0;
    private int pixelDataOffset = FILE_HEADER_SIZE + INFO_HEADER_SIZE;

    private int infoHeaderSize = INFO_HEADER_SIZE;
    private int bmpWidth = 0;
    private int bmpHeight = 0;
    private int colorPlanes = 1;
    private int bitsPerPixel = 24;
    private int compressionMethod = 0;
    private int pixelDataSize = 0x030000;
    private int horizontalResolution = 0x0;
    private int verticalResolution = 0x0;
    private int totalColors = 0;
    private int importantColors = 0;

    private int[] pixelArray;

    private ByteBuffer byteBuffer = null;
    private OutputStream outputStream;

    private void encode(Bitmap bitmap, String filePath) throws IOException {
        outputStream = new FileOutputStream(filePath);
        encode(bitmap);
        outputStream.close();
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
        outputStream.write(byteBuffer.array());
    }

    private void prepareBitmapData(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        pixelArray = new int[width * height];
        bitmap.getPixels(pixelArray, 0, width, 0, 0, width, height);

        int rowPaddingBytes = (4 - ((width * 3) % 4)) * height;
        pixelDataSize = (width * height * 3) + rowPaddingBytes;
        bmpFileSize = pixelDataSize + FILE_HEADER_SIZE + INFO_HEADER_SIZE;

        byteBuffer = ByteBuffer.allocate(bmpFileSize);
        bmpWidth = width;
        bmpHeight = height;
    }

    private void writePixelData() {
        int lastPixelIndex = (bmpWidth * bmpHeight) - 1;
        int rowPaddingBytes = 4 - ((bmpWidth * 3) % 4);
        if (rowPaddingBytes == 4) {
            rowPaddingBytes = 0;
        }

        int pixelsInCurrentRow = 1;
        int totalPaddingBytes = 0;
        int currentRowStartIndex = lastPixelIndex - bmpWidth;
        int currentPixelIndex = currentRowStartIndex;

        byte[] rgbBytes = new byte[3];

        for (int i = 0; i < lastPixelIndex; i++) {
            int color = pixelArray[currentPixelIndex];
            rgbBytes[0] = (byte) (color & 0xFF);
            rgbBytes[1] = (byte) ((color >> 8) & 0xFF);
            rgbBytes[2] = (byte) ((color >> 16) & 0xFF);
            byteBuffer.put(rgbBytes);

            if (pixelsInCurrentRow == bmpWidth) {
                totalPaddingBytes += rowPaddingBytes;
                for (int paddingIndex = 0; paddingIndex < rowPaddingBytes; paddingIndex++) {
                    byteBuffer.put((byte) 0x00);
                }
                pixelsInCurrentRow = 1;
                currentRowStartIndex -= bmpWidth;
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
        byteBuffer.put(bmpSignature);
        byteBuffer.put(intToLittleEndian4(bmpFileSize));
        byteBuffer.put(intToLittleEndian2(reservedField));
        byteBuffer.put(intToLittleEndian2(reservedField));
        byteBuffer.put(intToLittleEndian4(pixelDataOffset));
    }

    private void writeInfoHeader() {
        byteBuffer.put(intToLittleEndian4(infoHeaderSize));
        byteBuffer.put(intToLittleEndian4(bmpWidth));
        byteBuffer.put(intToLittleEndian4(bmpHeight));
        byteBuffer.put(intToLittleEndian2(colorPlanes));
        byteBuffer.put(intToLittleEndian2(bitsPerPixel));
        byteBuffer.put(intToLittleEndian4(compressionMethod));
        byteBuffer.put(intToLittleEndian4(pixelDataSize));
        byteBuffer.put(intToLittleEndian4(horizontalResolution));
        byteBuffer.put(intToLittleEndian4(verticalResolution));
        byteBuffer.put(intToLittleEndian4(totalColors));
        byteBuffer.put(intToLittleEndian4(importantColors));
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
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        new BmpEncoder().encode(bitmap, fileOutputStream);
        fileOutputStream.close();
    }
}