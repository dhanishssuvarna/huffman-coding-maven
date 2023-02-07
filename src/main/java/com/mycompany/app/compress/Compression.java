package com.mycompany.app.compress;

import com.mycompany.app.treeNode.Node;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * The interface Compression.
 */
public interface Compression {
    /**
     * Gets content.
     *
     * @param path the path
     * @return the content
     */
    StringBuilder getContent(String path) throws FileNotFoundException;


    /**
     * Generate char freq map.
     *
     * @param path the path
     * @return the map
     */
    Map<Character, Integer> generateCharFreq(String path);

    /**
     * Generate tree node.
     *
     * @param pq the pq is the priority Queue
     * @return the node i.e root of the tree
     */
    Node generateTree(PriorityQueue<Node> pq);

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

    /**
     * Write into file.
     *
     * @param root        the root
     * @param paddedZeros the padded zeros
     * @param byteArray   the byte array
     * @return the string
     */
    void WriteIntoFile(String file, Node root, int paddedZeros, byte[] byteArray);

    /**
     * Compress the input file
     *
     * @param path the path
     * @return the string
     */
    String compress(String path) throws FileNotFoundException;
}
