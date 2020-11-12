import com.bkendall.bk_weather.StringBuilder;

import org.junit.Assert;
import org.junit.Test;

public class TestStringBuilder {
    @Test
    public void testSetFirstLetterCap(){
        String result = StringBuilder.setFirstLetterCap("spam");
        Assert.assertEquals(result, "Spam");
        }

    @Test
    public void testSetFirstLetterCapHandlesCap(){
        String result = StringBuilder.setFirstLetterCap("Spam");
        Assert.assertEquals(result, "Spam");
    }

    @Test
    public void testGetTimeRegexReturnsTime(){
        String result = StringBuilder.getTimeRegex("Fri Nov 13 02:00:00 EST 2020");
        Assert.assertEquals(result, "02:00");
    }

    @Test
    public void testGetTimeRegexReturnsWhenNoMatch(){
        String result = StringBuilder.getTimeRegex("Fri Nov 13 EST 2020");
        Assert.assertEquals(result, "XX:XX");
    }
}
