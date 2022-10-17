package com.ayata.urldatabase.static_files;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Library {
    public static String splitAndGetFirst(String data, String splitSequence){
        String arrData[] = data.split(splitSequence);
        return arrData[0];
    }

    public static void createFile(MultipartFile file, String path) throws IOException {
        File convertFile = new File(path);
        convertFile.createNewFile();
        FileOutputStream fout = new FileOutputStream(convertFile);
        fout.write(file.getBytes());
        fout.close();
    }
}
