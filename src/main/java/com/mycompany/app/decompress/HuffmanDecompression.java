package com.mycompany.app.decompress;
import com.mycompany.app.treeNode.Node;
import java.io.*;

/**
 * The type Huffman decompression.
 */
public class HuffmanDecompression implements Decompression {
    @Override
    public Node regenerateTree(ObjectInputStream in){
        Node root=null;
        try {
            root = (Node) in.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return root;
    }

    @Override
    public int getPaddedZeros(ObjectInputStream in){
        int paddedZeros = 0;
        try {
            paddedZeros = in.readInt();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return paddedZeros;
    }


    @Override
    public byte[] getCompressedString(ObjectInputStream in){
        byte[] compressedString = null;
        try {
            compressedString = (byte[]) in.readObject();
        }catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return compressedString;
    }


    @Override
    public StringBuilder getBitString(byte[] compressedString){
        StringBuilder bitStr = new StringBuilder();
        for(byte b: compressedString){
            if(b>0){
                bitStr.append(generateBitString(b));
            }else{
                bitStr.append(generateBitString((b+256)%256));
            }
        }
        return bitStr;
    }

    @Override
    public String generateBitString(int c){
        StringBuilder binary = new StringBuilder();
        while (c > 0 ) {
            binary.append( c & 1 );
            c = c >> 1;
        }
        binary.append("0".repeat(8 - binary.length()));
        return binary.reverse().toString();
    }

    @Override
    public StringBuilder getDecompressedString(Node root, int paddedZeros, String bitStr){
        Node temp=root;
        int i=0;
        StringBuilder decompressedStr = new StringBuilder();
        while(i < bitStr.length()-paddedZeros)
        {
            while(temp.left != null && temp.right != null){
                if(bitStr.charAt(i) == '0')
                    temp=temp.left;

                if(bitStr.charAt(i) == '1')
                    temp=temp.right;
                i++;
            }
            decompressedStr.append((char) temp.value);
            temp=root;
        }
        return decompressedStr;
    }


    @Override
    public void WriteIntoFile(String file, String decompressedStr){
        try {
            FileWriter f = new FileWriter(file);
            f.write(decompressedStr);
            f.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String decompression(String path) {
        try {
            FileInputStream fin = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fin);

            Node root = regenerateTree(in);
            int paddedZeros = getPaddedZeros(in);
            byte[] compressedString = getCompressedString(in);

            in.close();
            fin.close();

            StringBuilder bitStr = getBitString(compressedString);
            StringBuilder decompressedStr = getDecompressedString(root, paddedZeros, bitStr.toString());

            String file = "decompress.txt";
            WriteIntoFile(file, decompressedStr.toString());
            return file;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}