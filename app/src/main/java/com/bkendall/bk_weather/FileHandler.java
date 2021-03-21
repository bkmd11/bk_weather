package com.bkendall.bk_weather;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class FileHandler {
    public static void createFile(Context context, JSONObject jsonObject, String fileName) throws IOException {
        // I create a file in the App memory
        String userString = jsonObject.toString();

        File file = new File(context.getFilesDir(), fileName);
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(userString);
        bufferedWriter.close();
    }

    public static JSONObject readFile(Context context, String fileName) throws IOException, JSONException {
        // I read the contents of a file and return a JSONObject
        File file = new File(context.getFilesDir(), fileName);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder stringBuilder = new StringBuilder();
        String line = bufferedReader.readLine();
        while (line != null) {
            stringBuilder.append(line).append("\n");
            line = bufferedReader.readLine();
        }
        bufferedReader.close();

        return new JSONObject(stringBuilder.toString());
    }

    public static boolean checkIfFileExists(Context context, String fileName) {
        // I check to see if a file already exists
        String path = context.getFilesDir().getAbsolutePath() + "/" + fileName;
        File file = new File(path);
        return file.exists();
    }

    public static boolean fileModifyDate(Context context, String fileName) throws IOException {
        // I check to see if a file is older than 15 minutes
        File file = new File(context.getFilesDir(), fileName);
        Date now = new Date();
        long time = now.getTime();

        System.out.println(time - file.lastModified());
        return time - file.lastModified() < 900000;
    }
}
