package com.bkendall.bk_weather;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class FileHandler {
    public void createFile(String fileName, String jsonObjectString) throws IOException {
        // I create a file in the App memory
        File file = new File(fileName);
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(jsonObjectString);
        bufferedWriter.close();
    }

    public String readFile(File fileName) throws IOException {
        // I read the contents of a file and return a JSONObject
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder stringBuilder = new StringBuilder();
        String line = bufferedReader.readLine();
        while (line != null) {
            stringBuilder.append(line).append("\n");
            line = bufferedReader.readLine();
        }
        bufferedReader.close();

        return stringBuilder.toString();
    }

    public boolean checkIfFileExists(String path) {
        // I check to see if a file already exists
        File file = new File(path);
        return file.exists();
    }

    public boolean fileModifyDate(String fileName) {
        // I check to see if a file is older than 15 minutes
        File file = new File(fileName);
        Date now = new Date();
        long time = now.getTime();

        return time - file.lastModified() < 900000;
    }
}
