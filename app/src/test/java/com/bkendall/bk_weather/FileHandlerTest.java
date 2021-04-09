package com.bkendall.bk_weather;

import org.junit.Assert;
import org.junit.Test;

public class FileHandlerTest {
    FileHandler fh = new FileHandler();

    @Test
    public void testCheckIfFileExists(){
        Assert.assertTrue(fh.checkIfFileExists("src\\test\\java\\com\\bkendall\\bk_weather\\test.json"));
    }

    @Test
    public void testFileModifyDate(){
        Assert.assertFalse(fh.fileModifyDate("src\\test\\java\\com\\bkendall\\bk_weather\\test.json"));
    }
}