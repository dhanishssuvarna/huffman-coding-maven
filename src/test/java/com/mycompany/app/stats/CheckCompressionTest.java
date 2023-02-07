package com.mycompany.app.stats;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CheckCompressionTest {
    CheckCompression cc;

    @Before
    public void setUp() throws Exception {
        cc = new CheckCompression();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetByteArray() {
        byte[] expected = "abcaba".getBytes();

        byte[] result = cc.getByteArray("testFile.txt");
        assertArrayEquals(expected, result);
        assertThrows(RuntimeException.class, () -> {
            cc.getByteArray("Invalid.txt");
        });
    }

    @Test
    public void testCompareFiles() {
        assertTrue(cc.CompareFiles("testFile.txt", "testFile.txt"));
        assertFalse(cc.CompareFiles("testFile.txt", "testCompress.txt"));
    }

}