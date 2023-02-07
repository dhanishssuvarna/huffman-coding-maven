package com.mycompany.app.decompress;

import com.mycompany.app.treeNode.Node;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class HuffmanDecompressionTest {
    HuffmanDecompression HuffDecompress;

    void  setDummyContent() throws IOException {
        Node root = new Node('a'+'b'+'c', 6);
        root.left = new Node('a', 3);
        root.right = new Node('b'+'c', 3);
        root.right.left = new Node('c', 1);
        root.right.right = new Node('b', 2);
        int paddedZero = 7;
        byte[] byteArray = new byte[]{115, 0};

        FileOutputStream fout = new FileOutputStream("testCompress.txt");
        ObjectOutputStream out =new ObjectOutputStream(fout);

        out.writeObject(root);
        out.writeInt(paddedZero);
        out.writeObject(byteArray);

        out.close();
        fout.close();
    }

    @Before
    public void setUp() throws Exception {
        HuffDecompress = new HuffmanDecompression();
        setDummyContent();
    }

    @After
    public void tearDown() throws Exception {
    }

    public static boolean areIdentical(Node root1, Node root2) {

        if (root1 == null && root2 == null) {
            return true;
        }

        if (root1 != null && root2 != null) {
            return ((root1.value == root2.value) &&
                    areIdentical(root1.left, root2.left) &&
                    areIdentical(root1.right, root2.right));
        }

        return false;
    }

    @Test
    public void testRegenerateTree() throws IOException {
        FileInputStream fin = new FileInputStream("testCompress.txt");
        ObjectInputStream in = new ObjectInputStream(fin);

        Node expected = new Node('a'+'b'+'c', 6);
        expected.left = new Node('a', 3);
        expected.right = new Node('b'+'c', 3);
        expected.right.left = new Node('c', 1);
        expected.right.right = new Node('b', 2);

        Node result = HuffDecompress.regenerateTree(in);

        fin.close();
        in.close();

        assertTrue(areIdentical(expected, result));
    }

    @Test
    public void testRegenerateTreeThrowsException() throws IOException {
        FileInputStream fin = new FileInputStream("testCompress.txt");
        ObjectInputStream in = new ObjectInputStream(fin);

        Node temp = HuffDecompress.regenerateTree(in);
        assertThrows(RuntimeException.class, () -> HuffDecompress.regenerateTree(in));
    }

    @Test
    public void testGetPaddedZeros() throws IOException {
        FileInputStream fin = new FileInputStream("testCompress.txt");
        ObjectInputStream in = new ObjectInputStream(fin);

        int expected = 7;

        Node temp = HuffDecompress.regenerateTree(in);
        int result = HuffDecompress.getPaddedZeros(in);

        fin.close();
        in.close();

        assertEquals(expected, result);
    }

    @Test
    public void testGetPaddedZerosThrowsException() throws IOException {
        FileInputStream fin = new FileInputStream("testCompress.txt");
        ObjectInputStream in = new ObjectInputStream(fin);

        assertThrows(RuntimeException.class, () -> HuffDecompress.getPaddedZeros(in));
    }

    @Test
    public void testGetCompressedString() throws IOException {
        FileInputStream fin = new FileInputStream("testCompress.txt");
        ObjectInputStream in = new ObjectInputStream(fin);

        byte[] expected = new byte[]{115, 0};

        Node temp = HuffDecompress.regenerateTree(in);
        int temp2 = HuffDecompress.getPaddedZeros(in);
        byte[] result = HuffDecompress.getCompressedString(in);

        fin.close();
        in.close();

        assertArrayEquals(expected, result);
    }

    @Test
    public void testGetCompressedStringThrowsException() throws IOException {
        FileInputStream fin = new FileInputStream("testCompress.txt");
        ObjectInputStream in = new ObjectInputStream(fin);

        Node temp = HuffDecompress.regenerateTree(in);
        assertThrows(RuntimeException.class, () -> HuffDecompress.getCompressedString(in));
    }

    @Test
    public void testGetBitString() {
        byte[] compressedString = new byte[]{115, 0};

        StringBuilder expected = new StringBuilder();
        expected.append("01110011");
        expected.append("00000000");

        StringBuilder result = HuffDecompress.getBitString(compressedString);

        assertEquals(expected.toString(), result.toString());
    }

    @Test
    public void testGenerateBitString() {
        int c = 115;

        String expected = "01110011";
        String result = HuffDecompress.generateBitString(c);

        assertEquals(expected, result);
    }

    @Test
    public void testGetDecompressedString() {
        Node root = new Node('a'+'b'+'c', 6);
        root.left = new Node('a', 3);
        root.right = new Node('b'+'c', 3);
        root.right.left = new Node('c', 1);
        root.right.right = new Node('b', 2);
        int paddedZero = 7;
        String bitStr = "0111001100000000";

        StringBuilder expected = new StringBuilder();
        expected.append("abcaba");

        StringBuilder result = HuffDecompress.getDecompressedString(root, paddedZero, bitStr);

        assertEquals(expected.toString(), result.toString());
    }

    @Test
    public void testWriteIntoFile() {
        String file = "testDecompress.txt";
        String decompressedStr = "abcaba";

        boolean expected = true;
        boolean result = HuffDecompress.WriteIntoFile(file, decompressedStr);

        assertEquals(expected, result);
        assertThrows(RuntimeException.class, () -> HuffDecompress.WriteIntoFile("dummy.txt", decompressedStr));

    }

    @Test
    public void testDecompression() {
        String path = "testCompress.txt";

        String expected = "decompress.txt";
        String result = HuffDecompress.decompression(path);

        assertEquals(expected, result);
        assertThrows(RuntimeException.class, () -> HuffDecompress.decompression("Invalid.txt"));
    }
}