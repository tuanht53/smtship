/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.app.smt.shiper.util;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.app.smt.shiper.R;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Created by janisharali on 24/05/17.
 */

public final class AppUtils {

    private AppUtils() {
        // This class is not publicly instantiable
    }

    public static void openPlayStoreForApp(Context context) {
        final String appPackageName = context.getPackageName();
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(context
                            .getResources()
                            .getString(R.string.app_market_link) + appPackageName)));
        } catch (android.content.ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(context
                            .getResources()
                            .getString(R.string.app_google_play_store_link) + appPackageName)));
        }
    }

    public static String formatNumber(Number value, String format) {
        DecimalFormat df = new DecimalFormat(format);
        return df.format(value);
    }

    public static int dpToPx(int dp, Resources resources) {
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        return Math.round(
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) dp, displayMetrics));
    }

    public static String formatTimeMoving(int time) {
        SimpleDateFormat dateformat = new SimpleDateFormat("hh:mm aa", Locale.US);
        Date currentDate = new Date(System.currentTimeMillis() + time * 1000);
        return dateformat.format(currentDate);
    }

    public static String getDateTime(long timeStamp){

        try{
            DateFormat sdf = new SimpleDateFormat(AppConstants.TIMESTAMP_FORMAT);
            Date netDate = (new Date(timeStamp));
            return sdf.format(netDate);
        }
        catch(Exception ex){
            return "";
        }
    }

    public static String getDateFromTimestamp(long mTimestamp, String mDateFormate) {
        String date = null;
        try {
            date = android.text.format.DateFormat.format(mDateFormate, mTimestamp).toString();
            return date;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
