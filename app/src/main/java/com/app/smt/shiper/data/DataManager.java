package com.app.smt.shiper.data;

import com.app.smt.shiper.data.db.DatabaseHelper;
import com.app.smt.shiper.data.db.model.User;
import com.app.smt.shiper.data.model.TokenRequest;
import com.app.smt.shiper.data.model.TokenResponse;
import com.app.smt.shiper.data.model.fcm.FcmTokenRequest;
import com.app.smt.shiper.data.model.fcm.FcmTokenResponse;
import com.app.smt.shiper.data.model.user.UserProfile;
import com.app.smt.shiper.data.network.ApiHelper;
import com.app.smt.shiper.data.prefs.PreferencesHelper;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Part;

@Singleton
public class DataManager {

    private final ApiHelper mApiHelper;
    private final DatabaseHelper mDatabaseHelper;
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public DataManager(ApiHelper apiHelper, PreferencesHelper preferencesHelper,
                       DatabaseHelper databaseHelper) {
        mApiHelper = apiHelper;
        mPreferencesHelper = preferencesHelper;
        mDatabaseHelper = databaseHelper;
    }

    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }

    public void clearUser() {
        mPreferencesHelper.clear();
    }

    public void setAccessToken(String accessToken) {
        mPreferencesHelper.setAccessToken(accessToken);
    }

    public String getAccessToken() {
        return mPreferencesHelper.getAccessToken();
    }

    private String getAuthorization() {
        return mPreferencesHelper.getAccessToken();
    }

    public String getJwtNotification() {
        return mPreferencesHelper.getJwtNotification();
    }

    public void setJwtNotification(String token) {
        mPreferencesHelper.setJwtNotification(token);
    }

    public void setLastLocation(LatLng location) {
        mPreferencesHelper.setLastLocation(location);
    }

    public LatLng getLastLocation() {
        return mPreferencesHelper.getLastLocation();
    }

    public void setLastLocationAddress(String address) {
        mPreferencesHelper.setLastLocationAddress(address);
    }

    public String getLastLocationAddress() {
        return mPreferencesHelper.getLastLocationAddress();
    }

    public void setNotificationBookingID(String bookingID) {
        mPreferencesHelper.setNotificationBookingID(bookingID);
    }

    public String getNotificationBookingID() {
        return mPreferencesHelper.getNotificationBookingID();
    }

    public void setCountPromotion(int count) {
        mPreferencesHelper.setCountPromotion(count);
    }

    public int getCountPromotion() {
        return mPreferencesHelper.getCountPromotion();
    }

    public Observable<Boolean> insertUser(User user) {
        return mDatabaseHelper.insertUser(user);
    }

    public Observable<List<User>> getAllUsers() {
        return mDatabaseHelper.getAllUsers();
    }

    public Observable<TokenResponse> getTokenUser(TokenRequest tokenRequest) {
        return mApiHelper.getTokenUser(tokenRequest);
    }

    public Observable<UserProfile> apiGetUserProfile() {
        return mApiHelper.apiGetUserProfile(getAuthorization());
    }

    public Observable<UserProfile> apiUpdateUserProfile(UserProfile userProfile) {
        return mApiHelper.apiUpdateUserProfile(getAuthorization(), userProfile);
    }

    public Observable<UserProfile> apiUploadAvatar(@Part MultipartBody.Part file) {
        return mApiHelper.apiUploadAvatar(getAuthorization(), file);
    }

    public Observable<FcmTokenResponse> syncRegistrationFcmToken(FcmTokenRequest fcmTokenRequest) {
        return mApiHelper.syncRegistrationFcmToken(getAuthorization(), fcmTokenRequest);
    }

}
