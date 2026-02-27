package biz.binarysolutions.imageconverter.image;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

@SuppressWarnings("FieldCanBeLocal")
class BMPUtil {

    private static final int FILE_HEADER_SIZE = 14;
    private static final int INFO_HEADER_SIZE = 40;

    // Bitmap file header
    private final byte[] fileSignature = {'B', 'M'};
    private int fileSize = 0;
    private final int reservedField1 = 0;
    private final int reservedField2 = 0;
    private final int pixelDataOffset = FILE_HEADER_SIZE + INFO_HEADER_SIZE;

    // Bitmap info header
    private final int infoHeaderSize = INFO_HEADER_SIZE;
    private int imageWidth = 0;
    private int imageHeight = 0;
    private final int colorPlaneCount = 1;
    private final int bitsPerPixel = 24;
    private final int compressionMethod = 0;
    private int pixelDataSize = 0x030000;
    private final int horizontalResolution = 0x0;
    private final int verticalResolution = 0x0;
    private final int colorPaletteSize = 0;
    private final int importantColorCount = 0;

    // Bitmap raw data
    private int[] argbPixels;

    // File section
    private ByteBuffer buffer = null;
    private OutputStream outputStream;

    private void saveBitmap(Bitmap bitmap, String filePath) throws IOException {
        outputStream = new FileOutputStream(filePath);
        save(bitmap);
        outputStream.close();
    }

    private void saveBitmap(Bitmap bitmap, OutputStream outputStream) throws IOException {
        this.outputStream = outputStream;
        save(bitmap);
    }

    private void save(Bitmap bitmap) throws IOException {
        prepareBitmapData(bitmap);
        writeFileHeader();
        writeInfoHeader();
        writePixelData();
        outputStream.write(buffer.array());
    }

    private void prepareBitmapData(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        argbPixels = new int[width * height];
        bitmap.getPixels(argbPixels, 0, width, 0, 0, width, height);

        int rowPaddingBytes = (4 - ((width * 3) % 4)) * height;
        pixelDataSize = (width * height * 3) + rowPaddingBytes;
        fileSize = pixelDataSize + FILE_HEADER_SIZE + INFO_HEADER_SIZE;

        buffer = ByteBuffer.allocate(fileSize);
        imageWidth = width;
        imageHeight = height;
    }

    private void writePixelData() {
        int lastPixelIndex = (imageWidth * imageHeight) - 1;
        int paddingBytesPerRow = 4 - ((imageWidth * 3) % 4);
        if (paddingBytesPerRow == 4) {
            paddingBytesPerRow = 0;
        }

        int pixelsWrittenInCurrentRow = 1;
        int totalPaddingBytesWritten = 0;
        int currentRowStartIndex = lastPixelIndex - imageWidth;
        int previousRowStartIndex = currentRowStartIndex;

        byte[] bgrPixelBytes = new byte[3];

        for (int pixelIndex = 0; pixelIndex < lastPixelIndex; pixelIndex++) {
            int argbPixel = argbPixels[currentRowStartIndex];

            bgrPixelBytes[0] = (byte) (argbPixel & 0xFF);         // Blue
            bgrPixelBytes[1] = (byte) ((argbPixel >> 8) & 0xFF);  // Green
            bgrPixelBytes[2] = (byte) ((argbPixel >> 16) & 0xFF); // Red

            buffer.put(bgrPixelBytes);

            if (pixelsWrittenInCurrentRow == imageWidth) {
                totalPaddingBytesWritten += paddingBytesPerRow;
                for (int paddingIndex = 0; paddingIndex < paddingBytesPerRow; paddingIndex++) {
                    buffer.put((byte) 0x00);
                }
                pixelsWrittenInCurrentRow = 1;
                currentRowStartIndex = previousRowStartIndex - imageWidth;
                previousRowStartIndex = currentRowStartIndex;
            } else {
                pixelsWrittenInCurrentRow++;
            }
            currentRowStartIndex++;
        }

        fileSize += totalPaddingBytesWritten - paddingBytesPerRow;
        pixelDataSize += totalPaddingBytesWritten - paddingBytesPerRow;
    }

    private void writeFileHeader() {
        buffer.put(fileSignature);
        buffer.put(intToDWord(fileSize));
        buffer.put(intToWord(reservedField1));
        buffer.put(intToWord(reservedField2));
        buffer.put(intToDWord(pixelDataOffset));
    }

    private void writeInfoHeader() {
        buffer.put(intToDWord(infoHeaderSize));
        buffer.put(intToDWord(imageWidth));
        buffer.put(intToDWord(imageHeight));
        buffer.put(intToWord(colorPlaneCount));
        buffer.put(intToWord(bitsPerPixel));
        buffer.put(intToDWord(compressionMethod));
        buffer.put(intToDWord(pixelDataSize));
        buffer.put(intToDWord(horizontalResolution));
        buffer.put(intToDWord(verticalResolution));
        buffer.put(intToDWord(colorPaletteSize));
        buffer.put(intToDWord(importantColorCount));
    }

    private byte[] intToWord(int value) {
        byte[] wordBytes = new byte[2];
        wordBytes[0] = (byte) (value & 0x00FF);
        wordBytes[1] = (byte) ((value >> 8) & 0x00FF);
        return wordBytes;
    }

    private byte[] intToDWord(int value) {
        byte[] dwordBytes = new byte[4];
        dwordBytes[0] = (byte) (value & 0x00FF);
        dwordBytes[1] = (byte) ((value >> 8) & 0x000000FF);
        dwordBytes[2] = (byte) ((value >> 16) & 0x000000FF);
        dwordBytes[3] = (byte) ((value >> 24) & 0x000000FF);
        return dwordBytes;
    }

    static void encodeToBMP(Bitmap bitmap, File outputFile) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
        new BMPUtil().saveBitmap(bitmap, fileOutputStream);
        fileOutputStream.close();
    }
}