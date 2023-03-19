package ru.academits;

import java.io.File;
public class Methods {
    public static String fullFilePath() {
        File myFile = new File(TestData.filePath);
        return myFile.getAbsolutePath();
    }
    public static String fileName() {
        File myFile = new File(TestData.filePath);
        return myFile.getName();
    }
}