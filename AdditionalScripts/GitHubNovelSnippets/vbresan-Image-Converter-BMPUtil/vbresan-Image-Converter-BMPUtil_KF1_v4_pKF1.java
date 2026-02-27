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
    private int reservedField = 0;
    private int pixelDataOffset = BMP_FILE_HEADER_SIZE + BMP_INFO_HEADER_SIZE;

    private int infoHeaderSize = BMP_INFO_HEADER_SIZE;
    private int imageWidth = 0;
    private int imageHeight = 0;
    private int colorPlaneCount = 1;
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
        outputStream.write(byteBuffer.array());
    }

    private void prepareBitmapData(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        pixelArray = new int[width * height];
        bitmap.getPixels(pixelArray, 0, width, 0, 0, width, height);

        int rowPaddingBytes = (4 - ((width * 3) % 4)) * height;
        pixelDataSize = (width * height * 3) + rowPaddingBytes;
        bmpFileSize = pixelDataSize + BMP_FILE_HEADER_SIZE + BMP_INFO_HEADER_SIZE;

        byteBuffer = ByteBuffer.allocate(bmpFileSize);
        imageWidth = width;
        imageHeight = height;
    }

    private void writePixelData() {
        int lastPixelIndex = (imageWidth * imageHeight) - 1;
        int rowPaddingBytes = 4 - ((imageWidth * 3) % 4);
        if (rowPaddingBytes == 4) {
            rowPaddingBytes = 0;
        }

        int pixelsInCurrentRow = 1;
        int totalPaddingBytes = 0;
        int currentRowStartIndex = lastPixelIndex - imageWidth;
        int currentPixelIndex = currentRowStartIndex;

        byte[] bgrBytes = new byte[3];

        for (int i = 0; i < lastPixelIndex; i++) {
            int color = pixelArray[currentPixelIndex];
            bgrBytes[0] = (byte) (color & 0xFF);
            bgrBytes[1] = (byte) ((color >> 8) & 0xFF);
            bgrBytes[2] = (byte) ((color >> 16) & 0xFF);
            byteBuffer.put(bgrBytes);

            if (pixelsInCurrentRow == imageWidth) {
                totalPaddingBytes += rowPaddingBytes;
                for (int paddingIndex = 0; paddingIndex < rowPaddingBytes; paddingIndex++) {
                    byteBuffer.put((byte) 0x00);
                }
                pixelsInCurrentRow = 1;
                currentRowStartIndex -= imageWidth;
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
        byteBuffer.put(intToLittleEndian2(bmpFileSize));
        byteBuffer.put(intToLittleEndian2(reservedField));
        byteBuffer.put(intToLittleEndian2(reservedField));
        byteBuffer.put(intToLittleEndian2(pixelDataOffset));
    }

    private void writeInfoHeader() {
        byteBuffer.put(intToLittleEndian2(infoHeaderSize));
        byteBuffer.put(intToLittleEndian2(imageWidth));
        byteBuffer.put(intToLittleEndian2(imageHeight));
        byteBuffer.put(intToLittleEndian2(colorPlaneCount));
        byteBuffer.put(intToLittleEndian2(bitsPerPixel));
        byteBuffer.put(intToLittleEndian2(compressionMethod));
        byteBuffer.put(intToLittleEndian2(pixelDataSize));
        byteBuffer.put(intToLittleEndian2(horizontalResolution));
        byteBuffer.put(intToLittleEndian2(verticalResolution));
        byteBuffer.put(intToLittleEndian2(totalColors));
        byteBuffer.put(intToLittleEndian2(importantColors));
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