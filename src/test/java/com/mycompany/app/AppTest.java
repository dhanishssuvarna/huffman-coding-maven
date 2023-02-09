package com.mycompany.app;

import static com.mycompany.app.App.CompareFiles;
import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void testCompareFiles_NormalCase() throws IOException {
        File file1 = tempFolder.newFile("testFile1.txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(file1));
        bw.write("abcaba");
        bw.close();

        File file2 = tempFolder.newFile("testFile2.txt");
        bw = new BufferedWriter(new FileWriter(file2));
        bw.write("abcdef");
        bw.close();

        assertTrue(CompareFiles(file1.getPath(), file1.getPath()));
        assertFalse(CompareFiles(file1.getPath(), file2.getPath()));
    }

    @Test
    public void testCompareFiles_FilNotFound() throws IOException {
        assertThrows(RuntimeException.class, () -> CompareFiles("FileNotFound.txt", "FileNotFound.txt"));
        assertThrows(RuntimeException.class, () -> CompareFiles("FileNotFound.txt", "FileNotFound.txt"));
    }
}
