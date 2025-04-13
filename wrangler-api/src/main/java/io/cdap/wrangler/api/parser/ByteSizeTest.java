package io.cdap.wrangler.api.parser;

import org.junit.Assert;
import org.junit.Test;

public class ByteSizeTest {

        @Test
        public void testKBConversion() {
            ByteSize size = new ByteSize("1KB");
            Assert.assertEquals(1024, size.getBytes());
        }

        @Test
        public void testMBConversion() {
            ByteSize size = new ByteSize("2.5MB");
            Assert.assertEquals(2621440L, size.getBytes());
        }

        @Test
        public void testGBConversion() {
            ByteSize size = new ByteSize("1GB");
            Assert.assertEquals(1073741824L, size.getBytes());
        }

        @Test
        public void testNoUnitBytes() {
            ByteSize size = new ByteSize("512B");
            Assert.assertEquals(512L, size.getBytes());
        }
    }


