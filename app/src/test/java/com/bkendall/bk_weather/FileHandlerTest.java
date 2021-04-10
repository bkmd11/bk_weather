package com.bkendall.bk_weather;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;


public class FileHandlerTest {
    FileHandler fh = new FileHandler();
    @Test
    public void testCreateFile() throws JSONException {
        try {
            fh.createFile("src\\test\\java\\com\\bkendall\\bk_weather\\spam.json",
                    "{\"lat\":0,\"lon\":0,\"timezone\":\"Etc\\/GMT\\");
        } catch (IOException e) {
            System.out.println("FILE WRITE ERROR!!!");
        }
    }

    @Test
    public void testCheckIfFileExists(){
        Assert.assertTrue(fh.checkIfFileExists("src\\test\\java\\com\\bkendall\\bk_weather\\test.json"));
    }

    @Test
    public void testFileModifyDateOldFile(){
        // TODO: Watch this test to see if it still doesnt pass. Should find a better way to test
        Assert.assertTrue(fh.fileModifyDate("src\\test\\java\\com\\bkendall\\bk_weather\\test.json"));
    }

    @Test
    public void testFileModifyDateNewFile(){
        Assert.assertTrue(fh.fileModifyDate("src\\test\\java\\com\\bkendall\\bk_weather\\spam.json"));
    }
}