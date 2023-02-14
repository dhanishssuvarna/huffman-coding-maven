package com.mycompany.app.compress;
import com.mycompany.app.treeNode.Node;
import com.mycompany.app.treeNode.NodeComparator;

import java.util.*;

/**
 * The type Huffman compression.
 */
public class HuffmanCompression implements IHuffmanCompression {
    @Override
    public Map<Character, Integer> generateCharFreq(StringBuilder s){
        Map<Character, Integer> mp = new HashMap<>();

        for(int i=0; i<s.length(); i++){
            char ch = s.charAt(i);
            mp.put(ch,(mp.getOrDefault(ch,0)+1));
        }
        return mp;
    }

    @Override
    public Node generateTree(Map<Character, Integer> mp){
        PriorityQueue<Node> pq = new PriorityQueue<>(new NodeComparator());

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
    public double getWAvg(Map<Character, Integer> mp, Map<Character, String> table){
        double sum=0;
        double cnt=0;

        for(Map.Entry<Character, Integer> m : mp.entrySet()){
            sum += m.getValue() * table.get(m.getKey()).length();
            cnt += m.getValue();
        }
        return sum/cnt;
    }
}