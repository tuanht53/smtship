package com.app.smt.shiper.util;

import android.text.TextUtils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class FilterNumberString {

    public static String filterNumber(String number) {
        char[] arrayNumber = number.toCharArray();
        for (int i = 0; i < arrayNumber.length; i++) {
            if (arrayNumber[i] != '.')
                try {
                    Integer.valueOf(arrayNumber[i] + "");
                } catch (Exception e) {
                    arrayNumber[i] = " ".charAt(0);
                }
        }
        number = String.valueOf(arrayNumber);
        number = number.replaceAll(" ", "");
        number = number.trim();
        if (TextUtils.isEmpty(number))
            number = "0";
        return number;
    }

    public static String filterMoney(String price) {
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
        otherSymbols.setDecimalSeparator('.');
        otherSymbols.setGroupingSeparator(',');
        NumberFormat format = new DecimalFormat("#,##0.##", otherSymbols);
        price = (!TextUtils.isEmpty(price)) ? price : "0";
        price = price.trim();
        price = format.format(Double.parseDouble(price));
        price = String.format("%1$s", price).replaceAll(",", ".");
        return price;
    }
}
