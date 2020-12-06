import com.bkendall.bk_weather.StringHandler;

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
        Assert.assertEquals(result, "02:00");
    }

    @Test
    public void testGetTimeRegexReturnsWhenNoMatch(){
        String result = StringHandler.getTimeRegex("Fri Nov 13 EST 2020");
        Assert.assertEquals(result, "XX:XX");
    }
}
