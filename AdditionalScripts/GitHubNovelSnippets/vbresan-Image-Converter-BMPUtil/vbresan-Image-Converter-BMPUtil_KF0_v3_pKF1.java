package biz.binarysolutions.imageconverter.image;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

@SuppressWarnings("FieldCanBeLocal")
class BMPUtil {

    private static final int BITMAP_FILE_HEADER_SIZE = 14;
    private static final int BITMAP_INFO_HEADER_SIZE = 40;

    // Bitmap file header
    private final byte[] fileHeaderSignature = {'B', 'M'};
    private int fileSizeBytes = 0;
    private final int reservedField1 = 0;
    private final int reservedField2 = 0;
    private final int pixelDataOffset = BITMAP_FILE_HEADER_SIZE + BITMAP_INFO_HEADER_SIZE;

    // Bitmap info header
    private final int infoHeaderSizeBytes = BITMAP_INFO_HEADER_SIZE;
    private int imageWidth = 0;
    private int imageHeight = 0;
    private final int colorPlaneCount = 1;
    private final int bitsPerPixel = 24;
    private final int compressionMethod = 0;
    private int pixelDataSizeBytes = 0x030000;
    private final int horizontalResolutionPixelsPerMeter = 0x0;
    private final int verticalResolutionPixelsPerMeter = 0x0;
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
        writeBitmapFileHeader();
        writeBitmapInfoHeader();
        writeBitmapPixelArray();
        outputStream.write(buffer.array());
    }

    private void prepareBitmapData(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        argbPixels = new int[width * height];
        bitmap.getPixels(argbPixels, 0, width, 0, 0, width, height);

        int rowPaddingBytes = (4 - ((width * 3) % 4)) * height;
        pixelDataSizeBytes = (width * height * 3) + rowPaddingBytes;
        fileSizeBytes = pixelDataSizeBytes + BITMAP_FILE_HEADER_SIZE + BITMAP_INFO_HEADER_SIZE;

        buffer = ByteBuffer.allocate(fileSizeBytes);
        imageWidth = width;
        imageHeight = height;
    }

    private void writeBitmapPixelArray() {
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

        fileSizeBytes += totalPaddingBytesWritten - paddingBytesPerRow;
        pixelDataSizeBytes += totalPaddingBytesWritten - paddingBytesPerRow;
    }

    private void writeBitmapFileHeader() {
        buffer.put(fileHeaderSignature);
        buffer.put(intToDWord(fileSizeBytes));
        buffer.put(intToWord(reservedField1));
        buffer.put(intToWord(reservedField2));
        buffer.put(intToDWord(pixelDataOffset));
    }

    private void writeBitmapInfoHeader() {
        buffer.put(intToDWord(infoHeaderSizeBytes));
        buffer.put(intToDWord(imageWidth));
        buffer.put(intToDWord(imageHeight));
        buffer.put(intToWord(colorPlaneCount));
        buffer.put(intToWord(bitsPerPixel));
        buffer.put(intToDWord(compressionMethod));
        buffer.put(intToDWord(pixelDataSizeBytes));
        buffer.put(intToDWord(horizontalResolutionPixelsPerMeter));
        buffer.put(intToDWord(verticalResolutionPixelsPerMeter));
        buffer.put(intToDWord(colorPaletteSize));
        buffer.put(intToDWord(importantColorCount));
    }

    private byte[] intToWord(int value) {
        byte[] word = new byte[2];
        word[0] = (byte) (value & 0x00FF);
        word[1] = (byte) ((value >> 8) & 0x00FF);
        return word;
    }

    private byte[] intToDWord(int value) {
        byte[] dword = new byte[4];
        dword[0] = (byte) (value & 0x00FF);
        dword[1] = (byte) ((value >> 8) & 0x000000FF);
        dword[2] = (byte) ((value >> 16) & 0x000000FF);
        dword[3] = (byte) ((value >> 24) & 0x000000FF);
        return dword;
    }

    static void encodeToBMP(Bitmap bitmap, File outputFile) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
        new BMPUtil().saveBitmap(bitmap, fileOutputStream);
        fileOutputStream.close();
    }
}