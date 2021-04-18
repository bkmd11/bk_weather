package com.bkendall.bk_weather;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;


public class FileHandlerTest {
    FileHandler fh = new FileHandler();

    @Before
    public void testCreateFile() {
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
        Assert.assertFalse(fh.fileModifyDate("src\\test\\java\\com\\bkendall\\bk_weather\\test.json"));
    }

    @Test
    public void testFileModifyDateNewFile(){
        Assert.assertTrue(fh.fileModifyDate("src\\test\\java\\com\\bkendall\\bk_weather\\spam.json"));
    }

    @Test
    public void testReadFile(){
        try {
            Assert.assertEquals("{\"lat\":0,\"lon\":0,\"timezone\":\"Etc\\/GMT\\\n",
                    fh.readFile(new File("src\\test\\java\\com\\bkendall\\bk_weather\\spam.json")));
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}