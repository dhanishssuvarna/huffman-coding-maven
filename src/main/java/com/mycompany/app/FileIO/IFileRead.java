package com.mycompany.app.FileIO;

import java.io.IOException;

public interface IFileRead {

    public StringBuilder readComp(String path);

    public ComplexReturnType readDecomp(String path);
}
