package io.cdap.wrangler.api.parser;

import org.junit.Assert;
import org.junit.Test;

public class TimeDurationTest {

    @Test
    public void testMilliseconds() {
        TimeDuration time = new TimeDuration("500ms");
        Assert.assertEquals(500L, time.getMillis());
    }

    @Test
    public void testSeconds() {
        TimeDuration time = new TimeDuration("2s");
        Assert.assertEquals(2000L, time.getMillis());
    }

    @Test
    public void testMinutes() {
        TimeDuration time = new TimeDuration("1.5min");
        Assert.assertEquals(90000L, time.getMillis());
    }

    @Test
    public void testSecAlias() {
        TimeDuration time = new TimeDuration("3sec");
        Assert.assertEquals(3000L, time.getMillis());
    }
}
