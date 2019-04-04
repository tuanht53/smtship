package com.app.smt.shiper.data.prefs;

import android.content.Context;

import com.app.smt.shiper.di.ApplicationContext;
import com.app.smt.shiper.util.AppConstants;
import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PreferencesHelper extends BasePreferenceHelper {

    private static final String PREF_FILE_NAME = "android_caro_pref_file";

    private static final String PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN";

    private static final String PREF_KEY_LAST_LOCATION_LAT = "PREF_KEY_LAST_LOCATION_LAT";

    private static final String PREF_KEY_LAST_LOCATION_LNG = "PREF_KEY_LAST_LOCATION_LNG";

    private static final String PREF_KEY_LAST_LOCATION_ADDRESS = "PREF_KEY_LAST_LOCATION_ADDRESS";

    private static final String PREF_KEY_NOTIFICATION_MODEL_ID = "PREF_KEY_NOTIFICATION_MODEL_ID";

    private static final String PREF_KEY_COUNT_PROMO = "PREF_KEY_COUNT_PROMO";

    private static final String PREF_KEY_JWT_NOTIFICATION = "PREF_KEY_JWT_NOTIFICATION";

    @Inject
    public PreferencesHelper(@ApplicationContext Context context) {
        super(context);
    }

    @Override
    protected String getPreferenceName() {
        return PREF_FILE_NAME;
    }

    public String getAccessToken() {
        return getString(PREF_KEY_ACCESS_TOKEN, null);
    }

    public void setAccessToken(String accessToken) {
        put(PREF_KEY_ACCESS_TOKEN, accessToken);
    }

    public String getJwtNotification() {
        return getString(PREF_KEY_JWT_NOTIFICATION, null);
    }

    public void setJwtNotification(String token) {
        put(PREF_KEY_JWT_NOTIFICATION, token);
    }

    public void setLastLocation(LatLng location) {
        put(PREF_KEY_LAST_LOCATION_LAT, location.latitude);
        put(PREF_KEY_LAST_LOCATION_LNG, location.longitude);
    }

    public LatLng getLastLocation() {
        double lat = getDouble(PREF_KEY_LAST_LOCATION_LAT, AppConstants.LAT_DEFAULT);
        double lng = getDouble(PREF_KEY_LAST_LOCATION_LNG, AppConstants.LONG_DEFAULT);
        return new LatLng(lat, lng);
    }

    public void setLastLocationAddress(String address) {
        put(PREF_KEY_LAST_LOCATION_ADDRESS, address);
    }

    public String getLastLocationAddress() {
        return getString(PREF_KEY_LAST_LOCATION_ADDRESS, "");
    }

    public void setNotificationBookingID(String bookingID) {
        put(PREF_KEY_NOTIFICATION_MODEL_ID, bookingID);
    }

    public String getNotificationBookingID() {
        return getString(PREF_KEY_NOTIFICATION_MODEL_ID, "");
    }

    public void setCountPromotion(int count) {
        put(PREF_KEY_COUNT_PROMO, count);
    }

    public int getCountPromotion() {
        return getInt(PREF_KEY_COUNT_PROMO, 0);
    }

}
