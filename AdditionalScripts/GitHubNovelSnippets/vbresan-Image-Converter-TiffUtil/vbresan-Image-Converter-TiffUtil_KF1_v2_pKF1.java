package biz.binarysolutions.imageconverter.image;

import android.graphics.Bitmap;
import android.net.Uri;

import org.beyka.tiffbitmapfactory.TiffBitmapFactory;
import org.beyka.tiffbitmapfactory.TiffConverter;
import org.beyka.tiffbitmapfactory.TiffSaver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import biz.binarysolutions.imageconverter.data.FilenameUriTuple;
import biz.binarysolutions.imageconverter.data.OutputFormat;
import biz.binarysolutions.imageconverter.exceptions.EncodeException;

public class TiffImageConverter {

    private static final int DEFAULT_AVAILABLE_MEMORY = 10_000_000;

    private static TiffBitmapFactory.Options createTiffDecodeOptions(File tiffFile) {
        TiffBitmapFactory.Options decodeOptions = new TiffBitmapFactory.Options();
        decodeOptions.inJustDecodeBounds = true;
        TiffBitmapFactory.decodeFile(tiffFile, decodeOptions);
        decodeOptions.inJustDecodeBounds = false;
        return decodeOptions;
    }

    private static String buildOutputFilename(
            String baseName,
            int pageIndex,
            int pageCount,
            OutputFormat outputFormat
    ) {
        String pageSuffix = String.format(
                Locale.getDefault(),
                " (%04d of %04d)",
                pageIndex,
                pageCount
        );

        String filenameWithoutExtension;
        int extensionIndex = baseName.lastIndexOf(".");
        if (extensionIndex == -1) {
            filenameWithoutExtension = baseName + pageSuffix;
        } else {
            filenameWithoutExtension =
                    baseName.substring(0, extensionIndex) + pageSuffix;
        }

        return filenameWithoutExtension + "." + outputFormat.getFileExtension();
    }

    private static void convertTiffDirectly(
            OutputFormat outputFormat,
            String inputPath,
            String outputPath,
            int directoryIndex
    ) throws Exception {

        TiffConverter.ConverterOptions converterOptions =
                new TiffConverter.ConverterOptions();

        converterOptions.readTiffDirectory = directoryIndex;
        converterOptions.throwExceptions = true;

        switch (outputFormat) {
            case BMP:
                TiffConverter.convertTiffBmp(inputPath, outputPath, converterOptions, null);
                break;
            case JPG:
                TiffConverter.convertTiffJpg(inputPath, outputPath, converterOptions, null);
                break;
            case PNG:
                TiffConverter.convertTiffPng(inputPath, outputPath, converterOptions, null);
                break;
            default:
                throw new Exception("Unsupported output format for direct conversion.");
        }
    }

    static void saveBitmapAsTiff(Bitmap bitmap, File outputFile) throws IOException {
        TiffSaver.SaveOptions saveOptions = new TiffSaver.SaveOptions();
        saveOptions.inThrowException = true;

        try {
            TiffSaver.saveBitmap(outputFile, bitmap, saveOptions);
        } catch (Exception e) {
            IOException ioException = new IOException(e.toString());
            ioException.setStackTrace(e.getStackTrace());
            throw ioException;
        }
    }

    private static void encodeTiffPage(
            File inputTiffFile,
            OutputFormat outputFormat,
            TiffBitmapFactory.Options decodeOptions,
            File outputFile
    ) throws EncodeException {

        Bitmap bitmap = TiffBitmapFactory.decodeFile(inputTiffFile, decodeOptions);
        int directoryIndex = decodeOptions.inDirectoryNumber;

        try {
            if (bitmap != null) {
                Converter.encodeBitmap(bitmap, outputFormat, outputFile);
                bitmap.recycle();
            } else {
                String inputPath = inputTiffFile.getAbsolutePath();
                String outputPath = outputFile.getAbsolutePath();
                convertTiffDirectly(outputFormat, inputPath, outputPath, directoryIndex);
            }
        } catch (Exception e) {
            throw new EncodeException(e);
        }
    }

    public static List<FilenameUriTuple> convertTiffToImages(
            File inputTiffFile,
            OutputFormat outputFormat,
            File outputDirectory
    ) throws EncodeException {

        List<FilenameUriTuple> convertedImages = new ArrayList<>();

        TiffBitmapFactory.Options decodeOptions = createTiffDecodeOptions(inputTiffFile);
        decodeOptions.inAvailableMemory = DEFAULT_AVAILABLE_MEMORY;

        List<Integer> failedDirectoryIndices = new ArrayList<>();
        String inputFileName = inputTiffFile.getName();

        int directoryCount = decodeOptions.outDirectoryCount;
        for (int directoryIndex = 0; directoryIndex < directoryCount; directoryIndex++) {

            String outputFileName = buildOutputFilename(
                    inputFileName,
                    directoryIndex + 1,
                    directoryCount,
                    outputFormat
            );
            File outputFile = new File(outputDirectory, outputFileName);

            decodeOptions.inDirectoryNumber = directoryIndex;

            try {
                encodeTiffPage(inputTiffFile, outputFormat, decodeOptions, outputFile);
            } catch (EncodeException e) {
                outputFile.delete();
                failedDirectoryIndices.add(directoryIndex);
            }

            convertedImages.add(
                    new FilenameUriTuple(outputFileName, Uri.fromFile(outputFile))
            );
        }

        if (!failedDirectoryIndices.isEmpty()) {
            throw new EncodeException(failedDirectoryIndices);
        }

        return convertedImages;
    }

    public static boolean isTiff(File file) {
        TiffBitmapFactory.Options decodeOptions = createTiffDecodeOptions(file);
        return decodeOptions.outWidth != -1 || decodeOptions.outHeight != -1;
    }
}