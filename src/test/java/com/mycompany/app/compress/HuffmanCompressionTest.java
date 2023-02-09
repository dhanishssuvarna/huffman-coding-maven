package com.mycompany.app.compress;

import com.mycompany.app.treeNode.Node;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import static org.junit.Assert.*;

public class HuffmanCompressionTest {
    private HuffmanCompression HuffCompress;
    private Node node;

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Before
    public void setUp() throws Exception {
        HuffCompress = new HuffmanCompression();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetContent_NormalCase() throws IOException {
        File file = tempFolder.newFile("testFile.txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.write("abcaba");
        bw.close();

        StringBuilder result = HuffCompress.getContent(file.getPath());
        assertEquals("abcaba", result.toString());
    }

    @Test
    public void testGetContent_FileDoesNotExist() {
        assertThrows(RuntimeException.class, () -> HuffCompress.getContent("FileDoesNotExist.txt"));
    }

    @Test
    public void testCharacterFrequency_NormalCase() throws IOException {
        File file = tempFolder.newFile("testFile.txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.write("abcaba");
        bw.close();

        Map<Character, Integer> expected = new HashMap<>();
        expected.put('a', 3);
        expected.put('b', 2);
        expected.put('c', 1);

        Map<Character, Integer> result = HuffCompress.generateCharFreq(file.getPath());
        assertEquals(expected, result);
    }

    @Test
    public void testCharacterFrequency_FileDoesNotExist() throws IOException {
        assertThrows(RuntimeException.class, () -> HuffCompress.generateCharFreq("FileDoesNotExist.txt"));
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
    public void testGenerateTree_NormalCase() {
        PriorityQueue<Node> pq = new PriorityQueue<>((l, r) -> {
            if(l.weight == r.weight)
                return l.value - r.value;
            return l.weight - r.weight;
        });
        pq.add(new Node('a', 3));
        pq.add(new Node('b', 2));
        pq.add(new Node('c', 1));


        Node expected = new Node('a'+'b'+'c', 6);
        expected.left = new Node('a', 3);
        expected.right = new Node('b'+'c', 3);
        expected.right.left = new Node('c', 1);
        expected.right.right = new Node('b', 2);

        Node result = HuffCompress.generateTree(pq);

        assertTrue(areIdentical(expected, result));
    }

    @Test
    public void testGenerateTree_SingleNode(){
        PriorityQueue<Node> pq = new PriorityQueue<>((l, r) -> {
            if(l.weight == r.weight)
                return l.value - r.value;
            return l.weight - r.weight;
        });

        pq.add(new Node('a', 3));

        Node expected = new Node('a', 3);
        expected.left = new Node('a', 3);
        expected.right = new Node(0, 0);

        Node result = HuffCompress.generateTree(pq);

        assertTrue(areIdentical(expected, result));
    }

    @Test
    public void testGetTable_NormalCase() {
        Node root = new Node('a'+'b'+'c', 6);
        root.left = new Node('a', 3);
        root.right = new Node('b'+'c', 3);
        root.right.left = new Node('c', 1);
        root.right.right = new Node('b', 2);

        Map<Character, String> expected = new HashMap<>();
        expected.put('a', "0");
        expected.put('b', "11");
        expected.put('c', "10");

        Map<Character, String> result = new HashMap<>();
        HuffCompress.getTable(root, result, "");

        assertEquals(expected, result);
    }

    @Test
    public void testGetTable_RootNull() {
        Node root = null;
        Map<Character, String> result = new HashMap<>();
        HuffCompress.getTable(root, result, "");

        assertEquals(0, result.size());
    }



    @Test
    public void testGetBitString_NormalCase() {
        Map<Character, String> table = new HashMap<>();
        table.put('a', "0");
        table.put('b', "11");
        table.put('c', "10");

        String s = "abcaba";

        String expected = "011100110";

        String result = HuffCompress.getBitString(table, s).toString();

        assertEquals(expected, result);
    }

    @Test
    public void testGetBitString_TableNull() {
        Map<Character, String> table = null;
        String s = "abcaba";

        assertThrows(RuntimeException.class, () ->  HuffCompress.getBitString(table, s));
    }

    @Test
    public void testGetBitString_TableEmpty() {
        Map<Character, String> table = new HashMap<>();
        String s = "abcaba";

        assertThrows(RuntimeException.class, () ->  HuffCompress.getBitString(table, s));
    }

    @Test
    public void testGetBitString_StringEmpty() {
        Map<Character, String> table = new HashMap<>();
        table.put('a', "0");
        table.put('b', "11");
        table.put('c', "10");

        String s = "";

        assertThrows(RuntimeException.class, () ->  HuffCompress.getBitString(table, s));
    }

    @Test
    public void testPadBitString(){
        StringBuilder bitString = new StringBuilder();
        bitString.append("011100110");

        int expected = 7;

        int result = HuffCompress.padBitString(bitString);
        assertEquals(expected, result);
    }

    @Test
    public void testGetCompressedByteArray(){
        String bitStr = "0111001100100000";

        byte[] expected = new byte[2];
        expected[0] = (byte) 115;
        expected[1] = (byte) 32;

        byte[] result = HuffCompress.getCompressedByteArray(bitStr);

        assertArrayEquals(expected, result);
    }

    @Test
    public void testWriteIntoFile_NormalCase() throws IOException, ClassNotFoundException {
        File file = tempFolder.newFile("testCompress.txt");

        Node root = new Node('a'+'b'+'c', 6);
        root.left = new Node('a', 3);
        root.right = new Node('b'+'c', 3);
        root.right.left = new Node('c', 1);
        root.right.right = new Node('b', 2);
        int paddedZero = 7;
        byte[] byteArray = new byte[]{115, 0};

        HuffCompress.WriteIntoFile(file.getPath(), root, paddedZero, byteArray);

        FileInputStream fin = new FileInputStream(file);
        ObjectInputStream in =new ObjectInputStream(fin);

        Node resultRoot= (Node) in.readObject();
        int resultPad = in.readInt();
        byte[] resultByteArray = (byte[]) in.readObject();

        assertTrue(areIdentical(root, resultRoot));
        assertEquals(paddedZero, resultPad);
        assertArrayEquals(byteArray, resultByteArray);
    }

    @Test
    public void testWriteIntoFile_NoFileWritePermission() throws IOException {
        File file = tempFolder.newFile("testCompress.txt");
        file.setWritable(false);

        Node root = new Node('a'+'b'+'c', 6);
        root.left = new Node('a', 3);
        root.right = new Node('b'+'c', 3);
        root.right.left = new Node('c', 1);
        root.right.right = new Node('b', 2);
        int paddedZero = 7;
        byte[] byteArray = new byte[]{115, 0};

        assertThrows(RuntimeException.class, () -> HuffCompress.WriteIntoFile(file.getPath(), root, paddedZero, byteArray));
    }
}