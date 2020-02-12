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

import android.app.Activity;
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
import java.util.Calendar;
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

    public static long componentTimeToTimestampUTC(int year, int month, int day, int hour, int minute) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return (c.getTimeInMillis());
    }

    public static void callPhone(Activity mContext, String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        mContext.startActivity(intent);
    }

    /*public static ArrayList<com.app.smt.shiper.data.model.order.CallLog> getCallLog(Activity context, String phone) {
        ArrayList<com.app.smt.shiper.data.model.order.CallLog> arrayList = new ArrayList<>();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context,
                    new String[]{Manifest.permission.READ_CALL_LOG}, 1);
            AppLogger.e("not permission call log ");
            return arrayList;
        } else {
            Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI,
                    null, CallLog.Calls.NUMBER + " LIKE '%"+phone+"%'", null, CallLog.Calls.DATE + " DESC" + " LIMIT 3");
            int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
            int type = cursor.getColumnIndex(CallLog.Calls.TYPE);
            int date = cursor.getColumnIndex(CallLog.Calls.DATE);
            int duration = cursor.getColumnIndex(CallLog.Calls.DURATION);
            while (cursor.moveToNext()) {
                com.app.smt.shiper.data.model.order.CallLog log = new com.app.smt.shiper.data.model.order.CallLog();
                String phNumber = cursor.getString(number);
                String callType = cursor.getString(type);
                String callDate = cursor.getString(date);
                log.setId(callDate);
                String callDuration = cursor.getString(duration);
                int dircode = Integer.parseInt(callType);
                switch (dircode) {
                    case CallLog.Calls.OUTGOING_TYPE:
                        log.setContent("Gọi khách hàng " + phNumber + " lúc " + getDateFromTimestamp(Long.valueOf(callDate), "HH:mm dd/MM/yyyy") + " thời gian " + callDuration + "s");
                        break;
                    case CallLog.Calls.INCOMING_TYPE:
                        log.setContent("Khách hàng gọi shiper" + " lúc " + getDateFromTimestamp(Long.valueOf(callDate), "HH:mm dd/MM/yyyy") + " thời gian " + callDuration + "s");
                        break;

                    case CallLog.Calls.MISSED_TYPE:
                        log.setContent("Khách hàng gọi nhỡ shiper" + " lúc " + getDateFromTimestamp(Long.valueOf(callDate), "HH:mm dd/MM/yyyy") + " thời gian " + callDuration + "s");
                        break;
                }
                arrayList.add(log);
            }
            cursor.close();
            return arrayList;
        }
    }*/
}
