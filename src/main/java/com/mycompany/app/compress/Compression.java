package com.mycompany.app.compress;

/**
 * The interface Compression.
 */
public interface Compression {
    /**
     * Gets content of the input file
     */
    void generateCharFreq();

    /**
     * Compress the input file
     */
    void compress();
}
