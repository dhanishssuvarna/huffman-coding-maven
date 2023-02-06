package com.mycompany.app;

import com.mycompany.app.stats.CheckCompression;
import com.mycompany.app.compress.HuffmanCompression;
import com.mycompany.app.decompress.HuffmanDecompression;

import java.io.File;
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

        System.out.println("\nCompressing File........");
        HuffmanCompression hc = new HuffmanCompression();
        long start = System.currentTimeMillis();
        String compressedFile =  hc.compress(originalFile);
        long end = System.currentTimeMillis();
        long compressionTime = end - start;

        System.out.println("Decompressing File........");
        HuffmanDecompression hd = new HuffmanDecompression();
        start = System.currentTimeMillis();
        String decompressedFile = hd.decompression(compressedFile);
        end = System.currentTimeMillis();
        long decompressionTime = end - start;

        CheckCompression cc = new CheckCompression();
        cc.getStats(originalFile, compressedFile ,decompressedFile, compressionTime, decompressionTime);
    }
}