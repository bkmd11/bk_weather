package com.bkendall.bk_weather;

import org.junit.Assert;
import org.junit.Test;

public class TestStringHandler {
    @Test
    public void testSetFirstLetterCap(){
        String result = StringHandler.setFirstLetterCap("spam");
        Assert.assertEquals(result, "Spam");
        }

    @Test
    public void testSetFirstLetterCapHandlesCap(){
        String result = StringHandler.setFirstLetterCap("Spam");
        Assert.assertEquals(result, "Spam");
    }

    @Test
    public void testSetFistLetterCapHandlesSpace() {
        String result = StringHandler.setFirstLetterCap(" ");
        Assert.assertEquals(result, " ");
    }

    @Test
    public void testGetTimeRegexReturnsTime(){
        String result = StringHandler.getTimeRegex("Fri Nov 13 02:00:00 EST 2020");
        Assert.assertEquals(result, "2:00 AM");
    }

    @Test
    public void testGetTimeRegexReturnsWhenNoMatch(){
        String result = StringHandler.getTimeRegex("Fri Nov 13 EST 2020");
        Assert.assertEquals(result, "XX:XX");
    }

    @Test
    public void testConvertStandardTimeLessThanTwelve(){
        String result = StringHandler.convertStandardTime("04:00");
        Assert.assertEquals(result, "4:00 AM");
    }

    @Test
    public void testConvertStandardTimeGreaterThanTwelve(){
        String result = StringHandler.convertStandardTime("13:00");
        Assert.assertEquals(result, "1:00 PM");
    }

    @Test
    public void testConvertStandardTimeTwelve(){
        String result = StringHandler.convertStandardTime("12:00");
        Assert.assertEquals(result, "12:00 PM");
    }

    @Test
    public void testConvertStandardTimeMidnight(){
        String result = StringHandler.convertStandardTime("00:00");
        Assert.assertEquals(result, "12:00 AM");
    }
}
