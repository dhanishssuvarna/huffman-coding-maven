package com.mycompany.app.FileIO;

import com.mycompany.app.treeNode.Node;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;

public class FileRead implements IFileRead {
    @Override
    public StringBuilder readComp(String path) {
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
    public ComplexReturnType readDecomp(String path) {
        try{
            FileInputStream fin = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fin);

            Node root = (Node) in.readObject();
            int paddedZeros = in.readInt();
            byte[] compressedString = (byte[]) in.readObject();

            in.close();
            fin.close();

            ComplexReturnType crt = new ComplexReturnType(root, paddedZeros, compressedString);
            return crt;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
