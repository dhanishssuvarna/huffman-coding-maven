package com.mycompany.app.compress;

import com.mycompany.app.treeNode.NodeComparator;
import com.mycompany.app.treeNode.Node;

import java.io.*;
import java.util.*;

/**
 * The type Huffman compression.
 */
public class HuffmanCompression implements Compression {
    /**
     * The Path of the original file
     */
    String path;

    /**
     * The S stores the content of th file
     */
    StringBuilder s = new StringBuilder();

    /**
     * Instantiates a new Huffman compression.
     *
     * @param path the path
     */
    public HuffmanCompression(String path){
        this.path=path;
    }

    @Override
    public Map<Character, Integer> getContentAndGenerateCharFreq(FileReader fr){
        Map<Character, Integer> mp = new HashMap<>();
        try {
            int val = fr.read();
            while (val != -1){

                char ch = (char) val;
                s.append(ch);
                mp.put(ch,(mp.getOrDefault(ch,0)+1));
                val = fr.read();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return mp;
    }

    @Override
    public Node generateTree(Map<Character, Integer> mp, PriorityQueue<Node> pq){
        for(Map.Entry<Character, Integer> e : mp.entrySet()) {
//            System.out.println(e.getKey()+" : "+ e.getValue());
            Node temp = new Node(e.getKey(), e.getValue());
            pq.add(temp);
        }

        // if single node
        if(pq.size()==1){
            Node single = pq.poll();
            Node temp = new Node(single.value,single.weight);
            temp.left=single;
            temp.right=new Node(0,0);
            pq.add(temp);
        }

        while (pq.size() != 1){
            Node l = pq.poll();
            Node r = pq.poll();
            Node temp = new Node(l.value+r.value,l.weight+ r.weight);
            temp.left=l;
            temp.right=r;
            pq.add(temp);
        }

        return pq.peek();
    }

    /**
     * Gets table recursively
     *
     * @param root  the root of the tree
     * @param table the table contains the Huffman table
     * @param str   the str contains the bit string
     */
    void getTable(Node root, Map<Character, String> table, String str) {
        if(root==null)
            return;
        if (root.left == null && root.right == null) {
            table.put((char) root.value, str);
            return;
        }
        getTable(root.left, table,str + "0");
        getTable(root.right, table,str + "1");
    }

    @Override
    public Map<Character, String> generateTable(Node root){
        Map<Character, String> table = new HashMap<>();
        String temp="";
        getTable(root, table, temp);

//        for(Map.Entry<Character, String> e : table.entrySet()) {
//            System.out.println(e.getKey()+" | "+e.getValue());
//        }
        return table;
    }

    @Override
    public StringBuilder getBitString(Map<Character, String> table){
        StringBuilder bitStr = new StringBuilder();

        for (char c : s.toString().toCharArray()) {
            String bit = table.get(c);
            bitStr.append(bit);
        }
        return bitStr;
    }

    @Override
    public int padBitString(StringBuilder bitStr){
        int paddedZeros=8-(bitStr.length()%8);
        bitStr.append("0".repeat(paddedZeros));
        return paddedZeros;
    }

    @Override
    public byte[] getCompressedByteArray(String bitStr){
        byte[] byteArray = new byte[bitStr.length()/8];
        int k=0;

        for(int i=0; i< bitStr.length(); i=i+8) {
            String t = bitStr.substring(i, i+8);
            byte ch = (byte) Integer.parseInt(t, 2);
            byteArray[k++]=ch;
        }
        return byteArray;
    }

    @Override
    public void WriteIntoFile(String file, Node root, int paddedZeros, byte[] byteArray){
        try {
            FileOutputStream fout = new FileOutputStream(file);
            ObjectOutputStream out =new ObjectOutputStream(fout);

            out.writeObject(root);
            out.writeInt(paddedZeros);
            out.writeObject(byteArray);

            out.close();
            fout.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void compress(){
        PriorityQueue<Node> pq = new PriorityQueue<>(new NodeComparator());

        try {
            FileReader fr = new FileReader(path);
            Map<Character, Integer> mp = getContentAndGenerateCharFreq(fr);
            fr.close();

            Node root = generateTree(mp, pq);
            Map<Character, String> table = generateTable(root);

            StringBuilder bitStr = getBitString(table);
            int paddedZeros = padBitString(bitStr);

            byte[] byteArray = getCompressedByteArray(bitStr.toString());

            WriteIntoFile("compress.txt", root, paddedZeros, byteArray);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}