package com.mycompany.app.decompress;

import com.mycompany.app.treeNode.Node;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class HuffmanDecompressionTest {
    HuffmanDecompression HuffDecompress;

    HuffmanDecompression HuffDecompressSpy;

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Before
    public void setUp() throws Exception {
        HuffDecompress = new HuffmanDecompression();
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
//
//    @Test
//    public void testRegenerateTree_NormalCase() throws IOException {
//        File file = tempFolder.newFile("./testCompress.txt");
//
//        Node root = new Node('a'+'b'+'c', 6);
//        root.left = new Node('a', 3);
//        root.right = new Node('b'+'c', 3);
//        root.right.left = new Node('c', 1);
//        root.right.right = new Node('b', 2);
//        int paddedZero = 7;
//        byte[] byteArray = new byte[]{115, 0};
//
//        FileOutputStream fout = new FileOutputStream(file);
//        ObjectOutputStream out =new ObjectOutputStream(fout);
//
//        out.writeObject(root);
//        out.writeInt(paddedZero);
//        out.writeObject(byteArray);
//
//        out.close();
//        fout.close();
//
//        FileInputStream fin = new FileInputStream(file);
//        ObjectInputStream in = new ObjectInputStream(fin);
//
//        Node expected = new Node('a'+'b'+'c', 6);
//        expected.left = new Node('a', 3);
//        expected.right = new Node('b'+'c', 3);
//        expected.right.left = new Node('c', 1);
//        expected.right.right = new Node('b', 2);
//
//        Node result = HuffDecompress.regenerateTree(in);
//
//        fin.close();
//        in.close();
//
//        assertTrue(areIdentical(expected, result));
//    }
//
//    @Test
//    public void testRegenerateTree_TreeNotFoundInFile() throws IOException {
//        File file = tempFolder.newFile("./testCompress.txt");
//
//        int paddedZero = 7;
//        byte[] byteArray = new byte[]{115, 0};
//
//        FileOutputStream fout = new FileOutputStream(file);
//        ObjectOutputStream out =new ObjectOutputStream(fout);
//
//        out.writeInt(paddedZero);
//        out.writeObject(byteArray);
//
//        out.close();
//        fout.close();
//
//        FileInputStream fin = new FileInputStream(file);
//        ObjectInputStream in = new ObjectInputStream(fin);
//
//        assertThrows(RuntimeException.class, () -> HuffDecompress.regenerateTree(in));
//    }
//
//    @Test
//    public void testGetPaddedZeros() throws IOException {
//        File file = tempFolder.newFile("./testCompress.txt");
//
//        Node root = new Node('a'+'b'+'c', 6);
//        root.left = new Node('a', 3);
//        root.right = new Node('b'+'c', 3);
//        root.right.left = new Node('c', 1);
//        root.right.right = new Node('b', 2);
//        int paddedZero = 7;
//        byte[] byteArray = new byte[]{115, 0};
//
//        FileOutputStream fout = new FileOutputStream(file);
//        ObjectOutputStream out =new ObjectOutputStream(fout);
//
//        out.writeObject(root);
//        out.writeInt(paddedZero);
//        out.writeObject(byteArray);
//
//        out.close();
//        fout.close();
//
//        FileInputStream fin = new FileInputStream(file);
//        ObjectInputStream in = new ObjectInputStream(fin);
//
//        int expected = 7;
//
//        Node temp = HuffDecompress.regenerateTree(in);
//        int result = HuffDecompress.getPaddedZeros(in);
//
//        fin.close();
//        in.close();
//
//        assertEquals(expected, result);
//    }
//
//    @Test
//    public void testGetPaddedZeros_PaddedZerosNotFoundInFile() throws IOException {
//        File file = tempFolder.newFile("./testCompress.txt");
//
//        Node root = new Node('a'+'b'+'c', 6);
//        root.left = new Node('a', 3);
//        root.right = new Node('b'+'c', 3);
//        root.right.left = new Node('c', 1);
//        root.right.right = new Node('b', 2);
//        byte[] byteArray = new byte[]{115, 0};
//
//        FileOutputStream fout = new FileOutputStream(file);
//        ObjectOutputStream out =new ObjectOutputStream(fout);
//
//        out.writeObject(root);
//        out.writeObject(byteArray);
//
//        out.close();
//        fout.close();
//
//        FileInputStream fin = new FileInputStream(file);
//        ObjectInputStream in = new ObjectInputStream(fin);
//
//        Node temp = HuffDecompress.regenerateTree(in);
//        assertThrows(RuntimeException.class, () -> HuffDecompress.getPaddedZeros(in));
//    }
//
//    @Test
//    public void testGetCompressedString() throws IOException {
//        File file = tempFolder.newFile("./testCompress.txt");
//
//        Node root = new Node('a'+'b'+'c', 6);
//        root.left = new Node('a', 3);
//        root.right = new Node('b'+'c', 3);
//        root.right.left = new Node('c', 1);
//        root.right.right = new Node('b', 2);
//        int paddedZero = 7;
//        byte[] byteArray = new byte[]{115, 0};
//
//        FileOutputStream fout = new FileOutputStream(file);
//        ObjectOutputStream out =new ObjectOutputStream(fout);
//
//        out.writeObject(root);
//        out.writeInt(paddedZero);
//        out.writeObject(byteArray);
//
//        out.close();
//        fout.close();
//
//        FileInputStream fin = new FileInputStream(file);
//        ObjectInputStream in = new ObjectInputStream(fin);
//
//        byte[] expected = new byte[]{115, 0};
//
//        Node temp = HuffDecompress.regenerateTree(in);
//        int temp2 = HuffDecompress.getPaddedZeros(in);
//        byte[] result = HuffDecompress.getCompressedString(in);
//
//        fin.close();
//        in.close();
//
//        assertArrayEquals(expected, result);
//    }
//
//    @Test
//    public void testGetCompressedString_CompressedStringNotFoundInFile() throws IOException {
//        File file = tempFolder.newFile("./testCompress.txt");
//
//        Node root = new Node('a'+'b'+'c', 6);
//        root.left = new Node('a', 3);
//        root.right = new Node('b'+'c', 3);
//        root.right.left = new Node('c', 1);
//        root.right.right = new Node('b', 2);
//        int paddedZero = 7;
//
//        FileOutputStream fout = new FileOutputStream(file);
//        ObjectOutputStream out =new ObjectOutputStream(fout);
//
//        out.writeObject(root);
//        out.writeInt(paddedZero);
//
//        out.close();
//        fout.close();
//
//        FileInputStream fin = new FileInputStream(file);
//        ObjectInputStream in = new ObjectInputStream(fin);
//
//        Node temp = HuffDecompress.regenerateTree(in);
//        int temp2 = HuffDecompress.getPaddedZeros(in);
//        assertThrows(RuntimeException.class, () -> HuffDecompress.getCompressedString(in));
//    }

    @Test
    public void testGetBitString() {
        byte[] compressedString = new byte[]{115, 0};

        StringBuilder expected = new StringBuilder();
        expected.append("01110011");
        expected.append("00000000");

        HuffDecompressSpy = Mockito.spy(HuffDecompress);
        Mockito.doReturn("01110011").when(HuffDecompressSpy).generateBitString(115);
        Mockito.doReturn("00000000").when(HuffDecompressSpy).generateBitString(0);

        StringBuilder result = HuffDecompressSpy.getBitString(compressedString);

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
    public void testGetDecompressedString_NormalCase() {
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
    public void testGetDecompressedString_RootNull() {
        Node root = null;
        int paddedZero = 7;
        String bitStr = "0111001100000000";

        assertThrows(RuntimeException.class, () -> HuffDecompress.getDecompressedString(root, paddedZero, bitStr));
    }
//
//    @Test
//    public void testWriteIntoFile_NormalCase() throws IOException {
//        File file = tempFolder.newFile("./testDecompress.txt");
//        String decompressedStr = "abcaba";
//
//        String result="";
//        HuffDecompress.WriteIntoFile(file.getPath(), decompressedStr);
//
//        FileReader f = new FileReader(file);
//        int i;
//        char c;
//        while((i = f.read()) != -1){
//            c = (char) i;
//            result += c;
//        }
//
//        assertEquals(decompressedStr, result);
//    }
//
//    @Test
//    public void testWriteIntoFile_FileIsWriteProtected() throws IOException {
//        File file = tempFolder.newFile("./testDecompress.txt");
//        file.setWritable(false);
//
//        String decompressedStr = "abcaba";
//
//        assertThrows(RuntimeException.class, () -> HuffDecompress.WriteIntoFile(file.getPath(), decompressedStr));
//    }
}