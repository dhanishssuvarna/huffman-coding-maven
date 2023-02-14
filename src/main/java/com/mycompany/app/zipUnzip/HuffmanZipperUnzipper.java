package com.mycompany.app.zipUnzip;

import com.mycompany.app.FileIO.*;
import com.mycompany.app.compress.IHuffmanCompression;
import com.mycompany.app.compress.HuffmanCompression;
import com.mycompany.app.decompress.IHuffmanDecompression;
import com.mycompany.app.decompress.HuffmanDecompression;
import com.mycompany.app.treeNode.Node;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class HuffmanZipperUnzipper implements IZipperUnzipper{

    @Override
    public String compress(String originalFile) {
        String compressedFile = "compress.txt";

        File file = new File(originalFile);
        if((!file.exists()) || (file.length() == 0)){
            throw new RuntimeException("Enter a Valid file Name!!");
        }

        IFileRead fileReadObj = new FileRead();
        StringBuilder s = fileReadObj.readComp(originalFile);

        IHuffmanCompression hc = new HuffmanCompression();
        Map<Character, Integer> mp = hc.generateCharFreq(s);
        Node root = hc.generateTree(mp);
        Map<Character, String> table = new HashMap<>();
        hc.getTable(root, table, "");
        // w_avg
        double avg = hc.getWAvg(mp, table);
        System.out.println("Average : "+avg);
        // w_avg
        StringBuilder bitStr = hc.getBitString(table, s.toString());
        int paddedZeros = hc.padBitString(bitStr);
        byte[] byteArray = hc.getCompressedByteArray(bitStr.toString());

        IFileWrite fileWriteObj = new FileWrite();
        fileWriteObj.write(compressedFile, root, paddedZeros, byteArray);

        return compressedFile;
    }

    @Override
    public String decompress(String compressedFile) {
        String decompressedFile = "decompress.txt";

        File file = new File(compressedFile);
        if((!file.exists()) || (file.length() == 0)){
            throw new RuntimeException("Enter a Valid file Name!!");
        }

        IFileRead fileReadObj = new FileRead();
        ComplexReturnType crt = fileReadObj.readDecomp(compressedFile);
        Node root = crt.getRoot();
        int paddedZeros = crt.getPaddedZeros();
        byte[] compressedString = crt.getByteArray();

        IHuffmanDecompression hd = new HuffmanDecompression();
        StringBuilder bitStr = hd.getBitString(compressedString);
        StringBuilder decompressedStr = hd.getDecompressedString(root, paddedZeros, bitStr.toString());

        IFileWrite fileWriteObj = new FileWrite();
        fileWriteObj.write(decompressedFile, decompressedStr);

        return decompressedFile;
    }
}
