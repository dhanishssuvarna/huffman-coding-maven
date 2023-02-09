package com.mycompany.app.compress;
import com.mycompany.app.treeNode.Node;

import java.io.*;
import java.util.*;

/**
 * The type Huffman compression.
 */
public class HuffmanCompression implements Compression {
    @Override
    public StringBuilder getContent(String path) {
        StringBuilder s = new StringBuilder();
        try {
            FileReader fr = new FileReader(path);

            int val = fr.read();
            while (val != -1){
                char ch = (char) val;
                s.append(ch);
                val = fr.read();
            }

            fr.close();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        return s;
    }

    @Override
    public Map<Character, Integer> generateCharFreq(String path){
        Map<Character, Integer> mp = new HashMap<>();
        try {
            FileReader fr = new FileReader(path);
            int val = fr.read();
            while (val != -1){

                char ch = (char) val;
                mp.put(ch,(mp.getOrDefault(ch,0)+1));
                val = fr.read();
            }
            fr.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return mp;
    }

    @Override
    public Node generateTree(PriorityQueue<Node> pq){

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

    @Override
    public void getTable(Node root, Map<Character, String> table, String str) {
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
    public StringBuilder getBitString(Map<Character, String> table, String s){
        StringBuilder bitStr = new StringBuilder();

        if (table == null || table.size() == 0 || s.length() == 0){
            throw new RuntimeException();
        }

        for (char c : s.toCharArray()) {
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
}