package com.app.smt.shiper.data.network;


import com.app.smt.shiper.data.model.TokenRequest;
import com.app.smt.shiper.data.model.TokenResponse;
import com.app.smt.shiper.data.model.fcm.FcmTokenRequest;
import com.app.smt.shiper.data.model.fcm.FcmTokenResponse;
import com.app.smt.shiper.data.model.user.UserProfile;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiHelper {

    @POST("/pricingsku/v1/users/phone-oauth")
    Observable<TokenResponse> getTokenUser(@Body TokenRequest tokenRequest);

    @GET("/pricingsku/customers/profiles")
    Observable<UserProfile> apiGetUserProfile(@Header("Authorization") String authorization);

    @POST("/pricingsku/customers/profiles")
    Observable<UserProfile> apiUpdateUserProfile(@Header("Authorization") String authorization, @Body UserProfile userProfile);

    @Multipart
    @POST("/pricingsku/customers/upload-avatar")
    Observable<UserProfile> apiUploadAvatar(@Header("Authorization") String authorization, @Part MultipartBody.Part file);

    @Headers("Device-Agent: android")
    @POST("/pricingsku/customers/registration-token")
    Observable<FcmTokenResponse> syncRegistrationFcmToken(@Header("Authorization") String authorization, @Body FcmTokenRequest fcmTokenRequest);

}
