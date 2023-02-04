package com.mycompany.app.compress;

import com.mycompany.app.treeNode.Node;

import java.io.FileReader;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * The interface Compression.
 */
public interface Compression {
    /**
     * Gets content of the input file
     *
     * @param fr the file reader
     * @return the content and generate char freq
     */
    Map<Character, Integer> getContentAndGenerateCharFreq(FileReader fr);

    /**
     * Generate tree node.
     *
     * @param mp the mp is the frequency map
     * @param pq the pq is the priority Queue
     * @return the node i.e root of the tree
     */
    Node generateTree(Map<Character, Integer> mp, PriorityQueue<Node> pq);

    /**
     * Generate table map.
     *
     * @param root the root of tree
     * @return the map is the Huffman table
     */
    Map<Character, String> generateTable(Node root);

    /**
     * Gets bit string.
     *
     * @param table the Huffman table
     * @return the bit string
     */
    StringBuilder getBitString(Map<Character, String> table);

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

    /**
     * Write into file.
     *
     * @param file        the file
     * @param root        the root
     * @param paddedZeros the padded zeros
     * @param byteArray   the byte array
     */
    void WriteIntoFile(String file, Node root, int paddedZeros, byte[] byteArray);

    /**
     * Compress the input file
     */
    void compress();
}
