package com.mycompany.app.decompress;
import com.mycompany.app.treeNode.Node;

/**
 * The type Huffman decompression.
 */
public class HuffmanDecompression implements IHuffmanDecompression {
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
}