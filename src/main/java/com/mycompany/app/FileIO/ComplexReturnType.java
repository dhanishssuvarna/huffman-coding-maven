package com.mycompany.app.FileIO;

import com.mycompany.app.treeNode.Node;

public class ComplexReturnType {
    public Node getRoot() {
        return root;
    }

    public int getPaddedZeros() {
        return paddedZeros;
    }

    public byte[] getByteArray() {
        return byteArray;
    }

    Node root;
    int paddedZeros;
    byte[] byteArray;

    public ComplexReturnType(Node root, int paddedZeros, byte[] byteArray){
        this.root = root;
        this.paddedZeros = paddedZeros;
        this.byteArray = byteArray;
    }
}
