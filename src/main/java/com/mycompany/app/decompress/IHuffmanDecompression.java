package com.mycompany.app.decompress;

import com.mycompany.app.treeNode.Node;


/**
 * The interface Decompression.
 */
public interface IHuffmanDecompression {
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
}
