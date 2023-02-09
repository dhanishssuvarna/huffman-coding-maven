package com.mycompany.app.zipUnzip;

import com.mycompany.app.compress.Compression;
import com.mycompany.app.compress.HuffmanCompression;
import com.mycompany.app.decompress.Decompression;
import com.mycompany.app.decompress.HuffmanDecompression;
import com.mycompany.app.treeNode.Node;
import com.mycompany.app.treeNode.NodeComparator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanZipperUnzipper implements IZipperUnzipper{


    @Override
    public String compress(String originalFile) {
        String compressedFile = "compress.txt";

        Compression hc = new HuffmanCompression();

        PriorityQueue<Node> pq = new PriorityQueue<>(new NodeComparator());

        StringBuilder s = hc.getContent(originalFile);
        Map<Character, Integer> mp = hc.generateCharFreq(originalFile);

        for(Map.Entry<Character, Integer> e : mp.entrySet()) {
//            System.out.println(e.getKey()+" : "+ e.getValue());
            Node temp = new Node(e.getKey(), e.getValue());
            pq.add(temp);
        }

        Node root = hc.generateTree(pq);

        Map<Character, String> table = new HashMap<>();
        hc.getTable(root, table, "");

        StringBuilder bitStr = hc.getBitString(table, s.toString());
        int paddedZeros = hc.padBitString(bitStr);

        byte[] byteArray = hc.getCompressedByteArray(bitStr.toString());

        hc.WriteIntoFile(compressedFile, root, paddedZeros, byteArray);

        return compressedFile;
    }

    @Override
    public String decompress(String compressedFile) {
        String decompressedFile="decompress.txt";

        Decompression hd = new HuffmanDecompression();

        try {
            FileInputStream fin = new FileInputStream(compressedFile);
            ObjectInputStream in = new ObjectInputStream(fin);

            Node root = hd.regenerateTree(in);
            int paddedZeros = hd.getPaddedZeros(in);
            byte[] compressedString = hd.getCompressedString(in);

            in.close();
            fin.close();

            StringBuilder bitStr = hd.getBitString(compressedString);
            StringBuilder decompressedStr = hd.getDecompressedString(root, paddedZeros, bitStr.toString());

            hd.WriteIntoFile(decompressedFile, decompressedStr.toString());
            return decompressedFile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
