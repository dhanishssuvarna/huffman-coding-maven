package com.mycompany.app.FileIO;

import com.mycompany.app.treeNode.Node;

public interface IFileWrite {
    public void write(String file, Node root, int paddedZeros, byte[] byteArray);

    public void write(String file, StringBuilder s);
}
