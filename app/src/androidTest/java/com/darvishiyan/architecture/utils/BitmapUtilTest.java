package com.darvishiyan.architecture.utils;

import android.graphics.Bitmap;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class BitmapUtilTest {

    private Bitmap src;

    @Before
    public void setUp() {
        src = Bitmap.createBitmap(100, 200, Bitmap.Config.ARGB_8888);
    }

    @Test
    public void testOnBitmapConverter() {
        byte[] byteArray = BitmapUtil.toByteArray(src);
        Bitmap des = BitmapUtil.toBitmap(byteArray);
        Assert.assertEquals(src.getWidth(), des.getWidth());
        Assert.assertEquals(src.getHeight(), des.getHeight());
        Assert.assertEquals(src.getConfig(), des.getConfig());
    }
}