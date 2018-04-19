package com.darvishiyan.architecture.utils;

/**
 * Created by Hesam on 2/14/2018.
 */

public class StringUtil {

    public static boolean validate(String s) {
        return s != null && s.trim().length() > 0 && !s.equalsIgnoreCase("null");
    }

}
