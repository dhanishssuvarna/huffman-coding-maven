package com.mycompany.app.decompress;

import com.mycompany.app.treeNode.Node;

import java.io.ObjectInputStream;

/**
 * The interface Decompression.
 */
public interface Decompression {
    /**
     * Regenerate tree node.
     *
     * @param in the in is ObjectInputStream
     * @return the node
     */
    Node regenerateTree(ObjectInputStream in);

    /**
     * Gets padded zeros.
     *
     * @param in the in is ObjectInputStream
     * @return the padded zeros
     */
    int getPaddedZeros(ObjectInputStream in);

    /**
     * Get compressed string byte [ ].
     *
     * @param in the in is ObjectInputStream
     * @return the byte [ ]
     */
    byte[] getCompressedString(ObjectInputStream in);

    /**
     * Gets bit string.
     *
     * @param compressedString the compressed string
     * @return the bit string
     */
    StringBuilder getBitString(byte[] compressedString);

    /**
     * Generate bit string string.
     *
     * @param c the Ascci of char
     * @return the string
     */
    String generateBitString(int c);

    /**
     * Gets decompressed string.
     *
     * @param root        the root
     * @param paddedZeros the padded zeros
     * @param bitStr      the bit str
     * @return the decompressed string
     */
    StringBuilder getDecompressedString(Node root, int paddedZeros, String bitStr);

    /**
     * Write into file.
     *
     * @param file            the file
     * @param decompressedStr the decompressed string
     */
    void WriteIntoFile(String file, String decompressedStr);

    /**
     * Decompression the compressed file
     */
    void decompression();
}
