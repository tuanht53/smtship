package com.app.smt.shiper.util;

import android.text.TextUtils;

public class StringUtils {
    public static String getMainAddress(String address) {
        if (TextUtils.isEmpty(address)) {
            return "";
        } else {
            String[] array = address.split(",");
            if (array.length > 2) {
                if (array[0].contains(" ")) {
                    return array[0];
                } else {
                    return array[0] + " " + array[1];
                }
            } else {
                return address;
            }
        }
    }
}
