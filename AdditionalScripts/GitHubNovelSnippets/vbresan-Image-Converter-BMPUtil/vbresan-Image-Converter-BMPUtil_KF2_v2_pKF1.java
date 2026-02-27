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

    // BITMAPFILEHEADER fields
    private final byte[] bitmapSignature = {'B', 'M'};
    private int bitmapFileSize = 0;
    private int reservedField1 = 0;
    private int reservedField2 = 0;
    private int pixelDataOffset = BITMAP_FILE_HEADER_SIZE + BITMAP_INFO_HEADER_SIZE;

    // BITMAPINFOHEADER fields
    private int infoHeaderSize = BITMAP_INFO_HEADER_SIZE;
    private int bitmapWidth = 0;
    private int bitmapHeight = 0;
    private int colorPlaneCount = 1;
    private int bitsPerPixel = 24;
    private int compressionMethod = 0;
    private int bitmapDataSize = 0x030000;
    private int horizontalResolution = 0x0;
    private int verticalResolution = 0x0;
    private int colorPaletteSize = 0;
    private int importantColorCount = 0;

    private int[] pixelData;

    private ByteBuffer byteBuffer = null;
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
        writePixelArray();
        outputStream.write(byteBuffer.array());
    }

    private void prepareBitmapData(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        pixelData = new int[width * height];
        bitmap.getPixels(pixelData, 0, width, 0, 0, width, height);

        int rowPaddingBytes = (4 - ((width * 3) % 4)) * height;
        bitmapDataSize = (width * height * 3) + rowPaddingBytes;
        bitmapFileSize = bitmapDataSize + BITMAP_FILE_HEADER_SIZE + BITMAP_INFO_HEADER_SIZE;

        byteBuffer = ByteBuffer.allocate(bitmapFileSize);
        bitmapWidth = width;
        bitmapHeight = height;
    }

    private void writePixelArray() {
        int lastPixelIndex = (bitmapWidth * bitmapHeight) - 1;
        int paddingBytesPerRow = 4 - ((bitmapWidth * 3) % 4);
        if (paddingBytesPerRow == 4) {
            paddingBytesPerRow = 0;
        }

        int pixelsWrittenInRow = 1;
        int totalPaddingBytes = 0;
        int currentRowStartIndex = lastPixelIndex - bitmapWidth;
        int previousRowStartIndex = currentRowStartIndex;

        byte[] rgbBytes = new byte[3];

        for (int pixelIndex = 0; pixelIndex < lastPixelIndex; pixelIndex++) {
            int pixel = pixelData[currentRowStartIndex];

            rgbBytes[0] = (byte) (pixel & 0xFF);         // Blue
            rgbBytes[1] = (byte) ((pixel >> 8) & 0xFF);  // Green
            rgbBytes[2] = (byte) ((pixel >> 16) & 0xFF); // Red

            byteBuffer.put(rgbBytes);

            if (pixelsWrittenInRow == bitmapWidth) {
                totalPaddingBytes += paddingBytesPerRow;
                for (int padIndex = 0; padIndex < paddingBytesPerRow; padIndex++) {
                    byteBuffer.put((byte) 0x00);
                }
                pixelsWrittenInRow = 1;
                currentRowStartIndex = previousRowStartIndex - bitmapWidth;
                previousRowStartIndex = currentRowStartIndex;
            } else {
                pixelsWrittenInRow++;
            }
            currentRowStartIndex++;
        }

        bitmapFileSize += totalPaddingBytes - paddingBytesPerRow;
        bitmapDataSize += totalPaddingBytes - paddingBytesPerRow;
    }

    private void writeBitmapFileHeader() {
        byteBuffer.put(bitmapSignature);
        byteBuffer.put(intToDWord(bitmapFileSize));
        byteBuffer.put(intToWord(reservedField1));
        byteBuffer.put(intToWord(reservedField2));
        byteBuffer.put(intToDWord(pixelDataOffset));
    }

    private void writeBitmapInfoHeader() {
        byteBuffer.put(intToDWord(infoHeaderSize));
        byteBuffer.put(intToDWord(bitmapWidth));
        byteBuffer.put(intToDWord(bitmapHeight));
        byteBuffer.put(intToWord(colorPlaneCount));
        byteBuffer.put(intToWord(bitsPerPixel));
        byteBuffer.put(intToDWord(compressionMethod));
        byteBuffer.put(intToDWord(bitmapDataSize));
        byteBuffer.put(intToDWord(horizontalResolution));
        byteBuffer.put(intToDWord(verticalResolution));
        byteBuffer.put(intToDWord(colorPaletteSize));
        byteBuffer.put(intToDWord(importantColorCount));
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
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        new BMPUtil().saveBitmap(bitmap, fileOutputStream);
        fileOutputStream.close();
    }
}