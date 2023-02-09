package com.mycompany.app.zipUnzip;

public interface IZipperUnzipper {
    public String compress(String originalFile);

    public String decompress(String compressedFile);
}
