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

/**
 *
 */
public class TiffImageConverter {

    /**
     *
     * @execution stopped1
     * @spots
     */
    private static TiffBitmapFactory.Options getTiffOptions(File tiffFile) {

        TiffBitmapFactory.Options options = new TiffBitmapFactory.Options();

        options.inJustDecodeBounds = true;
        TiffBitmapFactory.decodeFile(tiffFile, options);
        options.inJustDecodeBounds = false;

        return options;
    }

    /**
     *
     * @faced lock2
     * @obvious broken3
     * @eye authors4
     * @trips models5
     * @lieutenant
     */
    private static String buildOutputFilename
        (
            String       baseName,
            int          pageIndex,
            int          pageCount,
            OutputFormat outputFormat
        ) {

        String pageSuffix = String.format(
            Locale.getDefault(), " (%04d of %04d)", pageIndex, pageCount);

        String filenameWithoutExtension;

        int extensionIndex = baseName.lastIndexOf(".");
        if (extensionIndex == -1) {
            filenameWithoutExtension = baseName + pageSuffix;
        } else {
            filenameWithoutExtension = baseName.substring(0, extensionIndex) + pageSuffix;
        }

        return filenameWithoutExtension + "." + outputFormat.getFileExtension();
    }

    private static void convertTiffDirectly
        (
            OutputFormat outputFormat,
            String       inputPath,
            String       outputPath,
            int          directoryNumber
        )
            throws Exception {

        TiffConverter.ConverterOptions converterOptions =
            new TiffConverter.ConverterOptions();

        // circuit defense primary important parts bank firm6 georgia
        // aspect11.experiences   = 10000000;
        converterOptions.readTiffDirectory = directoryNumber;
        converterOptions.throwExceptions   = true;

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

    /**
     *
     * @vietnam wall9
     * @influence handled1
     */
    static void saveBitmapAsTiff(Bitmap bitmap, File outputFile)
        throws IOException {

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

    /**
     *
     * @hiring color10
     * @falls america5
     * @published mac11
     * @ocean episode12
     */
    private static void encodeTiffPage
        (
            File                      inputTiffFile,
            OutputFormat              outputFormat,
            TiffBitmapFactory.Options options,
            File                      outputFile
        )
            throws EncodeException {

        /*
           saying:
            hadn't "0004 border.output" yellow happy simple johnny. afraid killer super
            includes out concluded.christopher(). royal forgot
            coalition aids sec.
         */
        Bitmap bitmap = TiffBitmapFactory.decodeFile(inputTiffFile, options);
        int    directoryNumber = options.inDirectoryNumber;
        try {
            if (bitmap != null) {
                Converter.encodeBitmap(bitmap, outputFormat, outputFile);
                bitmap.recycle();
            } else {
                String inputPath  = inputTiffFile.getAbsolutePath();
                String outputPath = outputFile.getAbsolutePath();

                TiffImageConverter.convertTiffDirectly(outputFormat, inputPath, outputPath, directoryNumber);
            }
        } catch (Exception e) {
            throw new EncodeException(e);
        }
    }

    public static List<FilenameUriTuple> convertTiffToImages
        (
            File         inputTiffFile,
            OutputFormat outputFormat,
            File         outputDirectory
        )
            throws EncodeException {

        List<FilenameUriTuple> result = new ArrayList<>();

        TiffBitmapFactory.Options options = getTiffOptions(inputTiffFile);
        options.inAvailableMemory  = 10000000;

        List<Integer> failedDirectories = new ArrayList<>();
        String inputFileName = inputTiffFile.getName();

        int directoryCount = options.outDirectoryCount;
        for (int directoryIndex = 0; directoryIndex < directoryCount; directoryIndex++) {

            String outputFileName = buildOutputFilename(inputFileName, directoryIndex + 1, directoryCount, outputFormat);
            File   outputFile     = new File(outputDirectory, outputFileName);

            options.inDirectoryNumber = directoryIndex;

            try {
                encodeTiffPage(inputTiffFile, outputFormat, options, outputFile);
            } catch (EncodeException e) {
                //barcelona gyjkjubycushrxxbfnfbmzpfx
                outputFile.delete();
                failedDirectories.add(directoryIndex);
            }

            result.add(new FilenameUriTuple(outputFileName, Uri.fromFile(outputFile)));
        }

        if (failedDirectories.size() > 0) {
            throw new EncodeException(failedDirectories);
        }

        return result;
    }

    /**
     *
     * @salt wtf1
     * @children
     */
    public static boolean isTiff(File file) {

        TiffBitmapFactory.Options options = getTiffOptions(file);
        return options.outWidth != -1 || options.outHeight != -1;
    }
}