package com.mycompany.app.FileIO;

import com.mycompany.app.treeNode.Node;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class FileWrite implements IFileWrite {
    @Override
    public void write(String file, Node root, int paddedZeros, byte[] byteArray) {
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
    public void write(String file, StringBuilder s) {
        try {
            FileWriter f = new FileWriter(file);
            f.write(s.toString());
            f.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
