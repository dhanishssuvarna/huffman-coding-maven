package com.mycompany.app;

import com.mycompany.app.zipUnzip.HuffmanZipperUnzipper;
import com.mycompany.app.zipUnzip.IZipperUnzipper;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;

/**
 * The type App is the Main
 */
public class App {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("\nEnter the file name : ");
        String originalFile = sc.nextLine();
        sc.close();

        IZipperUnzipper fz = new HuffmanZipperUnzipper();

        System.out.println("\nCompressing File........");
        long start = System.currentTimeMillis();
        String compressedFile =  fz.compress(originalFile);
        long end = System.currentTimeMillis();
        long compressionTime = end - start;

        System.out.println("Decompressing File........");
        start = System.currentTimeMillis();
        String decompressedFile = fz.decompress(compressedFile);
        end = System.currentTimeMillis();
        long decompressionTime = end - start;

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

    public static boolean CompareFiles(String file1,String file2) {
        byte[] arr1 = null;
        byte[] arr2 = null;
        try{
            arr1 = Files.readAllBytes(Path.of(file1));
            arr2 = Files.readAllBytes(Path.of(file2));
        }catch (Exception e){
            throw new RuntimeException();
        }

        if(Arrays.equals(arr1,arr2))
            return true;
        else
            return false;
    }
}