package com.solvd.prendiodemo.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class FileUtil {

    public static String loadFileAndGetPath(String url, File file) {
        try {
            FileUtils.copyURLToFile(new URL(url), file);
            return file.getAbsolutePath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
