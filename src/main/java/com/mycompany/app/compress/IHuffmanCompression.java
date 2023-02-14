package com.mycompany.app.compress;

import com.mycompany.app.treeNode.Node;

import java.io.FileNotFoundException;
import java.util.Map;

/**
 * The interface Compression.
 */
public interface IHuffmanCompression {
    /**
     * Generate char freq map.
     *
     * @param s the path
     * @return the map
     */
    Map<Character, Integer> generateCharFreq(StringBuilder s);


    /**
     * Generate tree node.
     *
     * @param mp the mp
     * @return the node
     */
    public Node generateTree(Map<Character, Integer> mp);

    /**
     * Get table.
     *
     * @param root  the root
     * @param table the table
     * @param str   the str
     */
    void getTable(Node root, Map<Character, String> table, String str);

    /**
     * Gets bit string.
     *
     * @param table the Huffman table
     * @param s     the s
     * @return the bit string
     */
    StringBuilder getBitString(Map<Character, String> table, String s);

    /**
     * Pad bit string int.
     *
     * @param bitStr the bit str
     * @return the int
     */
    int padBitString(StringBuilder bitStr);

    /**
     * Get compressed byte array byte [ ].
     *
     * @param bitStr the bit string
     * @return the byte [ ]
     */
    byte[] getCompressedByteArray(String bitStr);

    double getWAvg(Map<Character, Integer> mp, Map<Character, String> table);
}
