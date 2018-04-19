package com.darvishiyan.architecture.utils;

import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class StringUtilTest {

    @Test
    public void testOnValidate() {
        Assert.assertEquals(StringUtil.validate(null), false);
        Assert.assertEquals(StringUtil.validate(""), false);
        Assert.assertEquals(StringUtil.validate("null"), false);
        Assert.assertEquals(StringUtil.validate(" "), false);
        Assert.assertEquals(StringUtil.validate("test"), true);
    }

}
