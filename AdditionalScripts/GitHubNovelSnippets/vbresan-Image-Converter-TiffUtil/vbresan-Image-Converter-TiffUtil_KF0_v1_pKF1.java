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

public class TiffUtil {

    private static TiffBitmapFactory.Options createDecodeOptions(File tiffFile) {
        TiffBitmapFactory.Options decodeOptions = new TiffBitmapFactory.Options();
        decodeOptions.inJustDecodeBounds = true;
        TiffBitmapFactory.decodeFile(tiffFile, decodeOptions);
        decodeOptions.inJustDecodeBounds = false;
        return decodeOptions;
    }

    private static String buildOutputFilename(
            String originalFilename,
            int currentPage,
            int totalPages,
            OutputFormat outputFormat
    ) {
        String pageSuffix = String.format(
                Locale.getDefault(),
                " (%04d of %04d)",
                currentPage,
                totalPages
        );

        int extensionIndex = originalFilename.lastIndexOf(".");
        String baseName = (extensionIndex == -1)
                ? originalFilename + pageSuffix
                : originalFilename.substring(0, extensionIndex) + pageSuffix;

        return baseName + "." + outputFormat.getFileExtension();
    }

    private static void convertTiffPageDirectly(
            OutputFormat outputFormat,
            String inputPath,
            String outputPath,
            int directoryIndex
    ) throws Exception {

        TiffConverter.ConverterOptions converterOptions = new TiffConverter.ConverterOptions();
        // Limiting memory can cause a failure in conversion
        // converterOptions.availableMemory = 10000000;
        converterOptions.readTiffDirectory = directoryIndex;
        converterOptions.throwExceptions = true;

        if (outputFormat == OutputFormat.BMP) {
            TiffConverter.convertTiffBmp(inputPath, outputPath, converterOptions, null);
        } else if (outputFormat == OutputFormat.JPG) {
            TiffConverter.convertTiffJpg(inputPath, outputPath, converterOptions, null);
        } else if (outputFormat == OutputFormat.PNG) {
            TiffConverter.convertTiffPng(inputPath, outputPath, converterOptions, null);
        } else {
            throw new Exception("Unsupported output format for direct conversion.");
        }
    }

    static void encodeToTiff(Bitmap bitmap, File outputFile) throws IOException {
        TiffSaver.SaveOptions saveOptions = new TiffSaver.SaveOptions();
        saveOptions.inThrowException = true;

        try {
            TiffSaver.saveBitmap(outputFile, bitmap, saveOptions);
        } catch (Exception e) {
            IOException wrappedException = new IOException(e.toString());
            wrappedException.setStackTrace(e.getStackTrace());
            throw wrappedException;
        }
    }

    private static void convertSingleTiffPage(
            File inputTiffFile,
            OutputFormat outputFormat,
            TiffBitmapFactory.Options decodeOptions,
            File outputFile
    ) throws EncodeException {

        /*
           TODO:
            For "0004 TEST.tif" this call returns null. It is calling
            native method NativeDecoder.getBitmap(). Continue debugging
            explorations from there.
         */
        Bitmap bitmap = TiffBitmapFactory.decodeFile(inputTiffFile, decodeOptions);
        int directoryIndex = decodeOptions.inDirectoryNumber;

        try {
            if (bitmap != null) {
                Converter.encodeBitmap(bitmap, outputFormat, outputFile);
                bitmap.recycle();
            } else {
                String inputPath = inputTiffFile.getAbsolutePath();
                String outputPath = outputFile.getAbsolutePath();
                convertTiffPageDirectly(outputFormat, inputPath, outputPath, directoryIndex);
            }
        } catch (Exception e) {
            throw new EncodeException(e);
        }
    }

    public static List<FilenameUriTuple> convertFromTiff(
            File inputTiffFile,
            OutputFormat outputFormat,
            File outputFolder
    ) throws EncodeException {

        List<FilenameUriTuple> outputFiles = new ArrayList<>();

        TiffBitmapFactory.Options decodeOptions = createDecodeOptions(inputTiffFile);
        decodeOptions.inAvailableMemory = 10000000;

        List<Integer> failedDirectories = new ArrayList<>();
        String inputFilename = inputTiffFile.getName();

        int directoryCount = decodeOptions.outDirectoryCount;
        for (int directoryIndex = 0; directoryIndex < directoryCount; directoryIndex++) {

            String outputFilename = buildOutputFilename(
                    inputFilename,
                    directoryIndex + 1,
                    directoryCount,
                    outputFormat
            );
            File outputFile = new File(outputFolder, outputFilename);

            decodeOptions.inDirectoryNumber = directoryIndex;

            try {
                convertSingleTiffPage(inputTiffFile, outputFormat, decodeOptions, outputFile);
            } catch (EncodeException e) {
                //noinspection ResultOfMethodCallIgnored
                outputFile.delete();
                failedDirectories.add(directoryIndex);
            }

            outputFiles.add(new FilenameUriTuple(outputFilename, Uri.fromFile(outputFile)));
        }

        if (!failedDirectories.isEmpty()) {
            throw new EncodeException(failedDirectories);
        }

        return outputFiles;
    }

    public static boolean isTiffFile(File file) {
        TiffBitmapFactory.Options decodeOptions = createDecodeOptions(file);
        return decodeOptions.outWidth != -1 || decodeOptions.outHeight != -1;
    }
}