package com.mycompany.app.compress;

import com.mycompany.app.treeNode.Node;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import static org.junit.Assert.*;

public class HuffmanCompressionTest {
    private HuffmanCompression HuffCompress;
    private Node node;

    @Before
    public void setUp() throws Exception {
        HuffCompress = new HuffmanCompression();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testFileGetContent() {
        StringBuilder result = HuffCompress.getContent("testFile.txt");
        assertEquals("abcaba", result.toString());
        assertThrows(RuntimeException.class, () -> HuffCompress.getContent("Invalid.txt"));
    }

    @Test
    public void testCharacterFrequency() throws IOException {
        Map<Character, Integer> expected = new HashMap<>();
        expected.put('a', 3);
        expected.put('b', 2);
        expected.put('c', 1);

        Map<Character, Integer> result = HuffCompress.generateCharFreq("testFile.txt");
        assertEquals(expected, result);
        assertThrows(RuntimeException.class, () -> HuffCompress.generateCharFreq("Invalid.txt"));
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
    public void testGenerateTree() {
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
    public void testGenerateTreeSingleNode(){
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
    public void testGetTable() {
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
    public void testGetBitString() {
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
    public void testWriteIntoFile(){
        String file = "testCompress.txt";
        Node root = new Node('a'+'b'+'c', 6);
        root.left = new Node('a', 3);
        root.right = new Node('b'+'c', 3);
        root.right.left = new Node('c', 1);
        root.right.right = new Node('b', 2);
        int paddedZero = 7;
        byte[] byteArray = new byte[]{115, 0};

        HuffCompress.WriteIntoFile(file, root, paddedZero, byteArray);

        Node resultRoot;
        int resultPad;
        byte[] resultByteArray;

        try{
            FileInputStream fin = new FileInputStream(file);
            ObjectInputStream in =new ObjectInputStream(fin);

            resultRoot= (Node) in.readObject();
            resultPad = in.readInt();
            resultByteArray = (byte[]) in.readObject();

        }catch (IOException e){
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        assertTrue(areIdentical(root, resultRoot));
        assertEquals(paddedZero, resultPad);
        assertArrayEquals(byteArray, resultByteArray);
        assertThrows(RuntimeException.class, () -> HuffCompress.WriteIntoFile("dummy.txt", root, paddedZero, byteArray));
    }

    @Test
    public void testCompression(){
        String path = "testFile.txt";

        String expected = "compress.txt";
        String result = HuffCompress.compress(path);

        assertEquals(expected, result);
    }
}