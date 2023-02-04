package com.mycompany.app.stats;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;

/**
 * The type Check compression.
 */
public class CheckCompression {
    /**
     * Get byte array byte [ ].
     *
     * @param filename the filename
     * @return the byte [ ]
     */
    public byte[] getByteArray(String filename){
        byte[] arr;
        try {
            File file = new File(filename);
            FileInputStream input = new FileInputStream(file);
            arr = new byte[(int) file.length()];
            input.read(arr);
            input.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return arr;
    }

    /**
     * Compare files boolean.
     *
     * @param file1 the file 1
     * @param file2 the file 2
     * @return the boolean
     */
    public boolean CompareFiles(String file1,String file2){
        byte[] arr1 = getByteArray(file1);
        byte[] arr2 = getByteArray(file2);

        if(Arrays.equals(arr1,arr2))
            return true;
        else
            return false;
    }

    /**
     * Get stats.
     *
     * @param originalFile      the original file name
     * @param compressedFile    the compressed file name
     * @param decompressedFile  the decompressed file name
     * @param compressionTime   the compression time
     * @param decompressionTime the decompression time
     */
    public void getStats(String originalFile, String compressedFile, String decompressedFile, long compressionTime, long decompressionTime){
        boolean isEqual = CompareFiles(originalFile, decompressedFile);

        if(isEqual){
            File of = new File(originalFile);
            long ofl = of.length();

            File cf = new File(compressedFile);
            long cfl = cf.length();

            File df = new File(decompressedFile);
            long dfl = df.length();

            System.out.println("\n********** Operation Successful **********\n");
            System.out.println("Compress Time : " + compressionTime + " ms");
            System.out.println("Decompress Time : " + decompressionTime + " ms");
            System.out.println("Original File Size : " + (float) ofl/(1024*1024) + " MB");
            System.out.println("Compressed File Size : " + (float) cfl/(1024*1024) + " MB");
            System.out.println("Decompressed File Size : " + (float) dfl/(1024*1024) + " MB");
            System.out.println("Compression Ratio : " + ((float)(ofl-cfl)/ofl)*100 + " %");
        }else {
            System.out.println("\n********** Operation Failed **********\n");
        }
    }
}
